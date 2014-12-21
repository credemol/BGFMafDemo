package mafdemo.retail.bgf.mobile;

import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class Store {
    private String id;
    private String name;
    private String telNo = "";
    private String oldAddress = "";
    private String newAddress = "";
    private boolean service01;      // 24 Hour
    private boolean service02;      // delivery
    private boolean service03;      // bakery
    private boolean service04;      // fried
    private boolean service05;      // coffee
    private boolean service06;      // lotto
    private boolean service07;      // toto
    private boolean service08;      // cash.
    private int provinceDistrictId;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Store() {
        super();
    }


    public Store(String id, String name, String telNo, String oldAddress, String newAddress) {
        super();
        this.id = id;
        this.name = name;
        this.telNo = telNo;
        this.oldAddress = oldAddress;
        this.newAddress = newAddress;
    }

    public Store(String id, String name, String telNo, String oldAddress, String newAddress, boolean service01,
                 boolean service02, boolean service03, boolean service04, boolean service05, boolean service06,
                 boolean service07, boolean service08) {
        super();
        this.id = id;
        this.name = name;
        this.telNo = telNo;
        this.oldAddress = oldAddress;
        this.newAddress = newAddress;
        this.service01 = service01;
        this.service02 = service02;
        this.service03 = service03;
        this.service04 = service04;
        this.service05 = service05;
        this.service06 = service06;
        this.service07 = service07;
        this.service08 = service08;
    }

    public void setId(String id) {
        String oldId = this.id;
        this.id = id;
        propertyChangeSupport.firePropertyChange("id", oldId, id);
    }

    public String getId() {
        return id;
    }
    
    public String getKey() {
        return id;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange("name", oldName, name);
    }

    public String getName() {
        return name;
    }

    public void setTelNo(String telNo) {
        String oldTelNo = this.telNo;
        this.telNo = telNo;
        propertyChangeSupport.firePropertyChange("telNo", oldTelNo, telNo);
    }

    public String getTelNo() {
        return telNo;
    }

    public void setOldAddress(String oldAddress) {
        String oldOldAddress = this.oldAddress;
        this.oldAddress = oldAddress;
        propertyChangeSupport.firePropertyChange("oldAddress", oldOldAddress, oldAddress);
    }

    public String getOldAddress() {
        return oldAddress;
    }

    public void setNewAddress(String newAddress) {
        String oldNewAddress = this.newAddress;
        this.newAddress = newAddress;
        propertyChangeSupport.firePropertyChange("newAddress", oldNewAddress, newAddress);
    }

    public String getNewAddress() {
        return newAddress;
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

    public void setProvinceDistrictId(int provinceDistrictId) {
        int oldProvinceDistrictId = this.provinceDistrictId;
        this.provinceDistrictId = provinceDistrictId;
        propertyChangeSupport.firePropertyChange("provinceDistrictId", oldProvinceDistrictId, provinceDistrictId);
    }

    public int getProvinceDistrictId() {
        return provinceDistrictId;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    
    public String getServiceIcons() {
        StringBuffer sb = new StringBuffer();
        
        //sb.append("<address style='display:block; overflow:hidden; font-style:normal; margin-bottom:10px;'");
        sb.append("<ul style='overflow:hidden;'>");

        sb.append("<li style='display:block; overflow:hidden; float:left; margin-left:10px; height:27px;'>");
        sb.append("<img src='../www/images/small_01_hour.png' ");
        sb.append(service01 ? "style='margin-top:-28px;'/>" : "/>").append("</li>");
        
        sb.append("<li style='display:block; overflow:hidden; float:left; margin-left:10px; height:27px;'>");
        sb.append("<img src='../../www/images/small_02_delivery.png' ");
        sb.append(service02 ? "style='margin-top:-28px;'/>" : "/>").append("</li>");

        sb.append("<li style='display:block; overflow:hidden; float:left; margin-left:10px; height:27px;'>");
        sb.append("<img src='../../../www/images/small_03_bakery.png' ");
        sb.append(service03 ? "style='margin-top:-28px;'/>" : "/>").append("</li>");

        sb.append("<li style='display:block; overflow:hidden; float:left; margin-left:10px; height:27px;'>");
        sb.append("<img src='../../../../www/images/small_04_fried.png' ");
        sb.append(service04 ? "style='margin-top:-28px;'/>" : "/>").append("</li>");

        sb.append("<li style='display:block; overflow:hidden; float:left; margin-left:10px; height:27px;'>");
        sb.append("<img src='../../../../../www/images/small_05_coffee.png' ");
        sb.append(service05 ? "style='margin-top:-28px;'/>" : "/>").append("</li>");

        sb.append("<li style='display:block; overflow:hidden; float:left; margin-left:10px; height:27px;'>");
        sb.append("<img src='../../../../../../www/images/small_06_lotto.png' ");
        sb.append(service06 ? "style='margin-top:-28px;'/>" : "/>").append("</li>");

        sb.append("<li style='display:block; overflow:hidden; float:left; margin-left:10px; height:27px;'>");
        sb.append("<img src='../../../../../../../www/images/small_07_toto.png' ");
        sb.append(service07 ? "style='margin-top:-28px;'/>" : "/>").append("</li>");

        sb.append("<li style='display:block; overflow:hidden; float:left; margin-left:10px; height:27px;'>");
        sb.append("<img src='../images/small_08_cash.png' ");
        sb.append(service08 ? "style='margin-top:-28px;'/>" : "/>").append("</li>");
        
        
        sb.append("</ul>");
        //sb.append("</address>");

        TraceLog.info(getClass(), "getServiceIcons", "[" + sb.toString() + "]");
        return sb.toString();
    }
    

}
