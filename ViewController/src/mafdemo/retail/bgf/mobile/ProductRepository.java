package mafdemo.retail.bgf.mobile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Set;

import mafdemo.retail.bgf.application.DBConnectionFactory;

import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;

public class ProductRepository {

    
    public ProductRepository() {
        super();
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
    
    public Product[] findProducts(String categoryId, String productName) {
        TraceLog.info(getClass(), "findProducts", "START");
        TraceLog.info(getClass(), "findProducts", "categoryId: " + categoryId + ", productName: [" + productName + "]");
        
        boolean handleProductName = (productName != null && productName.trim().length() > 0);
        
        String sql = "SELECT P.ID, P.NAME, P.IMAGE, P.PRICE, P.CATEGORY_ID FROM PRODUCT P WHERE P.CATEGORY_ID = ? ";
        
        if(handleProductName) {
            sql += " AND P.NAME LIKE ?";
        }

        TraceLog.info(getClass(), "findProducts", "sql: " + sql) ;
        
        Connection conn = null;    
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List list = new ArrayList();
        try {
            conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, categoryId);
            if(handleProductName) {
                stmt.setString(2, "%" + productName.trim() + "%");
            }
            rs = stmt.executeQuery();
            
            while(rs.next()) {
                Product product = new Product(rs.getString("ID"), rs.getString("CATEGORY_ID"), 
                                        rs.getString("NAME"), rs.getInt("PRICE"), rs.getString("IMAGE"));  
                list.add(product);
                
            }
        } catch(SQLException e) {
            e.printStackTrace();
            TraceLog.severe(getClass(), "findProducts", e.getMessage());
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
        TraceLog.info(getClass(), "findProducts", list.size() + " products are found.");
        TraceLog.info(getClass(), "findProducts", "END");
        
        return (Product[]) list.toArray(new Product[list.size()]);
    }
    
    public Product[] findProductsByIdSet(Set keySet) {
        TraceLog.info(getClass(), "findProductsByIdSet", "START");
        TraceLog.info(getClass(), "findProductsByIdSet", "keySet: " + keySet);
        
        if(keySet == null || keySet.isEmpty()) {
            return new Product[0];
        }
        
        StringBuffer whereClause = new StringBuffer();
        for(Iterator iterator = keySet.iterator(); iterator.hasNext(); ) {
            String productId = (String) iterator.next();
            if(whereClause.length() > 0) {
                whereClause.append(", ");
            }
            whereClause.append("'").append(productId).append("'");
        }
        
        String sql = "SELECT P.ID, P.NAME, P.IMAGE, P.PRICE, P.CATEGORY_ID FROM PRODUCT P WHERE P.ID IN (" + whereClause.toString() + ")";

        TraceLog.info(getClass(), "findProductsByIdSet", "sql: " + sql);        
        Connection conn = null;    
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List list = new ArrayList();
        try {
            conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();
            
            while(rs.next()) {
                Product product = new Product(rs.getString("ID"), rs.getString("CATEGORY_ID"), 
                                        rs.getString("NAME"), rs.getInt("PRICE"), rs.getString("IMAGE"));  
                list.add(product);
                
            }
        } catch(SQLException e) {
            e.printStackTrace();
            TraceLog.severe(getClass(), "findProductsByIdSet", e.getMessage());
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

        TraceLog.info(getClass(), "findProductsByIdSet", "END");
        
        return (Product[]) list.toArray(new Product[list.size()]);
    }
    

    
    public Product findProduct(String productId) {
        TraceLog.info(getClass(), "findProduct", "START");
        TraceLog.info(getClass(), "findProducts", "productId: " + productId);

        if(productId == null || productId.trim().length() == 0) {
            return null;
        }
        
        String sql = "SELECT P.ID, P.NAME, P.IMAGE, P.PRICE, P.CATEGORY_ID FROM PRODUCT P WHERE P.ID = ? ";
        
        Connection conn = null;    
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, productId);

            rs = stmt.executeQuery();
            
            while(rs.next()) {
                return new Product(rs.getString("ID"), rs.getString("CATEGORY_ID"), 
                                        rs.getString("NAME"), rs.getInt("PRICE"), rs.getString("IMAGE"));  
                
            }
        } catch(SQLException e) {
            e.printStackTrace();
            TraceLog.severe(getClass(), "findProducts", e.getMessage());
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

        TraceLog.info(getClass(), "findProducts", "Can'f find Product!!!");
        
        return null;        
    }

    public Product[] findFavoriteProducts(String customerId) {
        TraceLog.info(getClass(), "findFavoriteProducts", "START - customerId: " + customerId);
        
        String sql = "SELECT P.ID, P.NAME, P.IMAGE, P.PRICE, P.CATEGORY_ID FROM PRODUCT P WHERE ID IN (SELECT DISTINCT(I.PRODUCT_ID) FROM PICKUP_ORDER_ITEM I, PICKUP_ORDER O WHERE O.ID = I.ORDER_ID AND O.CUSTOMER_ID = ?)";


        TraceLog.info(getClass(), "findFavoriteProducts", "sql: " + sql) ;
        
        Connection conn = null;    
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List list = new ArrayList();
        try {
            conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, customerId);

            rs = stmt.executeQuery();
            
            while(rs.next()) {
                Product product = new Product(rs.getString("ID"), rs.getString("CATEGORY_ID"), 
                                        rs.getString("NAME"), rs.getInt("PRICE"), rs.getString("IMAGE"));  
                list.add(product);
                
            }
        } catch(SQLException e) {
            e.printStackTrace();
            TraceLog.severe(getClass(), "findFavoriteProducts", e.getMessage());
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
        TraceLog.info(getClass(), "findFavoriteProducts", list.size() + " products are found.");
        TraceLog.info(getClass(), "findFavoriteProducts", "END");
        
        return (Product[]) list.toArray(new Product[list.size()]);        
    }
}
