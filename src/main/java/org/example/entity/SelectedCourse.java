package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.example.base.entity.BaseEntity;

@Getter
@Setter

@Entity
@Table(name = "selected_courses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"course_id", "student_info_id"})
})
public class SelectedCourse extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "course_id")
    private AvailableCourse course;

    @ManyToOne
    @JoinColumn(name = "student_info_id")
    private UserInfo studentInfo;

    @Min(value = 0, message = "Grade should not be less than 0")
    @Max(value = 20, message = "Age should not be greater than 20")
    private double grade;

    private boolean passed;

    private boolean evaluated;

    @Min(value = 0, message = "Semester cannot be negative")
    @Column(name = "student_semester")
    private int studentSemester;
}
