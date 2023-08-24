package org.example.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CourseInfoDTO {
    private String courseTitle;
    private String professor;
    private int credit;
    private double grade;
    private boolean isPassed;

    @Override
    public String toString() {
        String passStatus = isPassed ? "passed" : "failed";
        return  "\n\tcourseTitle ----- " + courseTitle +
                "\n\tprofessor ------- " + professor +
                "\n\tcredit ---------- " + credit +
                "\n\tgrade ----------- " + grade +
                "\n\tpassing status -- " + passStatus + "\n";
    }
}
