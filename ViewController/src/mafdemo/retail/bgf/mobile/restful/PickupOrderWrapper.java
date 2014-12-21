package mafdemo.retail.bgf.mobile.restful;


import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import mafdemo.retail.bgf.mobile.PickUpOrder;
import mafdemo.retail.bgf.mobile.Product;

public class PickupOrderWrapper {
    
    private String customerId = "customerId";
    private String customerName;
    private int id = 1;
    private Timestamp orderDate;
    private Timestamp pickupDate;
    private String status = "01";
    private int storeId =2;
    private int totalPrice =100;
    private PickupOrderItemWrapper[] items = new PickupOrderItemWrapper[0];
    
    //private final PickupOrder pickupOrder;
    
    public PickupOrderWrapper() {
        
    }
    
    public static PickupOrderWrapper copyOf(PickUpOrder order) {
        PickupOrderWrapper wrapper = new PickupOrderWrapper();
        
        wrapper.setCustomerId(order.getCustomerId());
        if(order.getPickupDate() != null) {
            wrapper.setPickupDate(new Timestamp(order.getPickupDate().getTime()));
        }
        wrapper.setStatus(order.getStatus());
        
        wrapper.setStoreId(Integer.parseInt(order.getStoreId()));
        wrapper.setTotalPrice(order.getTotalPrice());
        
        if(order.getProducts() != null) {
            Product[] products = order.getProducts();
            
            List itemList = new ArrayList();
            
            for(int i = 0; i < products.length; i++) {
                itemList.add(PickupOrderItemWrapper.copyOf(products[i]));
            }
            
            wrapper.setItems((PickupOrderItemWrapper[]) itemList.toArray(new PickupOrderItemWrapper[itemList.size()]));
        }
        return wrapper;   
    }
    



    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setPickupDate(Timestamp pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Timestamp getPickupDate() {
        return pickupDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setItems(PickupOrderItemWrapper[] items) {
        this.items = items;
    }

    public PickupOrderItemWrapper[] getItems() {
        return items;
    }

}
