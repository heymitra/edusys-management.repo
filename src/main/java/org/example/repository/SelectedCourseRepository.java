package org.example.repository;

import org.example.base.repository.BaseRepository;
import org.example.entity.CourseInfoDTO;
import org.example.entity.SelectedCourse;

import java.util.List;

public interface SelectedCourseRepository extends BaseRepository<SelectedCourse, Long> {
    Double calculateGPA(Long studentId, int semester);
    int getTakenCredits(Long studentId, int semester);
    boolean hasPassedCourseInPreviousSemesters(Long studentId, String courseTitle, int currentSemester);
    boolean hasTakenCourseInCurrentSemester(Long studentId, String courseTitle, int currentSemester);
    List<CourseInfoDTO> viewTakenCourseListByStudent(Long studentId, int currentSemester);
}
