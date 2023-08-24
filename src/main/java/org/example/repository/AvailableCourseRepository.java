package org.example.repository;

import org.example.base.repository.BaseRepository;
import org.example.entity.AvailableCourse;
import org.example.entity.DTO.OfferedCoursesByProfessorDTO;

import java.util.List;

public interface AvailableCourseRepository extends BaseRepository<AvailableCourse,Long> {
    List<OfferedCoursesByProfessorDTO> findCoursesByProfessorId(Long professorId);
    Long getProfessorCredits(Long professorInfoId);
}
