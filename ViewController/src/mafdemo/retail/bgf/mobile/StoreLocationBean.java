package mafdemo.retail.bgf.mobile;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class StoreLocationBean {
    private Store store;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private StoreRepository storeRepository = new StoreRepository();
    
    public StoreLocationBean() {
        super();
    }

    /*
    public void selectStore(String storeId) {
        TraceLog.info(getClass(), "selectStore", "START - storeId: " + storeId);
        Store store = storeRepository.findStore(storeId);
        
        TraceLog.info(getClass(), "selectStore", "store: " + store);
        if(store != null) {
            setStore(store);
        }
        TraceLog.info(getClass(), "selectStore", "END");
    }
*/
    
    public void setStore(Store store) {
        Store oldStore = this.store;
        this.store = store;
        propertyChangeSupport.firePropertyChange("store", oldStore, store);
    }

    public Store getStore() {
        return store;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public void selectStore(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "selectStore", "START");
        
        // #{pageFlowScope.locationStoreId}
        String storeId = (String) AdfmfJavaUtilities.getELValue("#{pageFlowScope.locationStoreId}");
        
        Store store = storeRepository.findStore(storeId);
        
        
        TraceLog.info(getClass(), "selectStore", "store: " + store);
        if(store != null) {
            setStore(store);
        }
        TraceLog.info(getClass(), "selectStore", "END");
    }
}
