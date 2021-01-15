package com.fileee.controller;

import com.fileee.payroll.controller.EmployeeController;
import com.fileee.payroll.entity.Employee;
import com.fileee.payroll.entity.SalaryType;
import com.fileee.payroll.entity.WageSettlement;
import com.fileee.payroll.entity.Worklog;
import com.fileee.payroll.error.ApiException;
import com.fileee.payroll.error.EntityNotFoundException;
import com.fileee.payroll.error.InvalidRequestException;
import com.fileee.payroll.repository.EmployeeRepository;
import com.fileee.payroll.repository.WorklogRepository;
import com.fileee.payroll.service.EmployeeService;
import com.fileee.payroll.service.WageSettlementService;
import com.fileee.payroll.service.WorklogService;
import com.fileee.payroll.utils.SortUtils;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeController controller;

    @Mock
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private WorklogService worklogService;
    @Mock
    private WorklogRepository worklogRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    Employee employee = new Employee(1L, "Malena Mingrone", SalaryType.HOURLY, new BigDecimal(100));
    Worklog worklog = new Worklog(4L, 1L, LocalDate.now(), 1);

    @Before
    public void setUp() {
        employeeService = new EmployeeService(employeeRepository);
        worklogService = new WorklogService(worklogRepository);
        controller = new EmployeeController(employeeService, worklogService, new WageSettlementService());

        doNothing().when(employeeRepository).delete(any());
        doReturn(employee).when(employeeRepository).save(any());
        doReturn(true).when(employeeRepository).existsById(any());
        doReturn(Optional.of(employee)).when(employeeRepository).findById(any());

        doReturn(worklog).when(worklogRepository).save(any());
        doReturn(employeeList()).when(employeeRepository).findAll();
        when(worklogRepository.findBy(anyLong(), eq(null), eq(null))).thenReturn(worklogList());

    }

    @Test
    public void testListEmployees() {
        List<Employee> employees = controller.listEmployees(null);
        assertEquals(employees, employeeList());
        controller.listEmployees(SortUtils.SortOrder.ASC);
    }

    @Test
    public void testGetEmployee() throws EntityNotFoundException {
        Employee employee = controller.getEmployee(1L);
        assertEquals(1L, employee.getId());
        assertEquals("Malena Mingrone", employee.getName());
        assertEquals(SalaryType.HOURLY, employee.getSalaryType());
        assertEquals(new BigDecimal(100), employee.getWage());
    }

    @Test
    public void testGetNonExistentEmployee() throws EntityNotFoundException {
        doReturn(Optional.empty()).when(employeeRepository).findById(any());
        thrown.expect(EntityNotFoundException.class);
        controller.getEmployee(1L);
    }

    @Test
    public void testAddEmployee() {
        Employee employee = controller.addEmployee(this.employee);
        assertEquals(employee, this.employee);
    }

    @Test
    public void testUpdateEmployee() throws EntityNotFoundException {
        assertEquals(employee, controller.updateEmployee(1L, employee));
    }

    @Test
    public void testUpdateNonExistentEmployee() throws EntityNotFoundException {
        doReturn(false).when(employeeRepository).existsById(any());
        thrown.expect(EntityNotFoundException.class);
        controller.updateEmployee(1L, employee);
    }

    @Test
    public void testRemoveEmployee() throws EntityNotFoundException {
        assertEquals(employee, controller.removeEmployee(1L));
    }

    @Test
    public void testRemoveNonExistentEmployee() throws EntityNotFoundException {
        doReturn(Optional.empty()).when(employeeRepository).findById(any());
        thrown.expect(EntityNotFoundException.class);
        controller.removeEmployee(1L);
    }

    @Test
    public void testListWorklog() throws EntityNotFoundException {
        assertEquals(worklogList(), controller.listWorklog(1L));
    }

    @Test
    public void testListWorklogOfNonExistentEmployee() throws EntityNotFoundException {
        doReturn(false).when(employeeRepository).existsById(any());
        thrown.expect(EntityNotFoundException.class);
        controller.listWorklog(1L);
    }

    @Test
    public void testAddWorklog() throws InvalidRequestException, EntityNotFoundException {
        assertEquals(worklog, controller.addWorklog(3L, worklog));
    }

    @Test
    public void testAddWorklogToNonExistentEmployee() throws InvalidRequestException, EntityNotFoundException {
        doReturn(Optional.empty()).when(employeeRepository).findById(any());
        thrown.expect(EntityNotFoundException.class);
        controller.addWorklog(4L, worklog);
    }

    @Test
    public void testAddWorklogInvalidRequestError() throws InvalidRequestException, EntityNotFoundException {
        employee.setSalaryType(SalaryType.FIXED);
        doReturn(Optional.of(employee)).when(employeeRepository).findById(any());
        thrown.expect(InvalidRequestException.class);
        controller.addWorklog(4L, worklog);
    }

    @Test
    public void testGetWageSettlementsForHourlyEmployee() throws ApiException {
        when(worklogRepository.findBy(anyLong(), isNotNull(), isNotNull())).thenReturn(worklogList());
        WageSettlement wageSettlement = controller.getWageSettlement(1L, LocalDate.now().minus(1L, ChronoUnit.MONTHS), LocalDate.now());
        assertEquals(new BigDecimal(1500).longValue(), wageSettlement.getTotalAmount().longValue());
    }

    @Test
    public void testGetWageSettlementsForHourlyEmployeeWithNoWorklog() throws ApiException {
        WageSettlement wageSettlement = controller.getWageSettlement(1L, LocalDate.now().minus(1L, ChronoUnit.MONTHS), LocalDate.now());
        assertEquals(BigDecimal.ZERO, wageSettlement.getTotalAmount());
    }

    @Test
    public void testGetWageSettlementsForFixedEmployee() throws ApiException {
        employee.setSalaryType(SalaryType.FIXED);
        doReturn(Optional.of(employee)).when(employeeRepository).findById(any());
        WageSettlement wageSettlement = controller.getWageSettlement(1L, LocalDate.now().minus(4L, ChronoUnit.MONTHS), LocalDate.now());
        assertEquals(400L, wageSettlement.getTotalAmount().longValue());
    }

    @Test
    public void testGetEmployeeWageSettlementsInvalidRequestError1() throws ApiException {
        thrown.expect(InvalidRequestException.class);
        thrown.expectMessage("Mandatory params startDate/endDate not provided.");
        controller.getWageSettlement(1L, null, null);
    }

    @Test
    public void testGetEmployeeWageSettlementsInvalidRequestError2() throws ApiException {
        thrown.expect(InvalidRequestException.class);
        thrown.expectMessage("Date param 'startDate' can not be greater than 'endDate' date param.");
        controller.getWageSettlement(1L, LocalDate.now(), LocalDate.now().minus(1L, ChronoUnit.MONTHS));
    }

    private List<Employee> employeeList() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Malena Mingrone", SalaryType.FIXED, new BigDecimal(130)));
        employees.add(new Employee(2L, "Delfina Lavaggi", SalaryType.FIXED, new BigDecimal(130)));
        employees.add(new Employee(3L, "Ignacio Frabasil", SalaryType.FIXED, new BigDecimal(130)));
        employees.add(new Employee(4L, "Juan Manuel Ortiz", SalaryType.FIXED, new BigDecimal(130)));
        return employees;
    }

    private List<Worklog> worklogList() {
        List<Worklog> worklogList = new ArrayList<>();
        worklogList.add(new Worklog(1L, 1L, LocalDate.now(), 5));
        worklogList.add(new Worklog(2L, 1L, LocalDate.now().minus(1L, ChronoUnit.DAYS), 5));
        worklogList.add(new Worklog(3L, 1L, LocalDate.now().minus(2L, ChronoUnit.DAYS), 5));
        return worklogList;
    }

}
