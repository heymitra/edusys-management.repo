package org.example.service;

import org.example.base.service.BaseService;
import org.example.entity.AvailableCourse;
import org.example.entity.DTO.OfferedCoursesByProfessorDTO;

import java.util.List;

public interface AvailableCourseService extends BaseService<AvailableCourse, Long> {
    List<OfferedCoursesByProfessorDTO> findCoursesByProfessorId(Long professorId);
    Long getProfessorCredits(Long professorInfoId);
}
