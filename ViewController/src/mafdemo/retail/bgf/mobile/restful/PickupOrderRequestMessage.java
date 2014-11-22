package mafdemo.retail.bgf.mobile.restful;

import java.util.Date;

import mafdemo.retail.bgf.mobile.ProductQuantity;

public class PickupOrderRequestMessage {
    private String customerId;
    private String storeId;
    private Date pickupDate;
    private Date registerDate;
    private ProductQuantity[] productQuanties;
    
    public PickupOrderRequestMessage() {
        super();
    }
}
