package mafdemo.retail.bgf.mobile;

public class ProductQuantity {
    private String productId;
    private int quantity;
    
    public ProductQuantity() {
        super();
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
