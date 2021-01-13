package com.fileee.payroll.error;

public class ApiException extends Exception {

    protected int code;

    public int getCode() {
        return code;
    }

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
