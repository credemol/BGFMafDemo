package mafdemo.retail.bgf.mobile.restful;



import java.sql.Timestamp;

public class AddPickupOrderResponse {
    private Result result = new Result();
    private PickupOrderWrapper pickupOrder;
    
    public AddPickupOrderResponse() {
        super();
    }
    
    public AddPickupOrderResponse(boolean success) {
        super();
        result.setSuccess(success);
    }    

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }


    public void setPickupOrder(PickupOrderWrapper pickupOrder) {
        this.pickupOrder = pickupOrder;
    }

    public PickupOrderWrapper getPickupOrder() {
        return pickupOrder;
    }
}
