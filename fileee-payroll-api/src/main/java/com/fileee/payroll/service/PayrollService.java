package com.fileee.payroll.service;

import com.fileee.payroll.model.Payroll;
import com.fileee.payroll.repository.PayrollRepository;
import org.springframework.stereotype.Service;

@Service
public class PayrollService extends CrudService<Payroll, Long, PayrollRepository>{

    public PayrollService(PayrollRepository repository) {
        super(repository);
    }

}
