package mafdemo.retail.bgf.mobile;

import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class AuthBean {
    public AuthBean() {
        super();
        
    }

    public void logout(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "logout", "START");

        AdfmfJavaUtilities.setELValue("#{applicationScope.authenticated}", Boolean.FALSE);
        AdfmfJavaUtilities.setELValue("#{applicationScope.isCustomerRole}", Boolean.FALSE);
        AdfmfJavaUtilities.setELValue("#{applicationScope.isStoreOwnerRole}", Boolean.FALSE);
        
        AdfmfJavaUtilities.logout();
        
        AdfmfContainerUtilities.resetFeature("mafdemo.retail.bgf.CU", true);
        //AdfmfContainerUtilities.gotoFeature("mafdemo.retail.bgf.CU");
        
        TraceLog.info(getClass(), "logout", "END");
    }
}
