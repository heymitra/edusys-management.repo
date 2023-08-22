package org.example.service;

import org.example.base.service.BaseService;
import org.example.entity.CourseInfoDTO;
import org.example.entity.SelectedCourse;

import java.util.List;

public interface SelectedCourseService extends BaseService<SelectedCourse, Long> {
    Double calculateGPA(Long studentId, int semester);
    int getTakenCredits(Long studentId, int semester);
    boolean hasPassedCourseInPreviousSemesters(Long studentId, String courseTitle, int currentSemester);
    boolean hasTakenCourseInCurrentSemester(Long studentId, String courseTitle, int currentSemester);
    List<CourseInfoDTO> viewTakenCourseListByStudent(Long studentId, int currentSemester);
}
