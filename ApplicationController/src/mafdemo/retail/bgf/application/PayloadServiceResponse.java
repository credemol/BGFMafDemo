package mafdemo.retail.bgf.application;

public class PayloadServiceResponse {
    private double from;
    private String collapse_key;
    private String customMessage;
    private String sound;
    private String alert;
    private String recipientType; // customer, storeOwner.
    private String orderId;

    public PayloadServiceResponse() {
        super();
    }


    public void setFrom(double from) {
        this.from = from;
    }

    public double getFrom() {
        return from;
    }

    public void setCollapse_key(String collapse_key) {
        this.collapse_key = collapse_key;
    }

    public String getCollapse_key() {
        return collapse_key;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getSound() {
        return sound;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }

    public String getRecipientType() {
        return recipientType;
    }

    public String toString() {
        return "from: " + from + ", collapse_key: " + collapse_key + ", customerMessage: " + customMessage + ", sound: " + sound + ", alert: " + alert + ", orderId: " + orderId + ", recipientType: " + recipientType;
    }
}