package com.fileee.payroll.controller;

import com.fileee.payroll.entity.Employee;
import com.fileee.payroll.entity.SalaryType;
import com.fileee.payroll.entity.WageSettlement;
import com.fileee.payroll.entity.Worklog;
import com.fileee.payroll.error.ApiException;
import com.fileee.payroll.error.EntityNotFoundException;
import com.fileee.payroll.error.InvalidRequestException;
import com.fileee.payroll.report.ReportGenerator;
import com.fileee.payroll.service.EmployeeService;
import com.fileee.payroll.service.WageSettlementService;
import com.fileee.payroll.service.WorklogService;
import com.fileee.payroll.utils.SortUtils;
import com.sun.javafx.binding.StringFormatter;
import freemarker.log.Logger;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final Logger log = Logger.getLogger(this.getClass().getName());

    private final EmployeeService employeeService;
    private final WorklogService worklogService;
    private final WageSettlementService wageSettlementService;

    public EmployeeController(EmployeeService employeeService, WorklogService worklogService, WageSettlementService wageSettlementService) {
        this.employeeService = employeeService;
        this.worklogService = worklogService;
        this.wageSettlementService = wageSettlementService;
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
        log.info(StringFormatter.format("Getting employee with id [%s]...", id).getValue());
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
        log.info(StringFormatter.format("Updating employee with id [%s]...", id).getValue());
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
        log.info(StringFormatter.format("Removing employee with id [%s]...", id).getValue());
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
        log.info(StringFormatter.format("Getting employee worklog with id [%s]...", id).getValue());
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
        log.info(StringFormatter.format("Adding worklog to employee with id [%s]...", id).getValue());
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
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws ApiException {
        Employee employee = employeeService.getById(id);
        List<Worklog> worklog = worklogService.getBy(id, startDate, endDate);
        return wageSettlementService.calculateWageSettlement(employee, worklog, startDate, endDate);
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
    @GetMapping("/{id}/wage-settlement/report")
    @ApiOperation("Downloads a .pdf file with the wage settlement for an employee for a certain period of time.")
    public ResponseEntity<InputStreamResource> downloadWageSettlement(@PathVariable Long id,
                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws ApiException {
        Employee employee = employeeService.getById(id);
        List<Worklog> worklog = worklogService.getBy(id, startDate, endDate);
        WageSettlement wageSettlement = wageSettlementService.calculateWageSettlement(employee, worklog, startDate, endDate);

        ByteArrayInputStream report = ReportGenerator.wageSettlementReport(wageSettlement);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=wage-settlement.pdf" )
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(report));
    }
}