package mafdemo.retail.bgf.mobile;

import javax.el.ValueExpression;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.bindings.dbf.AmxAccessorIteratorBinding;
import oracle.adfmf.bindings.iterator.BasicIterator;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.internal.AdfmfJavaUtilitiesInternal;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;


// this class is deprecated. see PickUpStoreBean
public class StoreSearchBean {
    private String provinceIndex = "";
    private String districtIndex = "";
    
    private boolean service01;
    private boolean service02;
    private boolean service03;
    private boolean service04;
    private boolean service05;
    private boolean service06;
    private boolean service07;
    private boolean service08;
    
    private String storeName;
    
    private String selectedStoreId;
    
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public StoreSearchBean() {
    }

    public void setProvinceIndex(String provinceIndex) {
        String oldProvinceIndex = this.provinceIndex;
        this.provinceIndex = provinceIndex;
        propertyChangeSupport.firePropertyChange("provinceIndex", oldProvinceIndex, provinceIndex);
    }

    public String getProvinceIndex() {
        return provinceIndex;
    }

    public void setDistrictIndex(String districtIndex) {
        String oldDistrictIndex = this.districtIndex;
        this.districtIndex = districtIndex;
        propertyChangeSupport.firePropertyChange("districtIndex", oldDistrictIndex, districtIndex);
    }

    public String getDistrictIndex() {
        return districtIndex;
    }

    public void selectProvince(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "selectProvince", "START");
        Object newValue = valueChangeEvent.getNewValue();
        Object oldValue = valueChangeEvent.getOldValue();
        if(newValue == null) {
            TraceLog.info(getClass(), "selectProvince", "newValue is null");
        } else {
            TraceLog.info(getClass(), "selectProvince", "newValue: " + newValue.getClass() + " - " + newValue);    
        }
        if(oldValue == null) {
            TraceLog.info(getClass(), "selectProvince", "oldValue is null");
        } else {
            TraceLog.info(getClass(), "selectProvince", "oldValue: " + oldValue.getClass() + " - " + oldValue);    
        }
        
        if(newValue != null) {
            this.setProvinceIndex(newValue.toString());
        }
        //
        //StoreList.provinceList.get(Integer.parseInt(newValue.toString()));
        
        String provincesInputValue = (String) AdfmfJavaUtilities.getELValue("#{bindings.provinces.inputValue}");
        TraceLog.info(getClass(), "selectProvince", provincesInputValue);
        AdfmfJavaUtilities.setELValue("#{bindings.provinceCode.inputValue}", provincesInputValue);
        
        //String provinceCode = StoreList.PROVINCES[Integer.parseInt(newValue.toString())];
        //TraceLog.info(getClass(), "provinceCode", provinceCode);
        //AdfmfJavaUtilities.setELValue("#{bindings.provinceCode.inputValue}", provinceCode);
        
        TraceLog.info(getClass(), "selectProvince", "Completed successfully");
    }
    
    public void selectDistrict(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "selectDistrict", "START");
        Object newValue = valueChangeEvent.getNewValue();
        if(newValue != null) {
            this.setDistrictIndex(newValue.toString());
        }
        
        String districtsInputValue = (String) AdfmfJavaUtilities.getELValue("#{bindings.districts.inputValue}");
        TraceLog.info(getClass(), "selectDistrict", "districtsInputValue: " + districtsInputValue);
        AdfmfJavaUtilities.setELValue("#{bindings.districtCode.inputValue}", districtsInputValue);
        
        TraceLog.info(getClass(), "selectDistrict", "END");
    }

    public void setService01(boolean service01) {
        boolean oldService01 = this.service01;
        this.service01 = service01;
        propertyChangeSupport.firePropertyChange("service01", oldService01, service01);
    }

    public boolean isService01() {
        return service01;
    }

    public void setService02(boolean service02) {
        boolean oldService02 = this.service02;
        this.service02 = service02;
        propertyChangeSupport.firePropertyChange("service02", oldService02, service02);
    }

    public boolean isService02() {
        return service02;
    }

    public void setService03(boolean service03) {
        boolean oldService03 = this.service03;
        this.service03 = service03;
        propertyChangeSupport.firePropertyChange("service03", oldService03, service03);
    }

    public boolean isService03() {
        return service03;
    }

    public void setService04(boolean service04) {
        boolean oldService04 = this.service04;
        this.service04 = service04;
        propertyChangeSupport.firePropertyChange("service04", oldService04, service04);
    }

    public boolean isService04() {
        return service04;
    }

    public void setService05(boolean service05) {
        boolean oldService05 = this.service05;
        this.service05 = service05;
        propertyChangeSupport.firePropertyChange("service05", oldService05, service05);
    }

    public boolean isService05() {
        return service05;
    }

    public void setService06(boolean service06) {
        boolean oldService06 = this.service06;
        this.service06 = service06;
        propertyChangeSupport.firePropertyChange("service06", oldService06, service06);
    }

    public boolean isService06() {
        return service06;
    }

    public void setService07(boolean service07) {
        boolean oldService07 = this.service07;
        this.service07 = service07;
        propertyChangeSupport.firePropertyChange("service07", oldService07, service07);
    }

    public boolean isService07() {
        return service07;
    }

    public void setService08(boolean service08) {
        boolean oldService08 = this.service08;
        this.service08 = service08;
        propertyChangeSupport.firePropertyChange("service08", oldService08, service08);
    }

    public boolean isService08() {
        return service08;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public void toggleService01(ActionEvent actionEvent) {
        // Add event code here...
        setService01(! isService01());
    }
    public void toggleService02(ActionEvent actionEvent) {
        // Add event code here...
        setService02(! isService02());
    }
    public void toggleService03(ActionEvent actionEvent) {
        // Add event code here...
        setService03(! isService03());
    }

    public void toggleService04(ActionEvent actionEvent) {
        // Add event code here...
        setService04(! isService04());
    }
    public void setStoreName(String storeName) {
        String oldStoreName = this.storeName;
        this.storeName = storeName;
        propertyChangeSupport.firePropertyChange("storeName", oldStoreName, storeName);
    }

    public String getStoreName() {
        return storeName;
    }

    public void searchStore(ActionEvent actionEvent) {
        // Add event code here...
        ValueExpression ve = AdfmfJavaUtilities.getValueExpression("#{bindings.storesIterator}", Object.class);
        AmxAccessorIteratorBinding aib = (AmxAccessorIteratorBinding) ve.getValue(AdfmfJavaUtilities.getAdfELContext());
        BasicIterator bi = (BasicIterator) aib.getIterator();
        bi.refresh(true);
        
    }

    public void setSelectedStoreId(String selectedStoreId) {
        String oldSelectedStoreId = this.selectedStoreId;
        this.selectedStoreId = selectedStoreId;
        propertyChangeSupport.firePropertyChange("selectedStoreId", oldSelectedStoreId, selectedStoreId);
    }

    public String getSelectedStoreId() {
        return selectedStoreId;
    }


    public void selectStore(ActionEvent actionEvent) {
        
    }


}
