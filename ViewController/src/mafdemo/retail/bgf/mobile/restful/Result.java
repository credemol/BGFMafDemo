package mafdemo.retail.bgf.mobile.restful;


public class Result {
    private String code;
    private String message;
    private boolean success;
    
    public Result() {
        super();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

}
