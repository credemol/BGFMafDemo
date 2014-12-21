package mafdemo.retail.bgf.mobile;

import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.amx.event.ActionEvent ;
import oracle.adfmf.framework.api.AdfmfSlidingWindowOptions ;
import oracle.adfmf.framework.api.AdfmfSlidingWindowUtilities ;
import oracle.adfmf.java.beans.PropertyChangeListener ;
import oracle.adfmf.java.beans.PropertyChangeSupport ;


public class CustomSpringboardBean {

    private String springboardWindow;

    private boolean springboardToggleFlag;

    public CustomSpringboardBean() {

    }
    public void setSpringboardToggleFlag(boolean springboardToggleFlag) {
        boolean oldSpringboardToggleFlag = this.springboardToggleFlag;
        this.springboardToggleFlag = springboardToggleFlag;
        propertyChangeSupport.firePropertyChange("springboardToggleFlag", oldSpringboardToggleFlag,
                                                 springboardToggleFlag);
    }

    public boolean isSpringboardToggleFlag() {
        return springboardToggleFlag;
    }

    public void setPropertyChangeSupport(PropertyChangeSupport propertyChangeSupport) {
        PropertyChangeSupport oldPropertyChangeSupport = this.propertyChangeSupport;
        this.propertyChangeSupport = propertyChangeSupport;
        propertyChangeSupport.firePropertyChange("propertyChangeSupport", oldPropertyChangeSupport,
                                                 propertyChangeSupport);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void setSpringboardWindow(String springboardWindow) {
        Object oldSpringboardWindow = this.springboardWindow;
        this.springboardWindow = springboardWindow;
        propertyChangeSupport.firePropertyChange("springboardWindow", oldSpringboardWindow, springboardWindow);
    }

    public String getSpringboardWindow() {
        return springboardWindow;
    }

    public void showSpringboard(ActionEvent actionEvent) {
        TraceLog.info(getClass(), "showSpringboard", "START");
        
        springboardToggleFlag = !springboardToggleFlag;
        
        TraceLog.info(getClass(), "showSpringboard", "springboardToggleFlag: " + springboardToggleFlag);
        TraceLog.info(getClass(), "showSpringboard", "springboardWindow: " + springboardWindow);
        
        if (springboardWindow == null) {
            // featureId
            springboardWindow = AdfmfSlidingWindowUtilities.create("mafdemo.retail.bgf.SpringBoard"); //slidingWindowContent
            this.setSpringboardWindow(springboardWindow);
        }

        AdfmfSlidingWindowOptions windowOptions = new AdfmfSlidingWindowOptions();

        windowOptions.setDirection(AdfmfSlidingWindowOptions.DIRECTION_LEFT);
        windowOptions.setStyle(AdfmfSlidingWindowOptions.STYLE_PINNED);
        if (springboardToggleFlag) {
            windowOptions.setSize("60%");
            AdfmfSlidingWindowUtilities.show(springboardWindow, windowOptions);
        } else {
            AdfmfSlidingWindowUtilities.hide(springboardWindow);
        }
        TraceLog.info(getClass(), "showSpringboard", "END");
    }


    public void hideSpringboard(ActionEvent actionEvent) {
        springboardToggleFlag = !springboardToggleFlag;
        String springboardWindow = this.getSpringboardWindow();
        AdfmfSlidingWindowUtilities.hide(springboardWindow);
    }


    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public boolean isFeatureRendered(String featureId) {
        return true;
        // #{row.id == 'mafdemo.retail.bgf.CU' || (row.id == 'mafdemo.retail.bgf.PickUp' &amp;&amp; securityContext.user.roles contains 'bgf_customer' ) || (row.id == 'mafdemo.retail.bgf.Pack' &amp;&amp; securityContext.user.roles contains 'bgf_store_owner')}
    }

    public void showCustomSpringboard(ActionEvent actionEvent) {
        TraceLog.info(getClass(), "showCustomSpringboard", "START");
        // Add event code here...
        
        showSpringboard(actionEvent);
        
        TraceLog.info(getClass(), "showCustomSpringboard", "END");
    }
}
