package mafdemo.retail.bgf.mobile;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;

public class StoreDC {
    private transient ProviderChangeSupport providerChangeSupport = new ProviderChangeSupport(this);
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    
    private StoreRepository storeRepository = null;
    
    private Province[] provinces = null;
    private District[] districts = null;
    private Store[] stores = null;
    
    public StoreDC() {
        super();
        
        this.storeRepository = new StoreRepository();
        this.provinces = this.storeRepository.getProvinces();
        this.districts = new District[0];
        //this.stores = new Store[0];
        this.stores = storeRepository.findFavoriteStores((String) AdfmfJavaUtilities.getELValue("#{securityContext.userName}"));
    }
    
    public void initStores() {
        this.provinces = this.storeRepository.getProvinces();
        this.districts = new District[0];
        //this.stores = new Store[0];
        this.stores = storeRepository.findFavoriteStores((String) AdfmfJavaUtilities.getELValue("#{securityContext.userName}"));
        
        this.providerChangeSupport.fireProviderRefresh("districts");
        this.providerChangeSupport.fireProviderRefresh("stores");
    }
    
    public void executeFindDistricts(String provinceCode) {
        this.districts = storeRepository.findDistricts(provinceCode);
        this.providerChangeSupport.fireProviderRefresh("districts");
    }
    
    public void executeFindStores(String provinceCode, String districtCode, String storeName, 
                              boolean service01, boolean service02, boolean service03, boolean service04,
                              boolean service05, boolean service06, boolean service07, boolean service08) {
        this.stores = storeRepository.findStores(provinceCode, districtCode, storeName, service01, service02, service03, service04, service05, service06, service07, service08);
        this.providerChangeSupport.fireProviderRefresh("stores");
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

    public void setProvinces(Province[] provinces) {
        Province[] oldProvinces = this.provinces;
        this.provinces = provinces;
        propertyChangeSupport.firePropertyChange("provinces", oldProvinces, provinces);
    }

    public Province[] getProvinces() {
        return provinces;
    }

    public void setDistricts(District[] districts) {
        District[] oldDistricts = this.districts;
        this.districts = districts;
        propertyChangeSupport.firePropertyChange("districts", oldDistricts, districts);
    }

    public District[] getDistricts() {
        return districts;
    }

    public void setStores(Store[] stores) {
        Store[] oldStores = this.stores;
        this.stores = stores;
        propertyChangeSupport.firePropertyChange("stores", oldStores, stores);
    }

    public Store[] getStores() {
        return stores;
    }

}
