package com.fileee.payroll.repository;

import com.fileee.payroll.model.Payroll;
import org.springframework.data.repository.CrudRepository;

public interface PayrollRepository extends CrudRepository<Payroll, Long> {
}
