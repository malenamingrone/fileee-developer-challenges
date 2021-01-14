package com.fileee.payroll.service;

import com.fileee.payroll.entity.Worklog;
import com.fileee.payroll.repository.WorklogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorklogService extends CrudService<Worklog, Long, WorklogRepository> {

    public WorklogService(WorklogRepository repository) {
        super(repository, Worklog.class);
    }

    public List<Worklog> getBy(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return repository.findBy(employeeId, startDate, endDate);
    }

}
