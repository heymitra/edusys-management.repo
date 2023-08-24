package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.base.entity.BaseEntity;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user_credentials")
public class UserCredential extends BaseEntity<Long> {

    public UserCredential(String username, String password) {
        this.username = username;
        this.password = password;
    }


    @Column(unique = true)
    @NotNull(message = "cannot be null")
    private String username;

    @NotNull(message = "cannot be null")
    private String password;

    @OneToOne(mappedBy = "userCredential", cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @Override
    public String toString() {
        return "\nUserCredential: " +
                "\n\tid: " + id +
                "\n\tusername: " + username +
                "\n\tpassword: " + password;
    }
}
