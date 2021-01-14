package com.fileee.controller;

import com.fileee.payroll.controller.PayrollController;
import com.fileee.payroll.entity.Payroll;
import com.fileee.payroll.error.EntityNotFoundException;
import com.fileee.payroll.error.InvalidRequestException;
import com.fileee.payroll.repository.EmployeeRepository;
import com.fileee.payroll.repository.PayrollRepository;
import com.fileee.payroll.service.EmployeeService;
import com.fileee.payroll.service.PayrollService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class PayrollControllerTest {

    @Mock
    private PayrollController controller;

    @Mock
    private PayrollService payrollService;
    @Mock
    private PayrollRepository payrollRepository;

    @Mock
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final Long EMPLOYEE_ID = 1L;

    @Before
    public void setUp() {
        payrollService = new PayrollService(payrollRepository);
        employeeService = new EmployeeService(employeeRepository);
        controller = new PayrollController(payrollService, employeeService);

        List<Payroll> payrollList = payrollList();
        doReturn(payrollList).when(payrollRepository).findBy(null, null, null);
        doReturn(payrollList).when(payrollRepository).saveAll(any());
        doReturn(true).when(employeeRepository).existsById(EMPLOYEE_ID);

    }

    @Test
    public void testListAllPayrolls() throws InvalidRequestException {
        assertEquals(payrollList(), controller.listPayrolls(null, null, null));
    }

    @Test
    public void testListPayrollByPeriodError() throws InvalidRequestException {
        thrown.expect(InvalidRequestException.class);
        controller.listPayrolls(1L, LocalDate.now(), LocalDate.now().minus(1L, ChronoUnit.MONTHS));
    }

    @Test
    public void testSavePayrolls() throws EntityNotFoundException {
        List<Payroll> payrollList = payrollList();
        assertEquals(payrollList, controller.savePayrolls(payrollList));
    }

    @Test
    public void testSaveNonExistentEmployeePayroll() throws EntityNotFoundException {
        doReturn(false).when(employeeRepository).existsById(EMPLOYEE_ID);
        thrown.expect(EntityNotFoundException.class);
        controller.savePayrolls(payrollList());
    }

    private List<Payroll> payrollList() {
        List<Payroll> payrollList = new ArrayList<>();
        payrollList.add(new Payroll(1L, 1L, LocalDate.now(), new BigDecimal(2700)));
        payrollList.add(new Payroll(2L, 1L, LocalDate.now().minus(1L, ChronoUnit.MONTHS), new BigDecimal(2700)));
        payrollList.add(new Payroll(3L, 1L, LocalDate.now().minus(2L, ChronoUnit.MONTHS), new BigDecimal(2700)));
        return payrollList;
    }
}
