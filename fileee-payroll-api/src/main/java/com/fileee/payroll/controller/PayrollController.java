package com.fileee.payroll.controller;

import com.fileee.payroll.error.EntityNotFoundException;
import com.fileee.payroll.error.InvalidRequestException;
import com.fileee.payroll.entity.Payroll;
import com.fileee.payroll.service.EmployeeService;
import com.fileee.payroll.service.PayrollService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("payrolls")
public class PayrollController {

    private final PayrollService payrollService;
    private final EmployeeService employeeService;

    public PayrollController(PayrollService payrollService, EmployeeService employeeService) {
        this.payrollService = payrollService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Payroll> listPayrolls(@RequestParam(required = false) Long employeeId,
                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate since,
                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate until) throws InvalidRequestException {
        return payrollService.getPayrollBy(employeeId, since, until);
    }

    @PostMapping
    public List<Payroll> savePayrolls(@RequestBody List<Payroll> payrollList) throws EntityNotFoundException {
        for (Payroll payroll : payrollList) {
            employeeService.exists(payroll.getEmployeeId());
        }
        return payrollService.saveAll(payrollList);
    }

}