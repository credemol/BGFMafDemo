package mafdemo.retail.bgf.mobile;

import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;

public class PickUpOrderDC {
    private transient ProviderChangeSupport providerChangeSupport = new ProviderChangeSupport(this);
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private PickUpHistoryRepository pickUpHistoryRepository = new PickUpHistoryRepository();
    
    private PickUpOrder[] pickUpOrders;

    public PickUpOrderDC() {
        super();
        
        reloadPickUpOrders();
    }
    
    
    private void reloadPickUpOrders() {
        String storeId = (String) AdfmfJavaUtilities.getELValue("#{applicationScope.storeId}");
        TraceLog.info(getClass(), "reloadPickUpOrders", "storeId: " + storeId);
        pickUpOrders = pickUpHistoryRepository.getPickUpOrdersByStoreId(storeId, true);        
    }
    public void refreshOrderList() {
        TraceLog.info(getClass(), "refreshOrderList", "START");
        
        reloadPickUpOrders();
        providerChangeSupport.fireProviderRefresh("pickUpOrders");

        TraceLog.info(getClass(), "refreshOrderList", "END");
    }
    
    public void setPickUpOrders(PickUpOrder[] pickUpOrders) {
        PickUpOrder[] oldPickUpOrders = this.pickUpOrders;
        this.pickUpOrders = pickUpOrders;
        propertyChangeSupport.firePropertyChange("pickUpOrders", oldPickUpOrders, pickUpOrders);
    }

    public PickUpOrder[] getPickUpOrders() {
        return pickUpOrders;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    
    public void addProviderChangeListener(ProviderChangeListener l) {
        providerChangeSupport.addProviderChangeListener(l);
    }

    public void removeProviderChangeListener(ProviderChangeListener l) {
        providerChangeSupport.removeProviderChangeListener(l);
    }
}
