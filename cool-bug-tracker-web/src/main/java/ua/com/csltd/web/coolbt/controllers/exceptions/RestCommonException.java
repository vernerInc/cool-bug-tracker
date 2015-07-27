package ua.com.csltd.web.coolbt.controllers.exceptions;

/**
 * @author : verner
 * @since : 15.04.2015
 */
public class RestCommonException extends RuntimeException {
    private RestError error;

    public RestCommonException(String message, RestError error) {
        super(message);
        this.error = error;
    }

    public RestCommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestCommonException(RestError error) {
        super(error.descr);
        this.error = error;
    }

    public RestError getError() {
        return error;
    }

    public void setError(RestError error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ExecutorRestException{" +
                "error=" + error +
                '}';
    }
}
