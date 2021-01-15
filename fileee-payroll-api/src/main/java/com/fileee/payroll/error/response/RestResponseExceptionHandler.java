package com.fileee.payroll.error.response;

import com.fileee.payroll.error.ApiException;
import freemarker.log.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = Logger.getLogger(this.getClass().getName());

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        if (ex instanceof ApiException) {
            ApiException apiException = (ApiException) ex;
            log.error("Handled error occurred: " + ex);
            HttpStatus httpStatus = apiException.getHttpStatus();
            return handleExceptionInternal(ex, new ErrorResponse(httpStatus.value(), apiException.getMessage()), new HttpHeaders(), httpStatus, request);
        }
        log.error("Unexpected error: " + ex);
        return handleExceptionInternal(ex, new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred."), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}