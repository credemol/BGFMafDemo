package mafdemo.retail.bgf.mobile.restful;


import java.util.ArrayList;

import java.util.List;


public class PickupOrderListResponse {
    
    private PickupOrderWrapper[] pickupOrderList = new PickupOrderWrapper[0];
    private Result result = new Result();
    
    public PickupOrderListResponse() {
        super();
    }


    public void setPickupOrderList(PickupOrderWrapper[] pickupOrderList) {
        this.pickupOrderList = pickupOrderList;
    }

    public PickupOrderWrapper[] getPickupOrderList() {
        return pickupOrderList;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
}
