package mafdemo.retail.bgf.mobile;

import oracle.adfmf.feature.LifeCycleListener;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class LogoutFeatureLifeCycleListener implements LifeCycleListener {
    public LogoutFeatureLifeCycleListener() {
        super();
    }


    public void activate() {
        // TODO Implement this method
        AdfmfJavaUtilities.logout();
    }

    public void deactivate() {
        // TODO Implement this method
    }
}
