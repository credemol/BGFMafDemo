package mafdemo.retail.bgf.mobile;

import java.util.Date;

public class PickUpOrder {
    private String id;
    private String status;
    private String customerId;
    private String storeId;
    private Product[] products;
    private Date pickupDate;
    private Date orderDate;
    private int totalPrice;
    
    private String storeName;
    private String storeNewAddress;
    private String storeOldAddress;
    private String customerName;
    
    public PickUpOrder() {
        super();
    }

    public PickUpOrder(String id, String status, String customerId, String storeId, Date pickupDate, Date orderDate,
                       int totalPrice) {
        super();
        this.id = id;
        this.status = status;
        this.customerId = customerId;
        this.storeId = storeId;
        this.pickupDate = pickupDate;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
    
    public PickUpOrder(String id, String status, String customerId, String storeId, Date pickupDate, Date orderDate,
                       int totalPrice, String storeName) {
        super();
        this.id = id;
        this.status = status;
        this.customerId = customerId;
        this.storeId = storeId;
        this.pickupDate = pickupDate;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.storeName = storeName;
    }    


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    
    public String getKey() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreId() {
        return storeId;
    }


    public void setProducts(Product[] products) {
        this.products = products;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreNewAddress(String storeNewAddress) {
        this.storeNewAddress = storeNewAddress;
    }

    public String getStoreNewAddress() {
        return storeNewAddress;
    }

    public void setStoreOldAddress(String storeOldAddress) {
        this.storeOldAddress = storeOldAddress;
    }

    public String getStoreOldAddress() {
        return storeOldAddress;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String toString() {
        return "id: " + id + ", storeName: " + storeName + ", orderDate: " + orderDate + ", pickupDate: " + pickupDate + ", totalPrice: " + totalPrice;
    }
}
