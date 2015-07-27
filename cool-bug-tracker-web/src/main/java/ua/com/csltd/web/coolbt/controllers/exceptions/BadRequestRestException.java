package ua.com.csltd.web.coolbt.controllers.exceptions;

/**
 * @author : verner
 * @since : 16.04.2015
 */
public class BadRequestRestException extends RuntimeException {
    private RestError error;

    public BadRequestRestException(String message, RestError error) {
        super(message);
        this.error = error;
    }

    public BadRequestRestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestRestException(RestError error) {
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
        return "ExecutorBadRequestRestException{" +
                "error=" + error +
                '}';
    }
}