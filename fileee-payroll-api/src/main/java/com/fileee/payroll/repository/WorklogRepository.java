package com.fileee.payroll.repository;

import com.fileee.payroll.entity.Worklog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorklogRepository extends CrudRepository<Worklog, Long> {

    @Query("from Worklog where (employeeId = (?1) or ?1 is null) and (date >= (?2) or ?2 is null) and (date <= (?3) or ?3 is null)")
    List<Worklog> findBy(long id, LocalDate startDate, LocalDate endDate);

}
