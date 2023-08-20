package org.example.entity;

import jakarta.persistence.*;
import org.example.base.entity.BaseEntity;
import org.example.entity.enumeration.UserRoleEnum;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_info")
public class UserInfo extends BaseEntity<Long> {
    private String name;
    private String surName;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @OneToOne
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;

    @OneToMany(mappedBy = "teacherInfo")
    private List<AvailableCourse> taughtCourses = new ArrayList<>();

    @OneToMany(mappedBy = "studentInfo")
    private List<SelectedCourse> selectedCourses;
}
