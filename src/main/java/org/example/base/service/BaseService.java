package org.example.base.service;

import org.example.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Optional;

public interface BaseService <E extends BaseEntity<ID>, ID extends Serializable> {
    E save(E entity);
    void deleteById (ID id);
    E update (E entity);
    Optional<E> findById (ID id);
}
