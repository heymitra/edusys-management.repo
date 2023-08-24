package org.example.entity.DTO;

import lombok.Getter;

@Getter

public class StudentInfoDTO {
    private String name;
    private String surname;
    private Long studentInfoId;
    private double grade;
    private boolean isEvaluated;

    public StudentInfoDTO(String name, String surname, Long studentInfoId, double grade, boolean isEvaluated) {
        this.name = name;
        this.surname = surname;
        this.studentInfoId = studentInfoId;
        this.grade = grade;
        this.isEvaluated = isEvaluated;
    }

    @Override
    public String toString() {
        String evaluationStatus = isEvaluated ? "Evaluated" : "Not Evaluated";
        return "\nStudents List: " +
                "\n\tstudent id ----------- " + studentInfoId +
                "\n\tname ----------------- " + name +
                "\n\tsurname -------------- " + surname +
                "\n\tgrade ---------------- " + grade +
                "\n\tevaluation status ---- " + evaluationStatus +
                "\n";
    }
}