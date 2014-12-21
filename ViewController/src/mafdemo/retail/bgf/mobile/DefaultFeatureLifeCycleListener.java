package mafdemo.retail.bgf.mobile;

import mafdemo.retail.bgf.application.RestBean;
import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.feature.LifeCycleListener;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class DefaultFeatureLifeCycleListener implements LifeCycleListener{
    private boolean deviceTokenRegistered = false;
    
    public DefaultFeatureLifeCycleListener() {
        super();
    }

    public void activate() {
        // TODO Implement this method
        if(this.deviceTokenRegistered == false) {
            deviceTokenRegistered = new RestBean().registerDeviceToken();
            TraceLog.info(getClass(), "start", "registered: " + deviceTokenRegistered) ;
        }


        Object userNameObj = AdfmfJavaUtilities.getELValue("#{securityContext.userName}");
        TraceLog.info(getClass(), "activate", "userNameObj: " + userNameObj);
        
        String userName = null;
        if(userNameObj != null) {
            TraceLog.info(getClass(), "activate", "userNameObj class: " + userNameObj.getClass().getName());
            userName = userNameObj.toString();
        }
        
        TraceLog.info(getClass(), "activate", "userName: " + userName) ;
        
        if(userName != null && userName.trim().length() > 0) {
            //TraceLog.info(getClass(), "activate", "debug 1");
            //AdfmfJavaUtilities.setELValue("#{applicationScope.userName}", userName);
            AdfmfJavaUtilities.setELValue("#{applicationScope.authenticated}", Boolean.TRUE);
            //TraceLog.info(getClass(), "activate", "debug 2");
            String isCustomerRole = (String) AdfmfJavaUtilities.getELValue("#{securityContext.userInRole['bgf_customer']}");
            //TraceLog.info(getClass(), "activate", "debug 3");
            TraceLog.info(getClass(), "activate", "isCustomerRole: " + isCustomerRole) ;
            //TraceLog.info(getClass(), "activate", "debug 4");
            AdfmfJavaUtilities.setELValue("#{applicationScope.isCustomerRole}", new Boolean("true".equals(isCustomerRole)));
            //TraceLog.info(getClass(), "activate", "debug 5");
            
            String isStoreOwnerRole = (String) AdfmfJavaUtilities.getELValue("#{securityContext.userInRole['bgf_store_owner']}");
            //TraceLog.info(getClass(), "activate", "debug 6");
            TraceLog.info(getClass(), "activate", "isStoreOwnerRole: " + isStoreOwnerRole);
            //TraceLog.info(getClass(), "activate", "debug 7");
            AdfmfJavaUtilities.setELValue("#{applicationScope.isStoreOwnerRole}", new Boolean("true".equals(isStoreOwnerRole)));
            //TraceLog.info(getClass(), "activate", "debug 8");
        }      
    }

    public void deactivate() {
        // TODO Implement this method
    }
}
