package mafdemo.retail.bgf.mobile;

import java.util.Map;

import javax.el.MethodExpression;


import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.model.AdfELContext;

import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class PickUpStoreBean {
    public PickUpStoreBean() {
    }

    public void selectProvince(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "selectProvince", "START");
        
        AdfELContext context = AdfmfJavaUtilities.getAdfELContext();
        
        MethodExpression me = 
            AdfmfJavaUtilities.getMethodExpression("#{bindings.executeFindDistricts.execute}", Object.class, new Class[] { });
        
        //me.invoke(context, new Object[] { provinceCode });
        me.invoke(context, new Object[] { });


        TraceLog.info(getClass(), "selectProvince", "END");
    }

    public void selectDistrict(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "selectDistrict", "START");
        
        executeFindStores();
    }
    
    private void executeFindStores() {
        String provinceCode = (String) AdfmfJavaUtilities.getELValue("#{pageFlowScope.selectedProvinceCode}");
        String districtCode = (String) AdfmfJavaUtilities.getELValue("#{pageFlowScope.selectedDistrictCode}");
        
        if(provinceCode == null || provinceCode.trim().length() == 0) {
            return;
        }
        if(districtCode == null || districtCode.trim().length() == 0) {
            return;
        }
        
        AdfELContext context = AdfmfJavaUtilities.getAdfELContext();
        
        MethodExpression me = 
            AdfmfJavaUtilities.getMethodExpression("#{bindings.executeFindStores.execute}", Object.class, new Class[] { });
        
        //me.invoke(context, new Object[] { provinceCode });
        me.invoke(context, new Object[] { });
        
        
    }
    
    public void storeNameChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "storeNameChanged", "START");
        
        executeFindStores();

        TraceLog.info(getClass(), "storeNameChanged", "END");
    }    

    public void toggleService01(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "toggleService01", "START");
        Boolean boolValue = (Boolean) AdfmfJavaUtilities.getELValue("#{viewScope.service01}");
        TraceLog.info(getClass(), "toggleService01", "boolValue" + boolValue);
        if(boolValue == null) {
            boolValue = Boolean.FALSE;
        }
    
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.service01}", boolValue.booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        
        executeFindStores();

        TraceLog.info(getClass(), "toggleService01", "END");
    }
    public void toggleService02(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "toggleService02", "START");
        Boolean boolValue = (Boolean) AdfmfJavaUtilities.getELValue("#{pageFlowScope.service02}");
        TraceLog.info(getClass(), "toggleService02", "boolValue" + boolValue);
        if(boolValue == null) {
            boolValue = Boolean.FALSE;
        }
    
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.service02}", boolValue.booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        
        executeFindStores();

        TraceLog.info(getClass(), "toggleService02", "END");
    }
    public void toggleService03(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "toggleService03", "START");
        Boolean boolValue = (Boolean) AdfmfJavaUtilities.getELValue("#{pageFlowScope.service03}");
        TraceLog.info(getClass(), "toggleService03", "boolValue" + boolValue);
        if(boolValue == null) {
            boolValue = Boolean.FALSE;
        }
    
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.service03}", boolValue.booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        
        executeFindStores();

        TraceLog.info(getClass(), "toggleService03", "END");
    }
    public void toggleService04(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "toggleService04", "START");
        Boolean boolValue = (Boolean) AdfmfJavaUtilities.getELValue("#{pageFlowScope.service04}");
        TraceLog.info(getClass(), "toggleService04", "boolValue" + boolValue);
        if(boolValue == null) {
            boolValue = Boolean.FALSE;
        }
    
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.service04}", boolValue.booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        
        executeFindStores();

        TraceLog.info(getClass(), "toggleService04", "END");
    }
    public void toggleService05(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "toggleService05", "START");
        Boolean boolValue = (Boolean) AdfmfJavaUtilities.getELValue("#{pageFlowScope.service05}");
        TraceLog.info(getClass(), "toggleService05", "boolValue" + boolValue);
        if(boolValue == null) {
            boolValue = Boolean.FALSE;
        }
    
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.service05}", boolValue.booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        
        executeFindStores();

        TraceLog.info(getClass(), "toggleService05", "END");
    }
    public void toggleService06(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "toggleService06", "START");
        Boolean boolValue = (Boolean) AdfmfJavaUtilities.getELValue("#{pageFlowScope.service06}");
        TraceLog.info(getClass(), "toggleService06", "boolValue" + boolValue);
        if(boolValue == null) {
            boolValue = Boolean.FALSE;
        }
    
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.service06}", boolValue.booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        
        executeFindStores();

        TraceLog.info(getClass(), "toggleService06", "END");
    }
    public void toggleService07(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "toggleService07", "START");
        Boolean boolValue = (Boolean) AdfmfJavaUtilities.getELValue("#{pageFlowScope.service07}");
        TraceLog.info(getClass(), "toggleService07", "boolValue" + boolValue);
        if(boolValue == null) {
            boolValue = Boolean.FALSE;
        }
    
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.service07}", boolValue.booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        
        executeFindStores();

        TraceLog.info(getClass(), "toggleService07", "END");
    }
    public void toggleService08(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "toggleService08", "START");
        Boolean boolValue = (Boolean) AdfmfJavaUtilities.getELValue("#{pageFlowScope.service08}");
        TraceLog.info(getClass(), "toggleService08", "boolValue" + boolValue);
        if(boolValue == null) {
            boolValue = Boolean.FALSE;
        }
    
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.service08}", boolValue.booleanValue() ? Boolean.FALSE : Boolean.TRUE);
        
        executeFindStores();

        TraceLog.info(getClass(), "toggleService08", "END");
    }


    public void selectStore(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "selectStore", "START");
        
        Object row = AdfmfJavaUtilities.getELValue("#{bindings.storesIterator.currentRow}");

        TraceLog.info(getClass(), "selectStore", "row: " + row);        
        
        if(row != null) {
            //try {
            TraceLog.info(getClass(), "selectStore", "row class: " + row.getClass().getName());
            oracle.adfmf.dc.bean.ConcreteJavaBeanObject beanObject = (oracle.adfmf.dc.bean.ConcreteJavaBeanObject) row;

            TraceLog.info(getClass(), "selectStore", "store name: " + beanObject.getAttribute("name"));
            
            Store store = PickUpUtils.toStore(beanObject);
            
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.ShoppingCartBean.selectedStore}", store);
        }
    }
    
    public void selectProvince____NOT_USED(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "selectProvince", "START");
        
        
        String provinceCode = (String) AdfmfJavaUtilities.getELValue("#{viewScope.provinceCode}");
        TraceLog.info(getClass(), "selectProvince", "provinceCode: [" + provinceCode + "]") ;
        if(provinceCode == null || provinceCode.trim().length() == 0)  {
            AdfmfJavaUtilities.setELValue("#{viewScope.selectedProvinceCode}", "");            
        } else {
            //String provinceCode = (String) AdfmfJavaUtilities.getELValue("#{bindings.provinces.inputValue}");
            TraceLog.info(getClass(), "selectProvince", "provinceCode: " + provinceCode);
            AdfmfJavaUtilities.setELValue("#{viewScope.selectedProvinceCode}", provinceCode);
        }
        
        AdfELContext context = AdfmfJavaUtilities.getAdfELContext();
        
        MethodExpression me = 
            AdfmfJavaUtilities.getMethodExpression("#{bindings.findDistricts.execute}", Object.class, new Class[] { });
        
        
        //me.invoke(context, new Object[] { provinceCode });
        me.invoke(context, new Object[] { });


        TraceLog.info(getClass(), "selectProvince", "END");
    }
    
    public void selectDistrict____NOT_USED(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "selectDistrict", "START");
        
        
        String districtCode = (String) AdfmfJavaUtilities.getELValue("#{viewScope.districtCode}");
        TraceLog.info(getClass(), "selectDistrict", "districtCode: [" + districtCode + "]") ;
        if(districtCode == null || districtCode.trim().length() == 0)  {
            AdfmfJavaUtilities.setELValue("#{viewScope.selectedDistrictCode}", "");        
            return;
        } else {
            //String districtCode = (String) AdfmfJavaUtilities.getELValue("#{bindings.districts.inputValue}");
            TraceLog.info(getClass(), "selectDistrict", "districtCode: " + districtCode);
            AdfmfJavaUtilities.setELValue("#{viewScope.selectedDistrictCode}", districtCode);
        }    
        

        executeFindStores();
    }
    
    private void executeFindStores____NOT_USED() {
        String provinceCode = (String) AdfmfJavaUtilities.getELValue("#{viewScope.selectedProvinceCode}");
        String districtCode = (String) AdfmfJavaUtilities.getELValue("#{viewScope.selectedDistrictCode}");
        
        if(provinceCode == null || provinceCode.trim().length() == 0) {
            return;
        }
        if(districtCode == null || districtCode.trim().length() == 0) {
            return;
        }
        
        AdfELContext context = AdfmfJavaUtilities.getAdfELContext();
        
        MethodExpression me = 
            AdfmfJavaUtilities.getMethodExpression("#{bindings.findStores.execute}", Object.class, new Class[] { });
        
        //me.invoke(context, new Object[] { provinceCode });
        me.invoke(context, new Object[] { });
        
        
    }
    
    public void storeNameChanged____NOT_USED(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "storeNameChanged", "START");
        
        executeFindStores();

        TraceLog.info(getClass(), "storeNameChanged", "END");
    }    
    public void selectStore____NOT_USED(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "selectStore", "START");
        
        Object row = AdfmfJavaUtilities.getELValue("#{bindings.findStoresIterator.currentRow}");

        TraceLog.info(getClass(), "increasePurchaseCount", "row: " + row);        
        
        if(row != null) {
            //try {
            TraceLog.info(getClass(), "increasePurchaseCount", "row class: " + row.getClass().getName());
            oracle.adfmf.dc.bean.ConcreteJavaBeanObject beanObject = (oracle.adfmf.dc.bean.ConcreteJavaBeanObject) row;

            TraceLog.info(getClass(), "selectStore", "store name: " + beanObject.getAttribute("name"));
            
            Store store = PickUpUtils.toStore(beanObject);
            
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.ShoppingCartBean.selectedStore}", store);
        }
    }
    
}
