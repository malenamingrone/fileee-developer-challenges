package com.fileee.payroll.controller;

import com.fileee.payroll.entity.Employee;
import com.fileee.payroll.entity.SalaryType;
import com.fileee.payroll.entity.WageSettlement;
import com.fileee.payroll.entity.Worklog;
import com.fileee.payroll.error.ApiException;
import com.fileee.payroll.error.EntityNotFoundException;
import com.fileee.payroll.error.InvalidRequestException;
import com.fileee.payroll.service.EmployeeService;
import com.fileee.payroll.service.WorklogService;
import com.fileee.payroll.utils.SortUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    public List<Employee> listEmployees(@RequestParam(required = false) SortUtils.SortOrder sortOrder) {
        List<Employee> employees = employeeService.getAll();
        if (!Objects.isNull(sortOrder)) {
            employees = SortUtils.sortBy(Employee::getName, String::compareTo, sortOrder, employees);
        }
        return employees;
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
        return worklogService.getBy(id, null, null);
    }

    @PostMapping("/{id}/worklog")
    public Worklog addWorklog(@PathVariable Long id, @RequestBody Worklog worklog) throws EntityNotFoundException, InvalidRequestException {
        Employee employee = employeeService.getById(id);
        if (SalaryType.FIXED.equals(employee.getSalaryType())) {
            throw new InvalidRequestException("Can not add worklog to fixed salary employees.");
        }
        return worklogService.save(worklog);
    }

    @GetMapping("/{id}/wage-settlement")
    public WageSettlement getWageSettlement(@PathVariable Long id,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws ApiException {
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            throw new InvalidRequestException("Mandatory params startDate/endDate not provided.");
        }
        if (startDate.isAfter(endDate)) {
            throw new InvalidRequestException("Date param 'startDate' can not be greater than 'endDate' date param.");
        }

        Employee employee = employeeService.getById(id);
        WageSettlement wageSettlement = new WageSettlement();
        wageSettlement.setEmployee(employee);
        wageSettlement.setStartDate(startDate);
        wageSettlement.setEndDate(endDate);

        if (SalaryType.HOURLY.equals(employee.getSalaryType())) {
            List<Worklog> worklog = worklogService.getBy(id, startDate, endDate);
            BigDecimal totalAmount = BigDecimal.ZERO;
            if (!worklog.isEmpty()) {
                totalAmount = worklog.stream()
                        .map(w -> employee.getWage().multiply(BigDecimal.valueOf(w.getWorkedHours())))
                        .reduce(BigDecimal::add)
                        .orElseThrow(() -> new ApiException("An error occurred calculation the employee's wage settlement total amount."));
            }
            wageSettlement.setTotalAmount(totalAmount);
        } else {
            long months = ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), endDate.withDayOfMonth(1));
            wageSettlement.setTotalAmount(BigDecimal.valueOf(months).multiply(employee.getWage()));
        }

        return wageSettlement;
    }

}