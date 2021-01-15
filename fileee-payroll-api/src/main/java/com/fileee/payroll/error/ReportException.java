package com.fileee.payroll.error;

import org.springframework.http.HttpStatus;

public class ReportException extends ApiException {

    public ReportException(Throwable throwable) {
        super("An error occurred generating report.", throwable);
        this.httpStatus = HttpStatus.CONFLICT;
    }

}
