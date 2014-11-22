package mafdemo.retail.bgf.mobile;

import com.sun.util.logging.Level;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mafdemo.retail.bgf.application.DBConnectionFactory;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;
import oracle.adfmf.util.Utility;
import oracle.adfmf.util.logging.Trace;

// this class is deprecated.
public class ProductList {
    private transient ProviderChangeSupport providerChangeSupport = new ProviderChangeSupport(this);
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public ProductList() {
        super();
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
    
    public ProductCategory[] getProductCategories() {
        TraceLog.info(getClass(), "getProductCategories", "START");
        List list = new ArrayList();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        final String sql =  "SELECT ID, NAME FROM PRODUCT_CATEGORY";
        
        
        try {
            conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()) {
                list.add(new ProductCategory(rs.getString("ID"), rs.getString("NAME")));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            TraceLog.severe(getClass(), "getProductCategories", "ERROR: " + e.getMessage());
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch(Exception e) {}
            }
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(Exception e) {}
            }
        }
        
        TraceLog.info(getClass(), "getProductCategories", "Completed successfully");
        return (ProductCategory[]) list.toArray(new ProductCategory[list.size()]);
    }
}
