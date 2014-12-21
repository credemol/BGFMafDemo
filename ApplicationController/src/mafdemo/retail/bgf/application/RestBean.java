package mafdemo.retail.bgf.application;

import oracle.adfmf.dc.ws.rest.RestServiceAdapter;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.api.Model;

public class RestBean {
    public RestBean() {
        super();
    }
    
    public boolean registerDeviceToken() {
        TraceLog.info(getClass(), "registerDeviceToken", "START");

        String userId = (String) AdfmfJavaUtilities.getELValue("#{securityContext.userName}");
        TraceLog.info(getClass(), "registerDeviceToken", "userId: " + userId);
        
        String osType = (String) AdfmfJavaUtilities.getELValue("#{deviceScope.device.os}");
        TraceLog.info(getClass(), "registerDeviceToken", "osType: " + osType);
        
        String deviceToken = (String) AdfmfJavaUtilities.getELValue("#{applicationScope.deviceToken}");
        TraceLog.info(getClass(), "registerDeviceToken", "deviceToken: " + deviceToken);
        
        
        if(userId == null || userId.trim().length() == 0) {
            return false;
        }
        if(osType == null || osType.trim().length() == 0) {
            return false;
        }
        if(deviceToken == null || deviceToken.trim().length() == 0) {
            return false;
        }
        
        RestServiceAdapter restServiceAdapter = Model.createRestServiceAdapter();
        restServiceAdapter.clearRequestProperties();
        
        restServiceAdapter.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        restServiceAdapter.addRequestProperty("Accept", "text/plain");

        restServiceAdapter.setConnectionName("bgf_demo_server");
        
        restServiceAdapter.setRequestType(RestServiceAdapter.REQUEST_TYPE_POST);
        
        restServiceAdapter.setRequestURI("/resources/pushserver");
        
        try {
            restServiceAdapter.setRetryLimit(0);
            

            StringBuffer reqBody = new StringBuffer();            
            reqBody.append("userId=").append(userId);
            reqBody.append("&osType=").append(osType);
            reqBody.append("&deviceToken=").append(deviceToken);
            
            String resJson = restServiceAdapter.send(reqBody.toString());;
            TraceLog.info(getClass(), "registerDeviceToken", "resJson: " + resJson);
            
            return "true".equals(resJson);
            
        } catch(Exception e) {
            TraceLog.severe(getClass(), "registerDeviceToken", "ERROR: " + e.getMessage());
        }
        return false;
    }    
}
