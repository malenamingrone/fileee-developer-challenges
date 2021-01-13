package com.fileee.payroll.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table
public class Employee {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private SalaryType salaryType;

    @Column
    private BigDecimal wage;

    public Employee() {
    }

    public Employee(long id, String name, SalaryType salaryType, BigDecimal wage) {
        this.id = id;
        this.name = name;
        this.salaryType = salaryType;
        this.wage = wage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SalaryType getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(SalaryType salaryType) {
        this.salaryType = salaryType;
    }

    public BigDecimal getWage() {
        return wage;
    }

    public void setWage(BigDecimal wage) {
        this.wage = wage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                Objects.equals(name, employee.name) &&
                salaryType == employee.salaryType &&
                Objects.equals(wage, employee.wage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salaryType, wage);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salaryType=" + salaryType +
                ", wage=" + wage +
                '}';
    }
}
