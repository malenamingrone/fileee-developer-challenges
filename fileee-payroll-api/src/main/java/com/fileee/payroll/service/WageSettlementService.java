package com.fileee.payroll.service;

import com.fileee.payroll.entity.Employee;
import com.fileee.payroll.entity.SalaryType;
import com.fileee.payroll.entity.WageSettlement;
import com.fileee.payroll.entity.Worklog;
import com.fileee.payroll.error.ApiException;
import com.fileee.payroll.error.InvalidRequestException;
import com.sun.javafx.binding.StringFormatter;
import freemarker.log.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
public class WageSettlementService {

    Logger log = Logger.getLogger(this.getClass().getName());

    public WageSettlement calculateWageSettlement(Employee employee, List<Worklog> worklog, LocalDate startDate, LocalDate endDate) {
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            throw new InvalidRequestException("Mandatory params startDate/endDate not provided.");
        }
        if (startDate.isAfter(endDate)) {
            throw new InvalidRequestException("Date param 'startDate' can not be greater than 'endDate' date param.");
        }

        WageSettlement wageSettlement = new WageSettlement();
        wageSettlement.setEmployee(employee);
        wageSettlement.setStartDate(startDate);
        wageSettlement.setEndDate(endDate);

        log.info(StringFormatter.format("Calculating wage settlement for employee with id [%s]...", employee.getId()).getValue());
        if (SalaryType.HOURLY.equals(employee.getSalaryType())) {
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
