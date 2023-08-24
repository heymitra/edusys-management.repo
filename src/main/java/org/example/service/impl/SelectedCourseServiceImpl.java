package org.example.service.impl;

import jakarta.persistence.NoResultException;
import org.example.base.service.impl.BaseServiceImpl;
import org.example.entity.DTO.CourseInfoDTO;
import org.example.entity.DTO.StudentInfoDTO;
import org.example.entity.SelectedCourse;
import org.example.repository.SelectedCourseRepository;
import org.example.service.SelectedCourseService;

import java.util.Collections;
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
        try {
            return repository.viewTakenCourseListByStudent(studentId, currentSemester);
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<StudentInfoDTO> findStudentsByCourseId(Long courseId) {
        try {
            return repository.findStudentsByCourseId(courseId);
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public SelectedCourse findToBeEvaluatedRecord(Long studentId, Long courseId) {
        try {
            return repository.findToBeEvaluatedRecord(studentId, courseId);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean areAllEvaluated(Long studentId, int currentTerm) {
        return repository.areAllEvaluated(studentId, currentTerm);
    }
}
