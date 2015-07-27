package ua.com.csltd.web.coolbt.controllers.exceptions;

/**
 * @author : verner
 * @since : 15.04.2015
 */
public class RestResult {
    private int code;
    private String descr;
    private String exeption;
    private String requestUrl;
    private String httpMethod;

    public RestResult() {
    }

    public RestResult(RestError restError) {
        if (restError != null) {
            this.code = restError.code;
            this.descr = restError.descr;
        }
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getExeption() {
        return exeption;
    }

    public void setExeption(String exeption) {
        this.exeption = exeption;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "code=" + code +
                ", descr='" + descr + '\'' +
                ", exeption='" + exeption + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                '}';
    }
}
