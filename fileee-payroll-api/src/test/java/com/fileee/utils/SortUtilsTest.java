package com.fileee.utils;

import com.fileee.payroll.entity.Employee;
import com.fileee.payroll.entity.Payroll;
import com.fileee.payroll.entity.SalaryType;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.fileee.payroll.utils.SortUtils.*;
import static org.junit.Assert.assertEquals;

public class SortUtilsTest {

    @Test
    public void testBubbleSortEmployee() {
        List<Employee> sortedList = sortBy(Employee::getName, String::compareTo, SortOrder.ASC, employeeList());
        assertEquals("Delfina Lavaggi", sortedList.get(0).getName());
        assertEquals("Ignacio Frabasil", sortedList.get(1).getName());
        assertEquals("Juan Manuel Ortiz", sortedList.get(2).getName());
        assertEquals("Malena Mingrone", sortedList.get(3).getName());
    }

    @Test
    public void testBubbleSortPayroll() {
        List<Payroll> sortedList = sortBy(Payroll::getAmount, BigDecimal::compareTo, SortOrder.DESC, payrollList());
        assertEquals(new BigDecimal(13000), sortedList.get(0).getAmount());
        assertEquals(new BigDecimal(1300), sortedList.get(1).getAmount());
        assertEquals(new BigDecimal(130), sortedList.get(2).getAmount());
    }

    private List<Employee> employeeList() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Malena Mingrone", SalaryType.FIXED, new BigDecimal(130)));
        employees.add(new Employee(2L, "Delfina Lavaggi", SalaryType.FIXED, new BigDecimal(130)));
        employees.add(new Employee(3L, "Ignacio Frabasil", SalaryType.FIXED, new BigDecimal(130)));
        employees.add(new Employee(4L, "Juan Manuel Ortiz", SalaryType.FIXED, new BigDecimal(130)));
        return employees;
    }

    private List<Payroll> payrollList() {
        List<Payroll> payrollList = new ArrayList<>();
        payrollList.add(new Payroll(1L, 1L, LocalDate.now(), new BigDecimal(130)));
        payrollList.add(new Payroll(2L, 1L, LocalDate.now(), new BigDecimal(13000)));
        payrollList.add(new Payroll(3L, 1L, LocalDate.now(), new BigDecimal(1300)));
        return payrollList;
    }
}