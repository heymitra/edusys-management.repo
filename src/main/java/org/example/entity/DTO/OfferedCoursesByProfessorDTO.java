package org.example.entity.DTO;

import lombok.Getter;

@Getter

public class OfferedCoursesByProfessorDTO {
    private String courseTitle;
    private Long courseId;

    public OfferedCoursesByProfessorDTO(String courseTitle, Long courseId) {
        this.courseTitle = courseTitle;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "\nOffered Courses by Professor: " +
                "\n\tcourse title --- " + courseTitle +
                "\n\tcourseId ------- " + courseId;
    }
}
