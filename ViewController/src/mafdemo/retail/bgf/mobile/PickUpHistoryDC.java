package mafdemo.retail.bgf.mobile;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;

public class PickUpHistoryDC {
    private transient ProviderChangeSupport providerChangeSupport = new ProviderChangeSupport(this);
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private PickUpHistoryRepository pickUpHistoryRepository = new PickUpHistoryRepository();
    
    private PickUpOrder[] pickUpOrders;

    public PickUpHistoryDC() {
        super();
        
        pickUpOrders = pickUpHistoryRepository.getPickUpOrders(true);
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
