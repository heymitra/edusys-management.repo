package org.example.base.service.impl;

import org.example.base.entity.BaseEntity;
import org.example.base.repository.BaseRepository;
import org.example.base.service.BaseService;

import java.io.Serializable;
import java.util.Optional;

public class BaseServiceImpl
        <E extends BaseEntity<ID>,
                ID extends Serializable,
                R extends BaseRepository<E, ID>>
        implements BaseService<E, ID> {


    protected final R repository;

    public BaseServiceImpl(R repository) {
        this.repository = repository;
    }


    @Override
    public E save(E entity) {
        if (!repository.contain(entity)) {
            try {
                repository.beginTransaction();
                repository.save(entity);
                repository.commitTransaction();
            } catch (Exception e) {
                repository.rollBack();
                return null;
            }
        } else {
            throw new RuntimeException("This entity is already saved.");
        }
        return entity;
    }

    @Override
    public void deleteById(ID id) {
        if (repository.contain(id)) {
            try {
                repository.beginTransaction();
                repository.deleteById(id);
                repository.commitTransaction();
            } catch (Exception e) {
                repository.rollBack();
            }
        } else {
            throw new RuntimeException("This entity is not saved.");
        }
    }

    @Override
    public E update(E entity) {
        if (repository.contain(entity.getId())) {
            try {
                repository.beginTransaction();
                repository.update(entity);
                repository.commitTransaction();
            } catch (Exception e) {
                repository.rollBack();
                return entity;
            }
        } else {
            throw new RuntimeException("This entity is not saved.");
        }
        return entity;
    }

    @Override
    public Optional<E> findById(ID id) {
        return repository.findById(id);
    }
}
