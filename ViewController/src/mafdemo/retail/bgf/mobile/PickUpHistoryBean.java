package mafdemo.retail.bgf.mobile;

import javax.el.MethodExpression;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.model.AdfELContext;

public class PickUpHistoryBean {
    
    private PickUpHistoryRepository pickUpHistoryRepository = new PickUpHistoryRepository();
    
    public PickUpHistoryBean() {
        super();
    }

    public void executeRefreshPickUpHistory(ActionEvent actionEvent) {
        // Add event code here...
        
        AdfELContext context = AdfmfJavaUtilities.getAdfELContext();
        
        MethodExpression me = 
            AdfmfJavaUtilities.getMethodExpression("#{bindings.refreshPickUpHistory.execute}", Object.class, new Class[] { });
        
        me.invoke(context, new Object[] { });
    }
    
    public void updateOrderStatus() {
        String orderId = (String) AdfmfJavaUtilities.getELValue("#{applicationScope.orderId}");
        
        pickUpHistoryRepository.updateOrderStatus(orderId, "10");
        
        AdfmfJavaUtilities.setELValue("#{applicationScope.notified}", Boolean.FALSE);
        
    }
}
