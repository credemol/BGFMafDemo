package mafdemo.retail.bgf.mobile;

import mafdemo.retail.bgf.mobile.restful.RESTJSONBean;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

import oracle.net.aso.s;

public class PickUpHistoryDetailBean {
    RESTJSONBean restJsonBean = new RESTJSONBean();
    PickUpHistoryRepository pickUpHistoryRepository = new PickUpHistoryRepository();
    
    public PickUpHistoryDetailBean() {
        super();
    }

    public void refreshOrderStatus(ActionEvent actionEvent) {
        // Add event code here...
        
        String orderId = (String)AdfmfJavaUtilities.getELValue("#{bindings.id.inputValue}");
        String status = (String)AdfmfJavaUtilities.getELValue("#{bindings.status.inputValue}");
        
        String newStatus = restJsonBean.getOrderStatus(orderId);
        
        if(status.equals(newStatus) == false) {
            AdfmfJavaUtilities.setELValue("#{bindings.status.inputValue}", newStatus);
            pickUpHistoryRepository.updateOrderStatus(orderId, newStatus);
        }
    }
}
