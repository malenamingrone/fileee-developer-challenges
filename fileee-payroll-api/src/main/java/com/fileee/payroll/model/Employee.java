package com.fileee.payroll.model;

import javax.persistence.*;
import java.math.BigDecimal;

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
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salaryType=" + salaryType +
                ", wage=" + wage +
                '}';
    }
}
