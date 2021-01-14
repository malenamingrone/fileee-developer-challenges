package com.fileee.payroll.error.response;

public class ErrorResponse {

    private int code;
    private String error;

    public ErrorResponse() {
    }

    public ErrorResponse(int code, String error) {
        this.code = code;
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code=" + code +
                ", error='" + error + '\'' +
                '}';
    }
}
