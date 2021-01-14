package com.fileee.payroll.error;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends ApiException {

    public InvalidRequestException(String detailedMessage) {
        super("Invalid request received: " + detailedMessage);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

}
