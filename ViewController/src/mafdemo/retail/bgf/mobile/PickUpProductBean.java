package mafdemo.retail.bgf.mobile;

import java.util.HashMap;
import java.util.Map;

import javax.el.MethodExpression;

import javax.el.ValueExpression;

import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.bindings.dbf.AmxAccessorIteratorBinding;
import oracle.adfmf.bindings.iterator.BasicIterator;
import oracle.adfmf.dc.bean.ConcreteJavaBeanObject;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.model.AdfELContext;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.java.beans.ProviderChangeSupport;

public class PickUpProductBean {
   
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    //private transient ProviderChangeSupport providerChangeSupport = new ProviderChangeSupport(this);
    
    public PickUpProductBean() {
        //purchaseMap.put("47", new Integer(2));
        //purchaseMap.put("52", new Integer(4));
        //purchaseMap.put("54", new Integer(9));
    }

    public void selectCategory_backup(ActionEvent actionEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "selectCategory", "START");
        
        String categoryId = (String)AdfmfJavaUtilities.getELValue("#{bindings.productCategories.inputValue}");

        TraceLog.info(getClass(), "selectCategory", "categoryId: " + categoryId) ;
        
        AdfmfJavaUtilities.setELValue("#{viewScope.selectedCategoryId}", categoryId);
        
        AdfELContext elContext = AdfmfJavaUtilities.getAdfELContext();
        MethodExpression methodExpression = AdfmfJavaUtilities.getMethodExpression("#{bindings.findProducts.execute}", Object.class, new Class[] {});
        methodExpression.invoke(elContext, new Object[] {});

        TraceLog.info(getClass(), "selectCategory", "END");
    }

    public void selectCategory(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        TraceLog.info(getClass(), "selectCategory", "START");
        
        this.searchProducts();

        TraceLog.info(getClass(), "selectCategory", "END");
    }

    public void setQuantity(ActionEvent actionEvent) {
        TraceLog.info(getClass(), "setQuantity", "START");
        
        Object row = AdfmfJavaUtilities.getELValue("#{bindings.productsIterator.currentRow}");

        TraceLog.info(getClass(), "setQuantity", "row: " + row);
        
        if(row != null) {
            TraceLog.info(getClass(), "setQuantity", "row class: " + row.getClass().getName());
            oracle.adfmf.dc.bean.ConcreteJavaBeanObject beanObject = (oracle.adfmf.dc.bean.ConcreteJavaBeanObject) row;
            //TraceLog.info(getClass(), "increasePurchase", "id: " + beanObject.getAttribute("id"));
            //TraceLog.info(getClass(), "increasePurchase", "name: " + beanObject.getAttribute("name"));
            
            String productId = (String) beanObject.getAttribute("id");
            Integer quantity = (Integer) beanObject.getAttribute("quantity");

            TraceLog.info(getClass(), "setQuantity", "id: " + productId + ", quantity: " + quantity);
            
            Map quantityMap = (Map) AdfmfJavaUtilities.getELValue("#{pageFlowScope.ShoppingCartBean.quantityMap}");
            if(quantity.intValue() == 0) {
                quantityMap.remove(productId);
            } else {
                quantityMap.put(productId, quantity);
            }
        }
    }
    



    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public void productNameChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        TraceLog.info(getClass(), "productNameChanged", "START");
        searchProducts();
        TraceLog.info(getClass(), "productNameChanged", "END");
    }
    
    private void searchProducts() {
        TraceLog.info(getClass(), "searchProducts", "START");
        
        //String categoryId = (String)AdfmfJavaUtilities.getELValue("#{bindings.productCategories.inputValue}");
        String categoryId = (String)AdfmfJavaUtilities.getELValue("#{pageFlowScope.selectedCategoryId}");

        TraceLog.info(getClass(), "searchProducts", "categoryId: " + categoryId) ;
        
        //AdfmfJavaUtilities.setELValue("#{pageFlowScope.selectedCategoryId}", categoryId);
        
        AdfELContext elContext = AdfmfJavaUtilities.getAdfELContext();
        MethodExpression methodExpression = AdfmfJavaUtilities.getMethodExpression("#{bindings.executeFindProducts.execute}", Object.class, new Class[] {});
        methodExpression.invoke(elContext, new Object[] {});

        TraceLog.info(getClass(), "searchProducts", "END");
    }
    
}
