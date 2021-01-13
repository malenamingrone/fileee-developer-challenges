package com.fileee.payroll.service;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public class CrudService<E, ID, R extends CrudRepository<E, ID>> {

    protected final R repository;

    public CrudService(R repository) {
        this.repository = repository;
    }

    public List<E> getAll() {
        List<E> e = new ArrayList<>();
        repository.findAll().forEach(e::add);
        return e;
    }

    public E save(E e) {
        return repository.save(e);
    }

    public List<E> saveAll(Iterable<E> e) {
        return (List<E>) repository.saveAll(e);
    }

    public E getById(ID id) throws Exception {
        return repository.findById(id).orElseThrow(Exception::new);
    }

    public void exists(ID id) throws Exception {
        if (!repository.existsById(id)) {
            throw new Exception();
        }
    }

    public E update(ID id, E employee) throws Exception {
        this.exists(id);
        return repository.save(employee);
    }

    public E delete(ID id) throws Exception {
        E e = this.getById(id);
        repository.delete(e);
        return e;
    }

}
