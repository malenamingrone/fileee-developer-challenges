package com.fileee.payroll.error;

public class InvalidRequestException extends ApiException {

    public InvalidRequestException(String detailedMessage) {
        super("Invalid request received: " + detailedMessage);
        this.code = 400;
    }

}
