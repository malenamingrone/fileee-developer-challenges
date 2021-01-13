package com.fileee.payroll.repository;

import com.fileee.payroll.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
