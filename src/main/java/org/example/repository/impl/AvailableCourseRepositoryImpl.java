package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.base.repository.impl.BaseRepositoryImpl;
import org.example.entity.AvailableCourse;
import org.example.entity.DTO.OfferedCoursesByProfessorDTO;
import org.example.repository.AvailableCourseRepository;

import java.util.List;

public class AvailableCourseRepositoryImpl extends BaseRepositoryImpl<AvailableCourse, Long>
        implements AvailableCourseRepository {
    public AvailableCourseRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<AvailableCourse> getEntityClass() {
        return AvailableCourse.class;
    }

    @Override
    public List<OfferedCoursesByProfessorDTO> findCoursesByProfessorId(Long professorId) {
        TypedQuery<OfferedCoursesByProfessorDTO> query = entityManager.createQuery(
                "SELECT NEW org.example.entity.DTO.OfferedCoursesByProfessorDTO(c.courseTitle, c.id) " +
                        "FROM AvailableCourse c " +
                        "WHERE c.professorInfo.id = :professorId",
                OfferedCoursesByProfessorDTO.class
        );

        query.setParameter("professorId", professorId);

        return query.getResultList();
    }

    @Override
    public Long getProfessorCredits(Long professorInfoId) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT SUM(ac.credit) FROM AvailableCourse ac " +
                        "WHERE ac.professorInfo.id = :professorInfoId",
                Long.class
        );

        query.setParameter("professorInfoId", professorInfoId);

        Long result = query.getSingleResult();
        return result != null ? result : 0;
    }
}
