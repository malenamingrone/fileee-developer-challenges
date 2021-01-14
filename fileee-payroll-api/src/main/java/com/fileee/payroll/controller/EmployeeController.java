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
import com.sun.javafx.binding.StringFormatter;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
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

    Logger log = Logger.getLogger(this.getClass().getName());

    private final EmployeeService employeeService;
    private final WorklogService worklogService;

    public EmployeeController(EmployeeService employeeService, WorklogService worklogService) {
        this.employeeService = employeeService;
        this.worklogService = worklogService;
    }

    /**
     * Lists all employees. Sorts the results by name, if desired.
     *
     * @param sortOrder order for the list to be sorted by name (ascending or descending).
     * @return the list of employees.
     */
    @GetMapping
    @ApiOperation("Lists all employees. Sorts the results by name, if desired.")
    public List<Employee> listEmployees(@RequestParam(required = false) SortUtils.SortOrder sortOrder) {
        log.info("Getting all employees...");
        List<Employee> employees = employeeService.getAll();
        log.info("Employees got " + employees);
        if (!Objects.isNull(sortOrder)) {
            employees = SortUtils.sortBy(Employee::getName, String::compareTo, sortOrder, employees);
        }
        return employees;
    }

    /**
     * Gets an employee by id.
     *
     * @param id of the employee to be fetched.
     * @return the employee.
     * @throws EntityNotFoundException if the supplied id doesn't exist.
     */
    @GetMapping("/{id}")
    @ApiOperation("Gets an employee by id.")
    public Employee getEmployee(@PathVariable Long id) throws EntityNotFoundException {
        log.info(StringFormatter.format("Getting employee with id [%s]...", id));
        return employeeService.getById(id);
    }

    /**
     * Creates a new employee.
     *
     * @param employee to be created.
     * @return the created employee.
     */
    @PostMapping
    @ApiOperation("Creates a new employee.")
    public Employee addEmployee(@RequestBody Employee employee) {
        log.info("Adding new employee...");
        return employeeService.save(employee);
    }

    /**
     * Updates an employee by id.
     *
     * @param id       of the employee to be updated
     * @param employee the modified employee.
     * @return the updated employee.
     * @throws EntityNotFoundException if the supplied id doesn't exist.
     */
    @PutMapping("/{id}")
    @ApiOperation("Updates an employee by id.")
    public Employee updateEmployee(@PathVariable Long id, Employee employee) throws EntityNotFoundException {
        log.info(StringFormatter.format("Updating employee with id [%s]...", id));
        return employeeService.update(id, employee);
    }


    /**
     * Deletes an employee by id.
     *
     * @param id of the employee to be deleted
     * @return the deleted employee.
     * @throws EntityNotFoundException if the supplied id doesn't exist.
     */
    @DeleteMapping("/{id}")
    @ApiOperation("Deletes an employee by id.")
    public Employee removeEmployee(@PathVariable Long id) throws EntityNotFoundException {
        log.info(StringFormatter.format("Removing employee with id [%s]...", id));
        return employeeService.delete(id);
    }

    /**
     * Gets an employee's worklog.
     *
     * @param id of the employee.
     * @return the worklog.
     * @throws EntityNotFoundException if the supplied id doesn't exist.
     */
    @GetMapping("/{id}/worklog")
    @ApiOperation("Gets an employee's worklog.")
    public List<Worklog> listWorklog(@PathVariable Long id) throws EntityNotFoundException {
        employeeService.exists(id);
        log.info(StringFormatter.format("Getting employee worklog with id [%s]...", id));
        return worklogService.getBy(id, null, null);
    }

    /**
     * Adds a worklog for an employee.
     *
     * @param id      of the employee.
     * @param worklog to be added.
     * @return the added worklog.
     * @throws EntityNotFoundException if the supplied id doesn't exist.
     * @throws InvalidRequestException if the employee received has a salary of type fixed.
     */
    @PostMapping("/{id}/worklog")
    @ApiOperation("Adds a worklog for an employee.")
    public Worklog addWorklog(@PathVariable Long id, @RequestBody Worklog worklog) throws EntityNotFoundException, InvalidRequestException {
        Employee employee = employeeService.getById(id);
        if (SalaryType.FIXED.equals(employee.getSalaryType())) {
            throw new InvalidRequestException("Can not add worklog to fixed salary employees.");
        }
        log.info(StringFormatter.format("Adding worklog to employee with id [%s]...", id));
        return worklogService.save(worklog);
    }

    /**
     * Calculates the wage settlement for an employee for a certain period of time.
     * Returns the information of the employee, the period of time, and the totalAmount paid or to be paid.
     *
     * @param id        of the employee.
     * @param startDate of the period.
     * @param endDate   of the period.
     * @return the wage settlement.
     * @throws ApiException if the request is invalid, or an error occurred calculating the total amount for a hourly salary employee.
     */
    @GetMapping("/{id}/wage-settlement")
    @ApiOperation("Calculates the wage settlement for an employee for a certain period of time.")
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

        log.info(StringFormatter.format("Calculating wage settlement for employee with id [%s]...", id));
        if (SalaryType.HOURLY.equals(employee.getSalaryType())) {
            List<Worklog> worklog = worklogService.getBy(id, startDate, endDate);
            log.info("Employee's worklog " + worklog);
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
            log.info("Worked months: " + months);
            wageSettlement.setTotalAmount(BigDecimal.valueOf(months).multiply(employee.getWage()));
        }
        log.info("Wage settlement calculated: " + wageSettlement);
        return wageSettlement;
    }

}