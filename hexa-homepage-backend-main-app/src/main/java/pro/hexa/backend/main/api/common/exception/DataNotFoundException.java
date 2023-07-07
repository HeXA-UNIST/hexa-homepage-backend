package pro.hexa.backend.main.api.common.exception;

public class DataNotFoundException extends Exception{
    private static final long serialVersionUID = 1000L;
    public DataNotFoundException(){}

    public DataNotFoundException(String msg){
        super(msg);
    }
    public DataNotFoundException(Throwable th){
        super(th);
    }

}
