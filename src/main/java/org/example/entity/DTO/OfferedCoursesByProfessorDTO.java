package org.example.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OfferedCoursesByProfessorDTO {
    private String courseTitle;
    private Long courseId;

    @Override
    public String toString() {
        return "\nOffered Courses by Professor: " +
                "\n\tcourse title --- " + courseTitle +
                "\n\tcourseId ------- " + courseId;
    }
}
