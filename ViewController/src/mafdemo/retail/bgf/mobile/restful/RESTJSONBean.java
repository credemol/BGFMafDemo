package mafdemo.retail.bgf.mobile.restful;



import java.net.URL;

import mafdemo.retail.bgf.mobile.PickUpHistoryRepository;
import mafdemo.retail.bgf.mobile.PickUpOrder;

import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.dc.ws.rest.RestServiceAdapter;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.api.JSONBeanSerializationHelper;
import oracle.adfmf.framework.api.Model;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.json.JSONArray;
import oracle.adfmf.json.JSONObject;
import oracle.adfmf.json.JSONString;

public class RESTJSONBean {

    private PickUpHistoryRepository pickUpHistoryRepository = new PickUpHistoryRepository();
    
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public RESTJSONBean() {
    }


    private String customerId;
    private String storeId;
    private int maxOrderId;



    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }


    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setMaxOrderId(int maxOrderId) {
        this.maxOrderId = maxOrderId;
    }

    public int getMaxOrderId() {
        return maxOrderId;
    }
    
    public boolean addOrder(PickUpOrder order) {
        TraceLog.info(getClass(), "addOrder", "START");
        
        RestServiceAdapter restServiceAdapter = Model.createRestServiceAdapter();
        restServiceAdapter.clearRequestProperties();
        
        restServiceAdapter.addRequestProperty("Content-Type", "application/json");
        restServiceAdapter.addRequestProperty("Accept", "application/json");

        restServiceAdapter.setConnectionName("bgf_demo_server");
        
        restServiceAdapter.setRequestType(RestServiceAdapter.REQUEST_TYPE_POST);
        
        restServiceAdapter.setRequestURI("/resources/pickuporder/add");
        
        PickupOrderWrapper orderWrapper = PickupOrderWrapper.copyOf(order);

        TraceLog.info(getClass(), "addOrder", "orderWrapper: " + orderWrapper);
        try {
            
            Object jsonObject = JSONBeanSerializationHelper.toJSON(orderWrapper);
            
            System.out.println("json Object class: " + jsonObject.getClass().getName());
            
            if(jsonObject instanceof JSONObject) {
                ((JSONObject) jsonObject).remove(".type");
                JSONArray items = ((JSONObject) jsonObject).getJSONArray("items");
                
                for(int i = 0, len = items.length(); i < len; i++) {
                    ((JSONObject)items.get(i)).remove(".type");
                }
            }
            
            String reqJson = jsonObject.toString();
            TraceLog.info(getClass(), "addOrder", "reqJson: " + reqJson);
            
            restServiceAdapter.setRetryLimit(0);
            
            String resJson = restServiceAdapter.send(reqJson);
            TraceLog.info(getClass(), "addOrder", "resJson: " + resJson);
            
            AddPickupOrderResponse response = (AddPickupOrderResponse) 
                JSONBeanSerializationHelper.fromJSON(AddPickupOrderResponse.class, resJson);
            
            Result result = response.getResult();
            if(result != null) {
                TraceLog.info(getClass(), "addOrder", "success: " + result.isSuccess());
                
                if(result.isSuccess()) {
                    pickUpHistoryRepository.insertPickupOrderWrapper(response.getPickupOrder());
                }
                return result.isSuccess();
            } 
        } catch(Exception e) {
            TraceLog.severe(getClass(), "addOrder", "ERROR: " + e.getMessage());
        }
        //JSONBeanSerializationHelper.fromJSON(PickupOrderWrapper.class, getJsonResponse());
        
        return false;
    }
    
    
    public boolean syncOrders(String storeId, int maxId) {
        TraceLog.info(getClass(), "syncOrders", "START - storeId: " + storeId + ", maxId: " + maxId);
        
        RestServiceAdapter restServiceAdapter = Model.createRestServiceAdapter();
        restServiceAdapter.clearRequestProperties();
        
        //restServiceAdapter.addRequestProperty("Content-Type", "application/json");
        restServiceAdapter.addRequestProperty("Accept", "application/json");

        restServiceAdapter.setConnectionName("bgf_demo_server");
        
        restServiceAdapter.setRequestType(RestServiceAdapter.REQUEST_TYPE_GET);
        
        String requestURI = "/resources/pickuporder/listByStore/" + storeId + "/" + maxId;
        TraceLog.info(getClass(), "syncOrders", "requestURI: " + requestURI);
        restServiceAdapter.setRequestURI(requestURI);
        
        restServiceAdapter.setRetryLimit(0);
        try {
            String resJson = restServiceAdapter.send("");
            PickupOrderListResponse orderListResponse = (PickupOrderListResponse)
                JSONBeanSerializationHelper.fromJSON(PickupOrderListResponse.class, resJson);

            TraceLog.severe(getClass(), "syncOrders", "Success: " + orderListResponse.getResult().isSuccess());
            if(orderListResponse.getResult().isSuccess()) {
                PickupOrderWrapper[] orders = orderListResponse.getPickupOrderList();
                
                for(int i = 0; i < orders.length; i++) {
                    pickUpHistoryRepository.insertPickupOrderWrapper(orders[i]);
                }
            }
            return true;
        } catch(Exception e) {
            TraceLog.severe(getClass(), "syncOrders", "ERROR: " + e.getMessage());
            return false;
        }
        
    }
    
    public String getOrderStatus(String orderId) {
        TraceLog.info(getClass(), "getOrderStatus", "START - orderId: " + orderId);
        
        RestServiceAdapter restServiceAdapter = Model.createRestServiceAdapter();
        restServiceAdapter.clearRequestProperties();
        
        //restServiceAdapter.addRequestProperty("Content-Type", "application/json");
        restServiceAdapter.addRequestProperty("Accept", "application/json");

        restServiceAdapter.setConnectionName("bgf_demo_server");
        
        restServiceAdapter.setRequestType(RestServiceAdapter.REQUEST_TYPE_GET);
        
        String requestURI = "/resources/pickuporder/getOrderStatus/" + orderId ;
        TraceLog.info(getClass(), "getOrderStatus", "requestURI: " + requestURI);
        restServiceAdapter.setRequestURI(requestURI);
        
        restServiceAdapter.setRetryLimit(0);
        
        String status = "99";
        try {
            String resJson = restServiceAdapter.send("");
           
                
            JSONObject jsonObject = (JSONObject)JSONBeanSerializationHelper.fromJSON(JSONObject.class, resJson);
            status = jsonObject.getString("status");

            TraceLog.info(getClass(), "getOrderStatus", "status: " + status);
        } catch(Exception e) {
            TraceLog.severe(getClass(), "getOrderStatus", "ERROR: " + e.getMessage());
        }
        return status;
    }
    
    public boolean updateOrderStatus(String orderId, String status) {
        TraceLog.info(getClass(), "updateOrderStatus", "START - orderId: " + orderId);
        
        RestServiceAdapter restServiceAdapter = Model.createRestServiceAdapter();
        restServiceAdapter.clearRequestProperties();
        
        //restServiceAdapter.addRequestProperty("Content-Type", "application/json");
        restServiceAdapter.addRequestProperty("Accept", "application/json");

        restServiceAdapter.setConnectionName("bgf_demo_server");
        
        restServiceAdapter.setRequestType(RestServiceAdapter.REQUEST_TYPE_PUT);
        
        String requestURI = "/resources/pickuporder/updateOrderStatus/" + orderId + "/" + status ;
        TraceLog.info(getClass(), "updateOrderStatus", "requestURI: " + requestURI);
        restServiceAdapter.setRequestURI(requestURI);
        
        restServiceAdapter.setRetryLimit(0);
        
        boolean updated = false;
        try {
            String resJson = restServiceAdapter.send("");
           
                
            JSONObject jsonObject = (JSONObject)JSONBeanSerializationHelper.fromJSON(JSONObject.class, resJson);
            updated = jsonObject.getBoolean("updated");

            TraceLog.info(getClass(), "updateOrderStatus", "updated: " + updated);
        } catch(Exception e) {
            TraceLog.severe(getClass(), "updateOrderStatus", "ERROR: " + e.getMessage());
        }
        return updated;
    }
    /*
    public void loadData() {

        RestServiceAdapter restServiceAdapter = Model.createRestServiceAdapter();

        // Clear any previously set request properties, if any
        restServiceAdapter.clearRequestProperties();

        // Set the connection name
        restServiceAdapter.setConnectionName("FB_GRAPH");

        // Specify the type of request
        restServiceAdapter.setRequestType(RestServiceAdapter.REQUEST_TYPE_GET);

        // Specify the number of retries
        restServiceAdapter.setRetryLimit(0);

        String accessToken = (String)AdfmfJavaUtilities.getELValue("#{applicationScope.access_token}");
        setAccessToken(accessToken);

        // Set the URI which is defined after the endpoint in the connections.xml.
        // The request is the endpoint + the URI being set
        restServiceAdapter.setRequestURI("/me?access_token=" + accessToken);

        AdfmfJavaUtilities.setELValue("#{applicationScope.requestURI}", restServiceAdapter.getRequestURI());
        //setJsonResponse("/me?access_token=" + getAccessToken());

        // Execute SEND and RECEIVE operation
        try {
            // For GET request, there is no payload
            setJsonResponse(restServiceAdapter.send(""));

            // Now create a new RESTJSONResponse object and parse the JSON string returned into this class
            RESTJSONResponse res = new RESTJSONResponse();
            res = (RESTJSONResponse)JSONBeanSerializationHelper.fromJSON(RESTJSONResponse.class, getJsonResponse());
            setResponse(res);
        } catch (Exception e) {
            e.printStackTrace();
            setJsonResponse(e.getMessage());
        }
    }
    */
}
