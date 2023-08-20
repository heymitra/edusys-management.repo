package org.example.entity;

import jakarta.persistence.*;
import org.example.base.entity.BaseEntity;

@Entity
@Table(name = "available_courses")
public class AvailableCourse extends BaseEntity<Long> {
    @Column(name = "course_title")
    private String courseTitle;
    private int capacity;
    private int credit;

    @ManyToOne
    @JoinColumn(name = "teacher_info_id")
    private UserInfo teacherInfo;
}
