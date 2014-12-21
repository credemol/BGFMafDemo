package mafdemo.retail.bgf.mobile.restful;

import mafdemo.retail.bgf.mobile.Product;

public class PickupOrderItemWrapper {
    private int id;
    private int productId;
    private int quantity;
    private int orderId;
    
    public PickupOrderItemWrapper() {
        super();
    }
    
    public static PickupOrderItemWrapper copyOf(Product product) {
        PickupOrderItemWrapper wrapper = new PickupOrderItemWrapper();
        
        //wrapper.setOrderId(Integer.parseInt(orderId));
        wrapper.setProductId(Integer.parseInt(product.getId()));
        wrapper.setQuantity(product.getQuantity());
        
        return wrapper;
    }

    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }
}
