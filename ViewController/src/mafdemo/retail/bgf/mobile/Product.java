package mafdemo.retail.bgf.mobile;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class Product {
    private String id;
    private String categoryId;
    private String name;
    private int price;
    private String image;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private int quantity;

    public Product() {
        super();
    }

    public Product(String id, String name, int price, String image) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product(String id, String categoryId, String name, int price, String image) {
        super();
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product(String id, String categoryId, String name, int price, String image, int quantity) {
        super();
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }
    
    public void setId(String id) {
        String oldId = this.id;
        this.id = id;
        propertyChangeSupport.firePropertyChange("id", oldId, id);
    }

    public String getId() {
        return id;
    }

    public void setCategoryId(String categoryId) {
        String oldCategoryId = this.categoryId;
        this.categoryId = categoryId;
        propertyChangeSupport.firePropertyChange("categoryId", oldCategoryId, categoryId);
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange("name", oldName, name);
    }

    public String getName() {
        return name;
    }

    public void setPrice(int price) {
        int oldPrice = this.price;
        this.price = price;
        propertyChangeSupport.firePropertyChange("price", oldPrice, price);
    }

    public int getPrice() {
        return price;
    }

    public void setImage(String image) {
        String oldImage = this.image;
        this.image = image;
        propertyChangeSupport.firePropertyChange("image", oldImage, image);
    }

    public String getImage() {
        return image;
    }

    public void setQuantity(int quantity) {
        int oldQuantity = this.quantity;
        this.quantity = quantity;
        propertyChangeSupport.firePropertyChange("quantity", oldQuantity, quantity);
    }

    public int getQuantity() {
        return quantity;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    
    public String toString() {
        return "id: " + id + ", name: " + name;
    }
}
