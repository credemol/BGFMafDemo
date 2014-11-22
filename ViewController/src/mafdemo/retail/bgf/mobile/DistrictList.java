package mafdemo.retail.bgf.mobile;

import com.sun.util.logging.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.el.ValueExpression;

import mafdemo.retail.bgf.application.DBConnectionFactory;

import oracle.adfmf.bindings.dbf.AmxIteratorBinding;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.util.Utility;
import oracle.adfmf.util.logging.Trace;


// This class is deprecated.
public class DistrictList {
    private String provinceCode;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public DistrictList() {
        super();
    }

    public District[] getDistricts() {
        Trace.log(Utility.ApplicationLogger, Level.SEVERE, StoreList.class, "getDistricts",
                  "############## getDestricts() start. provinceCode=[" + this.provinceCode + "]");
        if(this.provinceCode == null) {
            return new District[0];
        }
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        final String sql = "SELECT DISTRICT_CODE from PROVINCE_DISTRICT where PROVINCE_CODE = ?";
        List districts = new ArrayList();
        try {
            conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, provinceCode);
            
            rs = stmt.executeQuery();
            while(rs.next()) {
                String district = rs.getString("DISTRICT_CODE");
                districts.add(new District(district, district));
            }
        } catch(SQLException e) {
            Trace.log(Utility.ApplicationLogger, Level.SEVERE, StoreList.class, "getDistricts",
                      "############## Exception:  " + e.getMessage());

        } finally {
            try {
                if(rs != null) {
                    rs.close();
                }
            } catch(Exception e) {
            }
            try {
                if(stmt != null) {
                    stmt.close();
                }
            } catch(Exception e) {
            }
        }

        Trace.log(Utility.ApplicationLogger, Level.SEVERE, StoreList.class, "getDistricts",
                  "############## getDestricts() is completed.  ");

        
        return (District[]) districts.toArray(new District[districts.size()]);
    }
    public void setProvinceCode(String provinceInd) {
        Trace.log(Utility.ApplicationLogger, Level.SEVERE, StoreList.class, "setProvinceCode",
                  "############## setProvinceCode() start. provinceCode=[" + provinceCode + "]");
        String oldProvinceCode = this.provinceCode;
        this.provinceCode = provinceCode;
        propertyChangeSupport.firePropertyChange("provinceCode", oldProvinceCode, provinceCode);
        
        ValueExpression veIter1 = 
            (ValueExpression)AdfmfJavaUtilities.getValueExpression("#{bindings.districtsIterator}", Object.class);

        Trace.log(Utility.ApplicationLogger, Level.SEVERE, StoreList.class, "setProvinceCode",
                  "############## setProvinceCode() veIter1: " + veIter1);
        
        AmxIteratorBinding iteratorBinding1 = (AmxIteratorBinding)veIter1.getValue(AdfmfJavaUtilities.getAdfELContext());

        iteratorBinding1.getIterator().refresh();
        
        Trace.log(Utility.ApplicationLogger, Level.SEVERE, StoreList.class, "setProvinceCode",
                  "############## setProvinceCode() is completed. provinceCode=[" + provinceCode + "]");

    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
}
