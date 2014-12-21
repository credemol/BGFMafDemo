package mafdemo.retail.bgf.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import java.util.Set;

import javax.el.MethodExpression;

import mafdemo.retail.bgf.application.TraceLog;
import mafdemo.retail.bgf.mobile.restful.RESTJSONBean;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.dc.bean.ConcreteJavaBeanObject;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.model.AdfELContext;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class ShoppingCartBean {
    private Store selectedStore;
    private Date pickupDate;
    private Map quantityMap = new HashMap(); // productId, quantity.
    
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private PickUpHistoryRepository pickUpHistoryRepository = new PickUpHistoryRepository();
    private RESTJSONBean restJsonBean = new RESTJSONBean();
    
    public ShoppingCartBean() {
        super();
    }

    public void setSelectedStore(Store selectedStore) {
        TraceLog.info(getClass(), "setSelectedStore", "START - selectedStore: " + selectedStore);
        Store oldSelectedStore = this.selectedStore;
        this.selectedStore = selectedStore;
        propertyChangeSupport.firePropertyChange("selectedStore", oldSelectedStore, selectedStore);

        TraceLog.info(getClass(), "setSelectedStore", "END");
    }

    public Store getSelectedStore() {
        return selectedStore;
    }

    public void setQuantityMap(Map quantityMap) {
        Map oldQuantityMap = this.quantityMap;
        this.quantityMap = quantityMap;
        propertyChangeSupport.firePropertyChange("quantityMap", oldQuantityMap, quantityMap);
    }

    public Map getQuantityMap() {
        return quantityMap;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    
    public void setQuantity(ActionEvent actionEvent) {
        TraceLog.info(getClass(), "setQuantity", "START");
        
        Object row = AdfmfJavaUtilities.getELValue("#{bindings.selectedProductsIterator.currentRow}");

        TraceLog.info(getClass(), "setQuantity", "row: " + row);
        
        if(row != null) {
            TraceLog.info(getClass(), "setQuantity", "row class: " + row.getClass().getName());
            oracle.adfmf.dc.bean.ConcreteJavaBeanObject beanObject = (oracle.adfmf.dc.bean.ConcreteJavaBeanObject) row;
            //TraceLog.info(getClass(), "increasePurchase", "id: " + beanObject.getAttribute("id"));
            //TraceLog.info(getClass(), "increasePurchase", "name: " + beanObject.getAttribute("name"));
            
            String productId = (String) beanObject.getAttribute("id");
            Integer quantity = (Integer) beanObject.getAttribute("quantity");

            TraceLog.info(getClass(), "setQuantity", "id: " + productId + ", quantity: " + quantity);
            
            Map quantityMap = (Map) AdfmfJavaUtilities.getELValue("#{pageFlowScope.ShoppingCartBean.quantityMap}");
            if(quantity.intValue() == 0) {
                quantityMap.remove(productId);
            } else {
                quantityMap.put(productId, quantity);
            }
            
            // calculate total price
            
            AdfELContext elContext = AdfmfJavaUtilities.getAdfELContext();
            MethodExpression methodExpression = AdfmfJavaUtilities.getMethodExpression("#{bindings.calculateTotalPrice.execute}", Object.class, new Class[] {});
            methodExpression.invoke(elContext, new Object[] {});
            
            // set product list
            /*
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.cartProductId}", productId);
            
            methodExpression = AdfmfJavaUtilities.getMethodExpression("#{bindings.setCurrentProductRowWithKey.execute}", Object.class, new Class[] {});
            methodExpression.invoke(elContext, new Object[] {});
            
            ConcreteJavaBeanObject productObject = 
                (ConcreteJavaBeanObject)AdfmfJavaUtilities.getELValue("#{bindings.productsIterator.currentRow}");
            
            if(productObject != null) {
                Product newProduct = PickupUtils.toProduct(productObject);
                newProduct.setQuantity(quantity.intValue());
                AdfmfJavaUtilities.setELValue("#{pageFlowScope.updatableProduct}", newProduct);
                methodExpression = AdfmfJavaUtilities.getMethodExpression("#{bindings.updateProduct.execute}", Object.class, new Class[] {});
                methodExpression.invoke(elContext, new Object[] {});
            }
*/
  
        }
    }
    
    public void sendRequest() {
        TraceLog.info(getClass(), "sendRequest", "START");
        
        
        // FIXME. if the number of item is odd, then failed.
        //Boolean sendRequestServiceResult = new Boolean((this.quantityMap.keySet().size() % 2) == 0);
        
        
        PickUpOrder order = new PickUpOrder();
        order.setOrderDate(new Date());
        order.setPickupDate(pickupDate);
        String username = (String) AdfmfJavaUtilities.getELValue("#{securityContext.userName}");
        TraceLog.info(getClass(), "sendRequest", "securityContext.userName: " + username);
        if(username == null || username.trim().length() == 0) {
            username = "bgfcust1";
        }
        order.setCustomerId(username);
        if(selectedStore != null) {
            order.setStoreId(selectedStore.getId());
        }
        
        int totalPrice = ((Integer) AdfmfJavaUtilities.getELValue("#{pageFlowScope.totalPrice}")).intValue();
        TraceLog.info(getClass(), "sendRequest", "totalPrice: " + totalPrice) ;
        order.setTotalPrice(totalPrice);
        
        List list = new ArrayList();
        for(Iterator iterator = quantityMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String productId = (String) entry.getKey();
            Integer quantity = (Integer) entry.getValue();
            Product product = new Product();
            product.setId(productId);
            product.setQuantity(quantity.intValue());
            list.add(product);
        }
        order.setProducts((Product[]) list.toArray(new Product[list.size()]));

                
        order.setStatus("01");
        
        Boolean sendRequestServiceResult = Boolean.valueOf(restJsonBean.addOrder(order));
        
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.sendRequestServiceResult}", sendRequestServiceResult);
        TraceLog.info(getClass(), "sendRequest", "sendRequestServiceResult: " + sendRequestServiceResult);
        if(sendRequestServiceResult.booleanValue()) {
            quantityMap.clear();
            setPickupDate(null);
            
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.selectedProvinceCode}", "");
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.selectedDistrictCode}", "");
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.selectedStoreName}", "");
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.service01}", "");
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.service02}", "");
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.service03}", "");
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.service04}", "");
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.service05}", "");
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.service06}", "");
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.service07}", "");
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.service08}", "");
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.selectedStoreId}", "");
            //AdfmfJavaUtilities.setELValue("#{pageFlowScope.ShoppingCartBean.pickupDate}", "");
            
            
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.selectedCategoryId}", "");
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.productNameQuery}", "");
            
            AdfELContext context = AdfmfJavaUtilities.getAdfELContext();
            
            MethodExpression me = 
                AdfmfJavaUtilities.getMethodExpression("#{bindings.initStores.execute}", Object.class, new Class[] { });
            me.invoke(context, new Object[] { });

            me = AdfmfJavaUtilities.getMethodExpression("#{bindings.initProducts.execute}", Object.class, new Class[] { });
            me.invoke(context, new Object[] { });

            me = AdfmfJavaUtilities.getMethodExpression("#{bindings.refreshPickUpHistory.execute}", Object.class, new Class[] { });
            me.invoke(context, new Object[] { });
            
        }
        TraceLog.info(getClass(), "sendRequest", "END");
        
    }
    
    
    public void sendRequest______NOT_USED() {
        TraceLog.info(getClass(), "sendRequest", "START");
        
        
        // FIXME. if the number of item is odd, then failed.
        //Boolean sendRequestServiceResult = new Boolean((this.quantityMap.keySet().size() % 2) == 0);
        //Boolean sendRequestServiceResult = Boolean.TRUE;
        Boolean sendRequestServiceResult = Boolean.TRUE;
        
        PickUpOrder order = new PickUpOrder();
        order.setOrderDate(new Date());
        order.setPickupDate(pickupDate);
        
       
        order.setCustomerId("bgfcust1");
        if(selectedStore != null) {
            order.setStoreId(selectedStore.getId());
        }
        
        int totalPrice = ((Integer) AdfmfJavaUtilities.getELValue("#{pageFlowScope.totalPrice}")).intValue();
        TraceLog.info(getClass(), "sendRequest", "totalPrice: " + totalPrice) ;
        order.setTotalPrice(totalPrice);
        
        List list = new ArrayList();
        for(Iterator iterator = quantityMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String productId = (String) entry.getKey();
            Integer quantity = (Integer) entry.getValue();
            Product product = new Product();
            product.setId(productId);
            product.setQuantity(quantity.intValue());
            list.add(product);
        }
        order.setProducts((Product[]) list.toArray(new Product[list.size()]));

                
        order.setStatus("01");
        
        pickUpHistoryRepository.insertPickUpOrder(order);
        
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.sendRequestServiceResult}", sendRequestServiceResult);
        
        if(sendRequestServiceResult.booleanValue()) {
            quantityMap.clear();
        }
        TraceLog.info(getClass(), "sendRequest", "END");

    }

    public void setPickupDate(Date pickupDate) {
        Date oldPickupDate = this.pickupDate;
        this.pickupDate = pickupDate;
        propertyChangeSupport.firePropertyChange("pickupDate", oldPickupDate, pickupDate);
    }

    public Date getPickupDate() {
        return pickupDate;
    }

}
