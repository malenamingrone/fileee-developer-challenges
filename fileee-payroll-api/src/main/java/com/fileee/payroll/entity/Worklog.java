package com.fileee.payroll.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table
public class Worklog {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long employeeId;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column
    private double workedHours;

    public Worklog() {
    }

    public Worklog(long id, long employeeId, LocalDate date, double workedHours) {
        this.id = id;
        this.employeeId = employeeId;
        this.date = date;
        this.workedHours = workedHours;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(double workedHours) {
        this.workedHours = workedHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worklog worklog = (Worklog) o;
        return id == worklog.id &&
                employeeId == worklog.employeeId &&
                Double.compare(worklog.workedHours, workedHours) == 0 &&
                Objects.equals(date, worklog.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, date, workedHours);
    }

    @Override
    public String toString() {
        return "Worklog{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", date=" + date +
                ", workedHours=" + workedHours +
                '}';
    }

}
