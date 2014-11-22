package mafdemo.retail.bgf.mobile;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class District {
    private String code;
    private String name;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public District() {
        super();
    }

    public District(String code, String name) {
        super();
        this.code = code;
        this.name = name;
    }

    public String getKey() {
        return code;
    }
    public void setCode(String code) {
        String oldCode = this.code;
        this.code = code;
        propertyChangeSupport.firePropertyChange("code", oldCode, code);
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange("name", oldName, name);
    }

    public String getName() {
        return name;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
}
