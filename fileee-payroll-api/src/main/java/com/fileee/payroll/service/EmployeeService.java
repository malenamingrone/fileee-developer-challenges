package com.fileee.payroll.service;


import com.fileee.payroll.model.Employee;
import com.fileee.payroll.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends CrudService<Employee, Long, EmployeeRepository> {

    public EmployeeService(EmployeeRepository repository) {
        super(repository, Employee.class);
    }

}
