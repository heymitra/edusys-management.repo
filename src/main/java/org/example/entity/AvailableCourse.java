package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.base.entity.BaseEntity;

@Getter
@Setter

@Entity
@Table(name = "available_courses")
public class AvailableCourse extends BaseEntity<Long> {
    @Column(name = "course_title")
    private String courseTitle;
    //    private int capacity;
    private int credit;

//    private String semester;

    @ManyToOne
    @JoinColumn(name = "professor_info_id")
    private UserInfo professorInfo;

    @Override
    public String toString() {
        return "\nAvailable Course: " +
                "\n\tid: " + id +
                "\n\tcourse title: " + courseTitle +
//                "\n\tsemester: " + semester +
//                "\n\tcapacity: " + capacity +
                "\n\tcredit: " + credit +
                "\n\tprofessor info: " + professorInfo;
    }
}
