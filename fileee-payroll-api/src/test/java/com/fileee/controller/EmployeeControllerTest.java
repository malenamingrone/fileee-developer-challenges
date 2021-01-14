package com.fileee.controller;

import com.fileee.payroll.controller.EmployeeController;
import com.fileee.payroll.error.EntityNotFoundException;
import com.fileee.payroll.model.Employee;
import com.fileee.payroll.model.SalaryType;
import com.fileee.payroll.repository.EmployeeRepository;
import com.fileee.payroll.repository.WorklogRepository;
import com.fileee.payroll.service.EmployeeService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

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

    Employee employee = new Employee(1L, "Malena Mingrone", SalaryType.FIXED, new BigDecimal(100));

    @Before
    public void setUp() {
        employeeService = new EmployeeService(employeeRepository);
        worklogService = new WorklogService(worklogRepository);
        controller = new EmployeeController(employeeService, worklogService);

        doNothing().when(employeeRepository).delete(any());
        doReturn(employee).when(employeeRepository).save(any());
        doReturn(employeeList()).when(employeeRepository).findAll();
        doReturn(true).when(employeeRepository).existsById(any());
        doReturn(Optional.of(employee)).when(employeeRepository).findById(any());
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
        assertEquals(SalaryType.FIXED, employee.getSalaryType());
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

    private List<Employee> employeeList() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Malena Mingrone", SalaryType.FIXED, new BigDecimal(130)));
        employees.add(new Employee(2L, "Delfina Lavaggi", SalaryType.FIXED, new BigDecimal(130)));
        employees.add(new Employee(3L, "Ignacio Frabasil", SalaryType.FIXED, new BigDecimal(130)));
        employees.add(new Employee(4L, "Juan Manuel Ortiz", SalaryType.FIXED, new BigDecimal(130)));
        return employees;
    }

}
