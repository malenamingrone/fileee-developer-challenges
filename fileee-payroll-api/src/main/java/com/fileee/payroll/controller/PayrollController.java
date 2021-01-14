package com.fileee.payroll.controller;

import com.fileee.payroll.entity.Payroll;
import com.fileee.payroll.error.EntityNotFoundException;
import com.fileee.payroll.error.InvalidRequestException;
import com.fileee.payroll.service.EmployeeService;
import com.fileee.payroll.service.PayrollService;
import com.sun.javafx.binding.StringFormatter;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("payrolls")
public class PayrollController {

    Logger log = Logger.getLogger(this.getClass().getName());

    private final PayrollService payrollService;
    private final EmployeeService employeeService;

    public PayrollController(PayrollService payrollService, EmployeeService employeeService) {
        this.payrollService = payrollService;
        this.employeeService = employeeService;
    }

    /**
     * Gets all payrolls if no param is received, or for an specific employee or period of time, or both.
     *
     * @param employeeId optional.
     * @param since start date of the period (optional).
     * @param until end date of the period (optional).
     * @return the payroll.
     * @throws InvalidRequestException if the given period of time is invalid.
     */
    @GetMapping
    @ApiOperation("Gets all payrolls, or searches by employee or period of time, or both.")
    public List<Payroll> listPayrolls(@RequestParam(required = false) Long employeeId,
                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate since,
                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate until) throws InvalidRequestException {
        log.info("Getting payrolls...");
        return payrollService.getPayrollBy(employeeId, since, until);
    }

    /**
     * Creates one or several payrolls.
     *
     * @param payrollList to be created.
     * @return the payrolls created.
     * @throws EntityNotFoundException if the employee id provided does not exist.
     */
    @PostMapping
    @ApiOperation("Creates one or several payrolls.")
    public List<Payroll> savePayrolls(@RequestBody List<Payroll> payrollList) throws EntityNotFoundException {
        for (Payroll payroll : payrollList) {
            employeeService.exists(payroll.getEmployeeId());
        }
        log.info(StringFormatter.format("Saving payroll [%s]...", payrollList));
        return payrollService.saveAll(payrollList);
    }

}