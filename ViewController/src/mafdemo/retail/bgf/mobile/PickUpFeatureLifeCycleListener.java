package mafdemo.retail.bgf.mobile;

import mafdemo.retail.bgf.application.RestBean;
import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.feature.LifeCycleListener;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class PickUpFeatureLifeCycleListener implements LifeCycleListener{
    
    public PickUpFeatureLifeCycleListener() {
        super();
    }

    public void deactivate() {
        // TODO Implement this method
    }

    public void activate() {
        
        Boolean notified = (Boolean) AdfmfJavaUtilities.getELValue("#{applicationScope.notified}");
        
        if(notified.booleanValue()) {
        //    AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureId(), 
        //                                                              "adf.mf.api.amx.doNavigation", 
        //                                                              new Object[] { "featureActivated" });
        }
    }
}
