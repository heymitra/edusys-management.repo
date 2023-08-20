package org.example.base.repository;

import org.example.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Optional;

public interface BaseRepository<E extends BaseEntity<ID>, ID extends Serializable> {
    E save(E entity);
    void deleteById (ID id);
    E update (E entity);
    Optional<E> findById (ID id);
    boolean contain(E entity);
    boolean contain(ID id);

    void beginTransaction();
    void commitTransaction();
    void rollBack();
}
