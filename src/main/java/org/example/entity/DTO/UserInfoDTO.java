package org.example.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UserInfoDTO {
    private String name;
    private String surname;
    private Long id;

    @Override
    public String toString() {
        return "\nUser Info: " +
                "\n\tname -------- " + name +
                "\n\tsurname ----- " + surname +
                "\n\tid ---------- " + id + "\n";
    }
}
