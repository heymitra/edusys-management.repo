package org.example.service.impl;

import jakarta.persistence.NoResultException;
import org.example.base.service.impl.BaseServiceImpl;
import org.example.entity.CourseInfoDTO;
import org.example.entity.SelectedCourse;
import org.example.repository.SelectedCourseRepository;
import org.example.service.SelectedCourseService;

import java.util.List;

public class SelectedCourseServiceImpl extends BaseServiceImpl<SelectedCourse, Long, SelectedCourseRepository>
        implements SelectedCourseService {
    public SelectedCourseServiceImpl(SelectedCourseRepository repository) {
        super(repository);
    }

    @Override
    public Double calculateGPA(Long studentId, int semester) {
        return repository.calculateGPA(studentId, semester);
    }

    @Override
    public int getTakenCredits(Long studentId, int semester) {

        try {
            return repository.getTakenCredits(studentId, semester);
        } catch (NoResultException e) {
            return 0;
        }
    }

    @Override
    public boolean hasPassedCourseInPreviousSemesters(Long studentId, String courseTitle, int currentSemester) {
        return repository.hasPassedCourseInPreviousSemesters(studentId, courseTitle, currentSemester);
    }

    @Override
    public boolean hasTakenCourseInCurrentSemester(Long studentId, String courseTitle, int currentSemester) {
        return repository.hasTakenCourseInCurrentSemester(studentId, courseTitle, currentSemester);
    }

    @Override
    public List<CourseInfoDTO> viewTakenCourseListByStudent(Long studentId, int currentSemester) {
        return repository.viewTakenCourseListByStudent(studentId, currentSemester);
    }
}
