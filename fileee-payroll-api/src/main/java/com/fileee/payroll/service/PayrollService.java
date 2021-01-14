package com.fileee.payroll.service;

import com.fileee.payroll.entity.Payroll;
import com.fileee.payroll.error.InvalidRequestException;
import com.fileee.payroll.repository.PayrollRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class PayrollService extends CrudService<Payroll, Long, PayrollRepository> {

    public PayrollService(PayrollRepository repository) {
        super(repository, Payroll.class);
    }

    public List<Payroll> getPayrollBy(Long employeeId, LocalDate since, LocalDate until) throws InvalidRequestException {
        if (!Objects.isNull(since) && !Objects.isNull(until) && since.isAfter(until)) {
            throw new InvalidRequestException("Date param 'since' can not be greater than 'until' date param.");
        }
        return repository.findBy(employeeId, since, until);
    }

}
