package com.fileee.payroll.repository;

import com.fileee.payroll.entity.Payroll;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface PayrollRepository extends CrudRepository<Payroll, Long> {

    @Query("from Payroll where (employeeId = (?1) or ?1 is null) and (date >= (?2) or ?2 is null) and (date <= (?3) or ?3 is null)")
    List<Payroll> findBy(Long employeeId, LocalDate since, LocalDate until);

}
