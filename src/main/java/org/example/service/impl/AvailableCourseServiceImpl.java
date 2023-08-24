package org.example.service.impl;

import jakarta.persistence.NoResultException;
import org.example.base.service.impl.BaseServiceImpl;
import org.example.entity.AvailableCourse;
import org.example.entity.DTO.OfferedCoursesByProfessorDTO;
import org.example.repository.AvailableCourseRepository;
import org.example.service.AvailableCourseService;

import java.util.List;

public class AvailableCourseServiceImpl extends BaseServiceImpl<AvailableCourse, Long, AvailableCourseRepository>
        implements AvailableCourseService {
    public AvailableCourseServiceImpl(AvailableCourseRepository repository) {
        super(repository);
    }

    @Override
    public List<OfferedCoursesByProfessorDTO> findCoursesByProfessorId(Long professorId) {
        try {
            return repository.findCoursesByProfessorId(professorId);
        } catch (NoResultException | IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public Long getProfessorCredits(Long professorInfoId) {
        return repository.getProfessorCredits(professorInfoId);
    }
}
