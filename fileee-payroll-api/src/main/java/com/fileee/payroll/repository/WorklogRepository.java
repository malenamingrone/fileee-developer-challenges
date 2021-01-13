package com.fileee.payroll.repository;

import com.fileee.payroll.model.Worklog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorklogRepository extends CrudRepository<Worklog, Long> {

    @Query(value = "select * from Worklog where employee_id = ?1", nativeQuery = true)
    List<Worklog> findByEmployeeId(long id);

}
