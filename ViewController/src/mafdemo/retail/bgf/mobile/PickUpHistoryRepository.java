package mafdemo.retail.bgf.mobile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mafdemo.retail.bgf.application.DBConnectionFactory;
import mafdemo.retail.bgf.application.TraceLog;
import mafdemo.retail.bgf.mobile.restful.PickupOrderItemWrapper;
import mafdemo.retail.bgf.mobile.restful.PickupOrderWrapper;

public class PickUpHistoryRepository {
    public PickUpHistoryRepository() {
        super();
    }
    
    public PickUpOrder[] getPickUpOrders(boolean includeItems) {
        TraceLog.info(getClass(), "getPickUpOrders", "START");
        
        
        List list = new ArrayList();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        final String sql = "SELECT O.ID, O.STATUS, O.CUSTOMER_ID, O.CUSTOMER_NAME, O.STORE_ID, S.NAME STORE_NAME, " +
            "O.ORDER_DATE, O.PICKUP_DATE, O.TOTAL_PRICE, S.OLD_ADDRESS, S.NEW_ADDRESS " +
            " FROM PICKUP_ORDER O, STORE S WHERE O.STORE_ID = S.ID ORDER BY ORDER_DATE DESC";

        TraceLog.info(getClass(), "getPickUpOrders", "SQL: " + sql);
        try {
            conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            
            while(rs.next()) {
                PickUpOrder order = new PickUpOrder(rs.getString("ID"), rs.getString("STATUS"), rs.getString("CUSTOMER_ID"),
                                                    rs.getString("STORE_ID"), null, null, 
                                                    rs.getInt("TOTAL_PRICE"), rs.getString("STORE_NAME"));
                //TraceLog.info(getClass(), "getPickUpOrders", "order_date: " + rs.getTime("ORDER_DATE"));
                //TraceLog.info(getClass(), "getPickUpOrders", "order_date: " + rs.getTimestamp("ORDER_DATE"));
                //TraceLog.info(getClass(), "getPickUpOrders", "order_date: " + rs.getDate("ORDER_DATE"));
                Timestamp pickupDate = rs.getTimestamp("PICKUP_DATE");
                Timestamp orderDate = rs.getTimestamp("ORDER_DATE");
                
                if(pickupDate != null) {
                    order.setPickupDate(new Date(pickupDate.getTime()));
                }
                if(orderDate != null) {
                    order.setOrderDate(new Date(orderDate.getTime()));
                }
                order.setStoreOldAddress(rs.getString("OLD_ADDRESS"));
                order.setStoreNewAddress(rs.getString("NEW_ADDRESS"));
                order.setCustomerName(rs.getString("CUSTOMER_NAME"));
                //order.setTotalPrice(rs.getInt("TOTAL_PRICE"));
                
                list.add(order);
                TraceLog.info(getClass(), "getPickUpOrders", "order: " + order);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            TraceLog.severe(getClass(), "getPickUpOrders", e.getMessage());
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
        
        if(includeItems) {
            for(int i = 0, len = list.size(); i < len; i++) {
                PickUpOrder order = (PickUpOrder) list.get(i);
                order.setProducts(findProductsByOrderId(order.getId()));
            }
        }
        return (PickUpOrder[]) list.toArray(new PickUpOrder[list.size()]);
    }
    
    
    public PickUpOrder[] getPickUpOrdersByStoreId(String storeId, boolean includeItems) {
        TraceLog.info(getClass(), "getPickUpOrders", "START");
        
        
        List list = new ArrayList();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        final String sql = "SELECT O.ID, O.STATUS, O.CUSTOMER_ID, O.CUSTOMER_NAME, O.STORE_ID, S.NAME STORE_NAME, " +
            "O.ORDER_DATE, O.PICKUP_DATE, O.TOTAL_PRICE, S.OLD_ADDRESS, S.NEW_ADDRESS " +
            " FROM PICKUP_ORDER O, STORE S WHERE O.STORE_ID = ? AND O.STORE_ID = S.ID ORDER BY ORDER_DATE DESC";

        TraceLog.info(getClass(), "getPickUpOrders", "SQL: " + sql);
        try {
            conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, storeId);
            
            rs = stmt.executeQuery();
            
            
            while(rs.next()) {
                PickUpOrder order = new PickUpOrder(rs.getString("ID"), rs.getString("STATUS"), rs.getString("CUSTOMER_ID"),
                                                    rs.getString("STORE_ID"), null, null, 
                                                    rs.getInt("TOTAL_PRICE"), rs.getString("STORE_NAME"));
                //TraceLog.info(getClass(), "getPickUpOrders", "order_date: " + rs.getTime("ORDER_DATE"));
                //TraceLog.info(getClass(), "getPickUpOrders", "order_date: " + rs.getTimestamp("ORDER_DATE"));
                //TraceLog.info(getClass(), "getPickUpOrders", "order_date: " + rs.getDate("ORDER_DATE"));
                Timestamp pickupDate = rs.getTimestamp("PICKUP_DATE");
                Timestamp orderDate = rs.getTimestamp("ORDER_DATE");
                
                if(pickupDate != null) {
                    order.setPickupDate(new Date(pickupDate.getTime()));
                }
                if(orderDate != null) {
                    order.setOrderDate(new Date(orderDate.getTime()));
                }
                order.setStoreOldAddress(rs.getString("OLD_ADDRESS"));
                order.setStoreNewAddress(rs.getString("NEW_ADDRESS"));
                order.setCustomerName(rs.getString("CUSTOMER_NAME"));
                //order.setTotalPrice(rs.getInt("TOTAL_PRICE"));
                
                list.add(order);
                TraceLog.info(getClass(), "getPickUpOrders", "order: " + order);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            TraceLog.severe(getClass(), "getPickUpOrders", e.getMessage());
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
        
        if(includeItems) {
            for(int i = 0, len = list.size(); i < len; i++) {
                PickUpOrder order = (PickUpOrder) list.get(i);
                order.setProducts(findProductsByOrderId(order.getId()));
            }
        }
        return (PickUpOrder[]) list.toArray(new PickUpOrder[list.size()]);
    }
    
    public Product[] findProductsByOrderId(String orderId) {
        TraceLog.info(getClass(), "findProductsByOrderId", "START - orderId: " + orderId);
        List list = new ArrayList();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        final String sql = "SELECT P.ID, P.NAME, P.IMAGE, P.PRICE, P.CATEGORY_ID, O.QUANTITY FROM PRODUCT P, PICKUP_ORDER_ITEM O " +
            " WHERE O.ORDER_ID = ? AND O.PRODUCT_ID = P.ID";
        try {
            conn = DBConnectionFactory.getConnection();
            TraceLog.info(getClass(), "findProductsByOrderId", "conn: " + conn);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, orderId);
            
            rs = stmt.executeQuery();
            while(rs.next()) {
                //Product product = new Product(rs.getString("ID"), rs.getString("CATEGORY_ID"), rs.getString("NAME"), 
                //                     rs.getInt("PRICE"), rs.getString("IMAGE"), rs.getInt("QUANTITY"));
                //
                //TraceLog.info(getClass(), "findProductsByOrderId", "product: " + product);
                list.add(new Product(rs.getString("ID"), rs.getString("CATEGORY_ID"), rs.getString("NAME"), 
                                     rs.getInt("PRICE"), rs.getString("IMAGE"), rs.getInt("QUANTITY")));
            }
        } catch(SQLException e) {
            TraceLog.severe(getClass(), "findProductsByOrderId", e.getMessage());
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch(Exception ex) {}
            }
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(Exception ex) {}
            }
         }
        
        return (Product[]) list.toArray(new Product[list.size()]);
    }
    
    public void insertPickUpOrder(PickUpOrder order) {
        TraceLog.info(getClass(), "insertPickUpOrder", "START");
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        final String sql = "INSERT INTO PICKUP_ORDER (STATUS, CUSTOMER_ID, STORE_ID, ORDER_DATE, PICKUP_DATE, TOTAL_PRICE, CUSTOMER_NAME) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        String orderId = null;
        
        try {
            conn = DBConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, order.getStatus());
            stmt.setString(2, order.getCustomerId());
            stmt.setInt(3, Integer.parseInt(order.getStoreId()));
            Date orderDate = order.getOrderDate();
            if(orderDate != null) {
                stmt.setTimestamp(4, new Timestamp(orderDate.getTime()));
            } else {
                stmt.setTimestamp(4, null);
            }
            Date pickupDate = order.getPickupDate();
            if(pickupDate != null) {
                stmt.setTimestamp(5, new Timestamp(pickupDate.getTime()));
            } else {
                stmt.setTimestamp(5, null);
            }
            stmt.setInt(6, order.getTotalPrice());
            stmt.setString(7, order.getCustomerName());
            
            stmt.executeUpdate();
            
            //rs = stmt.getGeneratedKeys();
            stmt = conn.prepareStatement("select last_insert_rowid()");
            rs = stmt.executeQuery();
            
            if(rs != null && rs.next()) {
                int generatedKey = rs.getInt(1);
                TraceLog.info(getClass(), "insertPickUpOrder", "generatedKey: " + generatedKey);
                orderId = String.valueOf(generatedKey);
            }

            if(orderId != null) {
                insertPickUpItems(conn, orderId, order.getProducts());
            }

            conn.commit();
        } catch(SQLException e) {
            TraceLog.severe(getClass(), "findProductsByOrderId", e.getMessage());
            e.printStackTrace();
            try {
                conn.rollback();
            } catch(Exception ex1) {}
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch(Exception ex) {}
            }
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(Exception ex) {}
            }
            if(conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch(Exception ex) {}
            }
         }

        TraceLog.info(getClass(), "insertPickUpOrder", "END");
    }
    
    public void insertPickUpItems(Connection conn, String orderId, Product[] items) {
        TraceLog.info(getClass(), "insertPickUpItems", "START - orderId: " + orderId);
        
        if(items == null || items.length == 0) {
            return;
        }
        
        
        PreparedStatement stmt = null;
        int insertCount = 0;
        final String sql = "INSERT INTO PICKUP_ORDER_ITEM(ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (?, ?, ?)";
        try {
            stmt = conn.prepareStatement(sql);
            
            for(int i = 0; i < items.length; i++) {
                stmt.setInt(1, Integer.parseInt(orderId));
                stmt.setInt(2, Integer.parseInt(items[i].getId()));
                stmt.setInt(3, items[i].getQuantity());               
                stmt.executeUpdate();
                insertCount++;
            }
        } catch(SQLException e) {
            TraceLog.severe(getClass(), "findProductsByOrderId", e.getMessage());
            e.printStackTrace();
        } finally {
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(Exception ex) {}
            }
         }
        TraceLog.info(getClass(), "insertPickUpItems", "insertCount: " + insertCount);
        TraceLog.info(getClass(), "insertPickUpItems", "END");
    }
    
    public void insertPickupOrderWrapper(PickupOrderWrapper order) {
        TraceLog.info(getClass(), "insertPickUpOrder", "START");
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        final String sql = "INSERT INTO PICKUP_ORDER (ID, STATUS, CUSTOMER_ID, CUSTOMER_NAME, STORE_ID, ORDER_DATE, PICKUP_DATE, TOTAL_PRICE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        String orderId = null;
        
        try {
            conn = DBConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, order.getId());
            stmt.setString(2, order.getStatus());
            stmt.setString(3, order.getCustomerId());
            stmt.setString(4, order.getCustomerName());
            stmt.setInt(5, order.getStoreId());
            Date orderDate = order.getOrderDate();
            if(orderDate != null) {
                stmt.setTimestamp(6, new Timestamp(orderDate.getTime()));
            } else {
                stmt.setTimestamp(6, null);
            }
            Date pickupDate = order.getPickupDate();
            if(pickupDate != null) {
                stmt.setTimestamp(7, new Timestamp(pickupDate.getTime()));
            } else {
                stmt.setTimestamp(7, null);
            }
            stmt.setInt(8, order.getTotalPrice());
            
            stmt.executeUpdate();
            
            //rs = stmt.getGeneratedKeys();
            stmt = conn.prepareStatement("select last_insert_rowid()");
            rs = stmt.executeQuery();
            
            if(rs != null && rs.next()) {
                int generatedKey = rs.getInt(1);
                TraceLog.info(getClass(), "insertPickUpOrder", "generatedKey: " + generatedKey);
                orderId = String.valueOf(generatedKey);
            }

            if(orderId != null) {
                insertPickUpItemWrappers(conn, orderId, order.getItems());
            }

            conn.commit();
        } catch(SQLException e) {
            TraceLog.severe(getClass(), "findProductsByOrderId", e.getMessage());
            e.printStackTrace();
            try {
                conn.rollback();
            } catch(Exception ex1) {}
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch(Exception ex) {}
            }
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(Exception ex) {}
            }
            if(conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch(Exception ex) {}
            }
         }

        TraceLog.info(getClass(), "insertPickUpOrder", "END");        
    }
    
    public void insertPickUpItemWrappers(Connection conn, String orderId, PickupOrderItemWrapper[] items) {
        TraceLog.info(getClass(), "insertPickUpItemWrappers", "START - orderId: " + orderId);
        
        if(items == null || items.length == 0) {
            return;
        }
        
        
        PreparedStatement stmt = null;
        int insertCount = 0;
        final String sql = "INSERT INTO PICKUP_ORDER_ITEM(ID, ORDER_ID, PRODUCT_ID, QUANTITY) VALUES (?, ?, ?, ?)";
        try {
            stmt = conn.prepareStatement(sql);
            
            for(int i = 0; i < items.length; i++) {
                stmt.setInt(1, items[i].getId());
                stmt.setInt(2, Integer.parseInt(orderId));
                stmt.setInt(3, items[i].getProductId());
                stmt.setInt(4, items[i].getQuantity());               
                stmt.executeUpdate();
                insertCount++;
            }
        } catch(SQLException e) {
            TraceLog.severe(getClass(), "insertPickUpItemWrappers", e.getMessage());
            e.printStackTrace();
        } finally {
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(Exception ex) {}
            }
         }
        TraceLog.info(getClass(), "insertPickUpItemWrappers", "insertCount: " + insertCount);
        TraceLog.info(getClass(), "insertPickUpItemWrappers", "END");
    }
    
    public int getMaxOrderIdOfStore(String storeId) {
        TraceLog.info(getClass(), "getMaxOrderIdOfStore", "storeId: " + storeId);
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        final String sql = "SELECT MAX(ID) from PICKUP_ORDER WHERE STORE_ID = ?";
        
        try {
            conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, storeId);
            
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch(Exception e) {
            TraceLog.severe(getClass(), "getMaxOrderIdOfStore", "ERROR: " + e.getMessage());
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
        return 0;
    }
    
    public void updateOrderStatus(String orderId, String status) {
        TraceLog.info(getClass(), "updateOrderStatus", "START - orderId: " + orderId + ", status: " + status);
        Connection conn = null;
        PreparedStatement stmt = null;
        
        final String sql = "UPDATE PICKUP_ORDER SET STATUS = ? WHERE ID = ?";
        TraceLog.info(getClass(), "updateOrderStatus", "sql: " + sql);
                
        try {
            conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, Integer.parseInt(orderId));
            
            stmt.executeUpdate();
                
        } catch(Exception e) {
            TraceLog.severe(getClass(), "getMaxOrderIdOfStore", "ERROR: " + e.getMessage());
        } finally {
            if(stmt != null) {
                try {
                    stmt.close();
                } catch(Exception e) {}
            }
        }
        TraceLog.info(getClass(), "updateOrderStatus", "END");
    }
        
}
