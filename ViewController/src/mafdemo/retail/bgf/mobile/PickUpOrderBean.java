package mafdemo.retail.bgf.mobile;

import javax.el.MethodExpression;

import mafdemo.retail.bgf.application.TraceLog;
import mafdemo.retail.bgf.mobile.restful.RESTJSONBean;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.model.AdfELContext;

public class PickUpOrderBean {
    private PickUpHistoryRepository pickUpHistoryRepository = new PickUpHistoryRepository();
    private RESTJSONBean restJsonBean = new RESTJSONBean();
    
    public PickUpOrderBean() {
        super();
    }
    
    

    public void syncOrderList(ActionEvent actionEvent) {
        // Add event code here...
        String storeId = (String) AdfmfJavaUtilities.getELValue("#{applicationScope.storeId}");
        int maxId = pickUpHistoryRepository.getMaxOrderIdOfStore(storeId);
        
        boolean result = restJsonBean.syncOrders(storeId, maxId);    
        
        // https://blogs.oracle.com/jdevotnharvest/entry/how_to_programmatically_display_a
        // http://www.jobinesh.com/2013/12/adfmobile-programatically-invoking.html
        String message = result ? "Sync is completed." : "Sync is failed";
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(
                  "mafdemo.retail.bgf.Pack",
                   "navigator.notification.alert",
                   new Object[] {message,"", "Sync Result", "Ok"});        
        
        AdfELContext context = AdfmfJavaUtilities.getAdfELContext();
        
        MethodExpression me = 
            AdfmfJavaUtilities.getMethodExpression("#{bindings.refreshOrderList.execute}", Object.class, new Class[] { });
        
        //me.invoke(context, new Object[] { provinceCode });
        me.invoke(context, new Object[] { });
    }
    
    public void executeSyncOrderList() {
        String storeId = (String) AdfmfJavaUtilities.getELValue("#{applicationScope.storeId}");
        int maxId = pickUpHistoryRepository.getMaxOrderIdOfStore(storeId);
        
        boolean result = restJsonBean.syncOrders(storeId, maxId);    
        
        AdfmfJavaUtilities.setELValue("#{applicationScope.notified}", Boolean.FALSE);
    }

    public void updateOrderStatus(ActionEvent actionEvent) {
        TraceLog.info(getClass(), "updateOrderStatus", "START");
        // Add event code here...
        
        String orderId = (String)AdfmfJavaUtilities.getELValue("#{bindings.id.inputValue}");
        //String status = (String)AdfmfJavaUtilities.getELValue("#{bindings.status.inputValue}");        
        
        String status = (String)AdfmfJavaUtilities.getELValue("#{viewScope.newStatus}");

        TraceLog.info(getClass(), "updateOrderStatus","orderId: " + orderId + ", status: " + status);
        
        boolean updated = restJsonBean.updateOrderStatus(orderId, status);
        
        if(updated) {
            pickUpHistoryRepository.updateOrderStatus(orderId, status);
            AdfmfJavaUtilities.setELValue("#{bindings.status.inputValue}", status);
        }
    }
}
