package com.fileee.payroll.service;

import com.fileee.payroll.model.Worklog;
import com.fileee.payroll.repository.WorklogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorklogService extends CrudService<Worklog, Long, WorklogRepository> {

    public WorklogService(WorklogRepository repository) {
        super(repository);
    }

    public List<Worklog> getByEmployee(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

}
