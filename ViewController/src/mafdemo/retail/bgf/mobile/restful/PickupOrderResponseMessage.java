package mafdemo.retail.bgf.mobile.restful;

import java.util.Date;

public class PickupOrderResponseMessage {
    private boolean success;
    private String statusCode;
    private String statusMessage;
    
    private int orderId;
    private Date orderDate;
    
    public PickupOrderResponseMessage() {
        super();
    }
}
