package com.fileee.payroll.error;

import com.sun.javafx.binding.StringFormatter;

public class EntityNotFoundException extends ApiException {

    public EntityNotFoundException(Object id, String entityName) {
        super(StringFormatter.format("Entity %s with id [%s] not found.", entityName, id).getValue());
        this.code = 404;
    }
}
