package org.example.entity;

import lombok.Getter;

@Getter

public class CourseInfoDTO {
    private String courseTitle;
    private String professor;
    private int credit;
    private double grade;
    private boolean isPassed;

    public CourseInfoDTO(String courseTitle, String professor, int credit, double grade, boolean isPassed) {
        this.courseTitle = courseTitle;
        this.professor = professor;
        this.credit = credit;
        this.grade = grade;
        this.isPassed = isPassed;
    }

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
