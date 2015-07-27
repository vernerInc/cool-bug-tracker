package ua.com.csltd.web.coolbt.controllers.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : verner
 * @since : 15.04.2015
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestResult handleException(HttpServletRequest request, Exception ex) {
        logger.info("Exception Occured:: URL=" + request.getRequestURL());
        RestResult restResult = new RestResult();

        restResult.setCode(RestError.SERVER_ERROR.code);
        restResult.setDescr(RestError.SERVER_ERROR.descr);
        restResult.setRequestUrl(request.getRequestURL().toString());
        restResult.setExeption(ex.toString());
        restResult.setHttpMethod(request.getMethod());

        return restResult;
    }

    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(RestCommonException.class)
    @ResponseBody
    public RestResult handleException(HttpServletRequest request, RestCommonException ex) {
        logger.info("ExecutorRestCommonException Occured:: URL=" + request.getRequestURL());

        RestResult restResult = new RestResult();
        restResult.setCode(ex.getError().code);
        restResult.setDescr(ex.getError().descr);
        restResult.setRequestUrl(request.getRequestURL().toString());
        restResult.setExeption(ex.toString());
        restResult.setHttpMethod(request.getMethod());

        return restResult;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestRestException.class)
    @ResponseBody
    public RestResult handleException(HttpServletRequest request, BadRequestRestException ex) {
        logger.info("ExecutorBadRequestRestException Occured:: URL=" + request.getRequestURL());

        RestResult restResult = new RestResult();
        restResult.setCode(ex.getError().code);
        restResult.setDescr(ex.getError().descr);
        restResult.setRequestUrl(request.getRequestURL().toString());
        restResult.setExeption(ex.toString());
        restResult.setHttpMethod(request.getMethod());

        return restResult;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundRestException.class)
    @ResponseBody
    public RestResult handleException(HttpServletRequest request, ResourceNotFoundRestException ex) {
        logger.info("ExecutorNotFoundRestException Occured:: URL=" + request.getRequestURL());

        RestResult restResult = new RestResult();
        restResult.setCode(ex.getError().code);
        restResult.setDescr(ex.getError().descr);
        restResult.setRequestUrl(request.getRequestURL().toString());
        restResult.setExeption(ex.toString());
        restResult.setHttpMethod(request.getMethod());

        return restResult;
    }
}
