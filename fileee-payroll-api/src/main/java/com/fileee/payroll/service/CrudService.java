package com.fileee.payroll.service;

import com.fileee.payroll.error.EntityNotFoundException;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the basic crud methods of a CrudRepository.
 *
 * @param <E>  Entity class
 * @param <ID> Entity id type
 * @param <R>  Repository class
 */
public class CrudService<E, ID, R extends CrudRepository<E, ID>> {

    protected final R repository;
    protected final String entityName;

    public CrudService(R repository, Class<?> entity) {
        this.repository = repository;
        this.entityName = entity.getSimpleName();
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

    public E getById(ID id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, entityName));
    }

    public void exists(ID id) throws EntityNotFoundException {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(id, entityName);
        }
    }

    public E update(ID id, E employee) throws EntityNotFoundException {
        this.exists(id);
        return repository.save(employee);
    }

    public E delete(ID id) throws EntityNotFoundException {
        E e = this.getById(id);
        repository.delete(e);
        return e;
    }

}
