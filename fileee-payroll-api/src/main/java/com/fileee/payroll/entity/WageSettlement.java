package com.fileee.payroll.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class WageSettlement {

    private Employee employee;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal totalAmount;

    public WageSettlement() {
    }

    public WageSettlement(Employee employee, LocalDate startDate, LocalDate endDate, BigDecimal totalAmount) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WageSettlement that = (WageSettlement) o;
        return Objects.equals(employee, that.employee) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(totalAmount, that.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, startDate, endDate, totalAmount);
    }

    @Override
    public String toString() {
        return "WageSettlement{" +
                "employee=" + employee +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
