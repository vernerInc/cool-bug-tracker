package ua.com.csltd.web.coolbt.controllers.exceptions;

/**
 * @author : verner
 * @since : 15.04.2015
 */
public enum RestError {
    SERVER_ERROR(-1, "Server error occured")
    , SUCCESS(0, "SUCCESS")
    ;

    public final int code;
    public final String descr;

    RestError(int code, String descr) {
        this.code = code;
        this.descr = descr;
    }
}
