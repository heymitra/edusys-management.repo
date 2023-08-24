package org.example.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class StudentInfoDTO {
    private String name;
    private String surname;
    private Long studentInfoId;
    private double grade;
    private boolean isEvaluated;

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