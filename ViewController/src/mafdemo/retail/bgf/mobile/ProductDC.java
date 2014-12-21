package mafdemo.retail.bgf.mobile;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;

public class ProductDC {
    private transient ProviderChangeSupport providerChangeSupport = new ProviderChangeSupport(this);
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private ProductRepository productRepository = new ProductRepository();
    
    private Product[] products = null;
    
    public ProductDC() {
        super();
        //products = new Product[0];
        products = productRepository.findFavoriteProducts((String) AdfmfJavaUtilities.getELValue("#{securityContext.userName}"));
    }
    
    public ProductCategory[] getProductCategories() {
        return productRepository.getProductCategories();
    }
    
    public Product[] getProducts() {
        TraceLog.info(getClass(), "getProducts", "START");        
        try {
            return products;
        } finally {
            TraceLog.info(getClass(), "getProducts", "END");    
        }
    }
    
    
    public void initProducts() {
        
        products = productRepository.findFavoriteProducts((String) AdfmfJavaUtilities.getELValue("#{securityContext.userName}"));
        this.providerChangeSupport.fireProviderRefresh("products");
        
        
    }
    
    
    public void executeFindProducts(String categoryId, String productName) {
        TraceLog.info(getClass(), "executeFindProducts", "START");   
        
        this.products = productRepository.findProducts(categoryId, productName);
        
        if(this.products != null) {
            Map quantityMap = (Map) AdfmfJavaUtilities.getELValue("#{pageFlowScope.ShoppingCartBean.quantityMap}");
            
            for(int i = 0; i < products.length; i++) {
                String productId = products[i].getId();
                if(quantityMap.containsKey(productId)) {
                    products[i].setQuantity(((Integer) quantityMap.get(productId)).intValue());
                }
            }
        }
        this.providerChangeSupport.fireProviderRefresh("products");

        TraceLog.info(getClass(), "executeFindProducts", "END");   
    }
    
    
    public void updateProduct(Product product) {
        TraceLog.info(getClass(), "updateProduct", "START - product: " + product);
        
        this.providerChangeSupport.fireProviderChange("products", product.getId(), product);

        TraceLog.info(getClass(), "updateProduct", "END");
    }
    
    public void updateQuantity(String productId, int quantity) {
        TraceLog.info(getClass(), "updateQuantity", "START productId: " + productId + ", quantity: " + quantity);
        
        Product newProduct = productRepository.findProduct(productId);

        TraceLog.info(getClass(), "updateQuantity", "newProduct: " + newProduct);
        
        if(newProduct == null) {
            return;
        }
        newProduct.setQuantity(quantity);
        
        this.providerChangeSupport.fireProviderChange("products", productId, newProduct);

        TraceLog.info(getClass(), "updateQuantity", "END");
    }
    public void refreshProducts() {
        TraceLog.info(getClass(), "refreshProducts", "START"); 
        
        this.providerChangeSupport.fireProviderRefresh("products");


        TraceLog.info(getClass(), "refreshProducts", "END"); 
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    
    public void addProviderChangeListener(ProviderChangeListener l) {
        providerChangeSupport.addProviderChangeListener(l);
    }

    public void removeProviderChangeListener(ProviderChangeListener l) {
        providerChangeSupport.removeProviderChangeListener(l);
    }

}
