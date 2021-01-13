package com.fileee.payroll.controller;

import com.fileee.payroll.model.Payroll;
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
    public List<Payroll> listPayrolls(@RequestParam(required = false) String employee,
                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate since,
                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate until) {
        return payrollService.getAll();
    }

    @PostMapping
    public List<Payroll> savePayroll(@RequestBody List<Payroll> payrollList) throws Exception {
        for (Payroll payroll : payrollList) {
            employeeService.exists(payroll.getEmployeeId());
        }
        return payrollService.saveAll(payrollList);
    }

}