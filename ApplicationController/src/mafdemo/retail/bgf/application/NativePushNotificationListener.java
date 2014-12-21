package mafdemo.retail.bgf.application;

import oracle.adfmf.dc.ws.rest.RestServiceAdapter;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.api.JSONBeanSerializationHelper;
import oracle.adfmf.framework.api.Model;
import oracle.adfmf.framework.event.Event;
import oracle.adfmf.framework.event.EventListener;
import oracle.adfmf.framework.exception.AdfException;

public class NativePushNotificationListener implements EventListener {
    public NativePushNotificationListener() {
        super();
    }


    public void onError(AdfException adfException) {
        TraceLog.info(getClass(), "onError", "START");
        TraceLog.info(getClass(), "onError", "ERROR: " + adfException.getMessage());
        TraceLog.info(getClass(), "onError", "END");
    }

    public void onMessage(Event event) {
        TraceLog.info(getClass(), "onMessage", "START");
        
        JSONBeanSerializationHelper jsonHelper = new JSONBeanSerializationHelper();
        
        TraceLog.info(getClass(), "onMessage", "payload: " + event.getPayload());
        
        try {
            PayloadServiceResponse payload = (PayloadServiceResponse) 
                jsonHelper.fromJSON(PayloadServiceResponse.class, event.getPayload());
        
            TraceLog.info(getClass(), "onMessage", "payload: " + payload);
            int currentBadge = AdfmfContainerUtilities.getApplicationIconBadgeNumber();
            
            if(currentBadge > 0) {
                AdfmfContainerUtilities.setApplicationIconBadgeNumber(currentBadge);        
            }
            
            TraceLog.info(getClass(), "onMessage", "payload: " + payload);
            AdfmfJavaUtilities.setELValue("#{applicationScope.payload}", payload.toString());    
            
            AdfmfJavaUtilities.setELValue("#{applicationScope.orderId}", payload.getOrderId());
            AdfmfJavaUtilities.setELValue("#{applicationScope.notified}", Boolean.TRUE);
            
            if("customer".equals(payload.getRecipientType())){
                //AdfmfContainerUtilities.gotoFeature("mafdemo.retail.bgf.PickUp");    
                AdfmfContainerUtilities.resetFeature("mafdemo.retail.bgf.PickUp", true);
            } else if("storeOwner".equals(payload.getRecipientType())) {
                //AdfmfContainerUtilities.gotoFeature("mafdemo.retail.bgf.Pack");
                AdfmfContainerUtilities.resetFeature("mafdemo.retail.bgf.Pack", true);
                
            }
        } catch(Exception e) {
            TraceLog.severe(getClass(), "onMessage", "ERROR: " + e.getMessage()) ;
        }
        
        TraceLog.info(getClass(), "onMessage", "END");
    }

    public void onOpen(String id) {
        TraceLog.info(getClass(), "onOpen", "START");
        TraceLog.info(getClass(), "onOpen", "id: " + id);
        
        AdfmfJavaUtilities.setELValue("#{applicationScope.deviceToken}", id);
        boolean registered = new RestBean().registerDeviceToken();
        TraceLog.info(getClass(), "onOpen", "registerd: " + registered);
        
        TraceLog.info(getClass(), "onOpen", "END");
    }
    

}