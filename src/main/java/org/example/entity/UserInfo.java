package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.base.entity.BaseEntity;
import org.example.entity.enumeration.UserRoleEnum;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users_info")
public class UserInfo extends BaseEntity<Long> {

    public UserInfo(String name, String surname, UserRoleEnum role, String professorPosition, int studentTerm, UserCredential userCredential) {
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.professorPosition = professorPosition;
        this.studentTerm = studentTerm;
        this.userCredential = userCredential;
    }

    @NotNull(message = "cannot be null")
    private String name;

    @NotNull(message = "cannot be null")
    private String surname;

    @NotNull(message = "cannot be null")
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Column(name = "professor_position")
    private String professorPosition;

    @Column(name = "student_term")
    private int studentTerm;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;

    @OneToMany(mappedBy = "professorInfo")
    private List<AvailableCourse> taughtCourses = new ArrayList<>();

    @OneToMany(mappedBy = "studentInfo")
    private List<SelectedCourse> selectedCourses;

    @Override
    public String toString() {
        return "\n\tUserInfo: " +
                "\n\t\tid: " + id +
                "\n\t\tname: " + name +
                "\n\t\tsurname: " + surname +
                "\n\t\trole: " + role;
    }
}
