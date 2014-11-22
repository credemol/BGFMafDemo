package mafdemo.retail.bgf.mobile;

import java.util.Map;

import java.util.Set;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class ShoppingCartDC {
    private Product[] selectedProducts = new Product[0];
    private ProductRepository productRepository = new ProductRepository();
    private int totalPrice;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public ShoppingCartDC() {
        super();
    }
    
    public Product[] getSelectedProducts() {
        Map quantityMap = (Map) AdfmfJavaUtilities.getELValue("#{pageFlowScope.ShoppingCartBean.quantityMap}");
        
        Set keySet = quantityMap.keySet();
        
        selectedProducts = productRepository.findProductsByIdSet(keySet);
        
        if(selectedProducts != null && selectedProducts.length > 0) {
            for(int i = 0, len = selectedProducts.length ; i < len; i++) {
                selectedProducts[i].setQuantity(((Integer) quantityMap.get(selectedProducts[i].getId())).intValue());
            }
        }
        
        //
        calculateTotalPrice();
        //
        return selectedProducts;
    }

    public void setTotalPrice(int totalPrice) {
        int oldTotalPrice = this.totalPrice;
        this.totalPrice = totalPrice;
        propertyChangeSupport.firePropertyChange("totalPrice", oldTotalPrice, totalPrice);
    }
    
    public void calculateTotalPrice() {
        TraceLog.info(getClass(), "calculateTotalPrice", "START");
        int price = 0;
        
        if(this.selectedProducts != null && this.selectedProducts.length > 0) {
            Map map = (Map) AdfmfJavaUtilities.getELValue("#{pageFlowScope.ShoppingCartBean.quantityMap}");
            for(int i = 0, len = selectedProducts.length; i < len; i++ ) {
                String productId = selectedProducts[i].getId();
                Integer quantity = (Integer) map.get(productId);
                if(quantity != null) {
                    price += (selectedProducts[i].getPrice() * quantity.intValue());
                }
            }
        }
        TraceLog.info(getClass(), "calculateTotalPrice", "price: " + price);
        
        this.setTotalPrice(price);
        
        TraceLog.info(getClass(), "calculateTotalPrice", "END");
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    
    public void discard() {
        TraceLog.info(getClass(), "discard", "START");
        
        TraceLog.info(getClass(), "discard", "END");
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
}
