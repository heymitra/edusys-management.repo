package org.example.entity.enumeration;

public enum UserRoleEnum {
    STUDENT,
    PROFESSOR("FACULTY", "ADJUNCT"),
    EMPLOYEE;

    private final String[] positions;

    UserRoleEnum(String... positions) {
        this.positions = positions;
    }

    public String[] getPositions() {
        return positions;
    }

}
