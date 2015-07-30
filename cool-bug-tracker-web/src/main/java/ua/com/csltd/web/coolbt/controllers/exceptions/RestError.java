package ua.com.csltd.web.coolbt.controllers.exceptions;

/**
 * @author : verner
 * @since : 15.04.2015
 */
public enum RestError {
    SERVER_ERROR(-1, "Server error occured")
    , SUCCESS(0, "SUCCESS")
    , START_DATE_IS_NULL(100, "Start date can't be null")
    , END_DATE_IS_NULL(101, "End date can't be null")
    , INCORRECT_NUMBER_VALUE(102, "Incorrect number value")
    ;

    public final int code;
    public final String descr;

    RestError(int code, String descr) {
        this.code = code;
        this.descr = descr;
    }
}
