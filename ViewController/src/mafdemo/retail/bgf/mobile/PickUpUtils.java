package mafdemo.retail.bgf.mobile;

import mafdemo.retail.bgf.application.TraceLog;

import oracle.adfmf.dc.bean.ConcreteJavaBeanObject;

public class PickUpUtils {
    public PickUpUtils() {
        super();
    }
    
    public static Product toProduct(ConcreteJavaBeanObject beanObject) {
        TraceLog.info(PickUpUtils.class, "toProduct", "START");
        Product product = new Product();
        
        product.setId((String)beanObject.getAttribute("id"));
        product.setCategoryId((String)beanObject.getAttribute("categoryId"));
        product.setName((String)beanObject.getAttribute("name"));
        product.setPrice(((Integer)beanObject.getAttribute("price")).intValue());
        product.setImage((String)beanObject.getAttribute("image"));
        product.setQuantity(((Integer)beanObject.getAttribute("quantity")).intValue());

        TraceLog.info(PickUpUtils.class, "toProduct", "END");
        return product;
    }    
    
    public static Store toStore(ConcreteJavaBeanObject beanObject) {
        Store store = new Store();
        
        store.setId((String) beanObject.getAttribute("id"));
        store.setName((String) beanObject.getAttribute("name"));
        store.setTelNo((String) beanObject.getAttribute("telNo"));
        store.setOldAddress((String) beanObject.getAttribute("oldAddress"));
        store.setNewAddress((String) beanObject.getAttribute("newAddress"));
        store.setService01(((Boolean) beanObject.getAttribute("service01")).booleanValue());
        store.setService02(((Boolean) beanObject.getAttribute("service02")).booleanValue());
        store.setService03(((Boolean) beanObject.getAttribute("service03")).booleanValue());
        store.setService04(((Boolean) beanObject.getAttribute("service04")).booleanValue());
        store.setService05(((Boolean) beanObject.getAttribute("service05")).booleanValue());
        store.setService06(((Boolean) beanObject.getAttribute("service06")).booleanValue());
        store.setService07(((Boolean) beanObject.getAttribute("service07")).booleanValue());
        store.setService08(((Boolean) beanObject.getAttribute("service08")).booleanValue());
        store.setProvinceDistrictId(((Integer) beanObject.getAttribute("provinceDistrictId")).intValue());
        
        return store;
    }
}
