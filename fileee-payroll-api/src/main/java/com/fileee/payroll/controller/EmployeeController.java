package com.fileee.payroll.controller;

import com.fileee.payroll.error.EntityNotFoundException;
import com.fileee.payroll.model.Employee;
import com.fileee.payroll.model.Worklog;
import com.fileee.payroll.service.EmployeeService;
import com.fileee.payroll.service.WorklogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final WorklogService worklogService;

    public EmployeeController(EmployeeService employeeService, WorklogService worklogService) {
        this.employeeService = employeeService;
        this.worklogService = worklogService;
    }

    @GetMapping
    public List<Employee> listEmployees(@RequestParam(required=false) String sort) {
        return employeeService.getAll();
        }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) throws EntityNotFoundException {
        return employeeService.getById(id);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, Employee employee) throws EntityNotFoundException {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public Employee removeEmployee(@PathVariable Long id) throws EntityNotFoundException {
        return employeeService.delete(id);
    }

    @GetMapping("/{id}/worklog")
    public List<Worklog> listWorklog(@PathVariable Long id) throws EntityNotFoundException {
        employeeService.exists(id);
        return worklogService.getByEmployee(id);
    }

    @PostMapping("/{id}/worklog")
    public Worklog addWorklog(@PathVariable Long id, @RequestBody Worklog worklog) throws EntityNotFoundException {
        employeeService.exists(id);
        return worklogService.save(worklog);
    }

}