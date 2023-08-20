package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.example.base.entity.BaseEntity;

@Entity
@Table(name = "selected_courses")
public class SelectedCourse extends BaseEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "course_id")
    private AvailableCourse course;

    @ManyToOne
    @JoinColumn(name = "student_info_id")
    private UserInfo studentInfo;

    private double grade;
    private boolean passed;
}
