package org.example.base.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.base.entity.BaseEntity;
import org.example.base.repository.BaseRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepositoryImpl<E extends BaseEntity<ID>, ID extends Serializable>
        implements BaseRepository<E, ID> {

    protected final EntityManager entityManager;

    public BaseRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract Class<E> getEntityClass();

    @Override
    public E save(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(E entity) {
        entityManager.remove(entity);
    }

    @Override
    public E update(E entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public Optional<E> findById(ID id) {
        return Optional.ofNullable((entityManager.find(getEntityClass(), id)));
    }

    @Override
    public List<E> loadAll() {
        return entityManager.createQuery("FROM " + getEntityClass().getSimpleName(), getEntityClass()).getResultList();
    }

    @Override
    public boolean contain(E entity){
        return entityManager.contains(entity);
    }

    @Override
    public boolean contain(ID id){
        return entityManager.contains(id);
    }

    @Override
    public void beginTransaction() {
        if (!entityManager.getTransaction().isActive())
            entityManager.getTransaction().begin();
    }

    @Override
    public void commitTransaction() {
        if (entityManager.getTransaction().isActive())
            entityManager.getTransaction().commit();
    }

    @Override
    public void rollBack() {
        if (entityManager.getTransaction().isActive())
            entityManager.getTransaction().rollback();
    }
}
