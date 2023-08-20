package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.base.entity.BaseEntity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user_credentials")
public class UserCredential extends BaseEntity<Long> {

    @Column(unique = true)
    private String username;

    private String password;

    @OneToOne(mappedBy = "userCredential", cascade = CascadeType.ALL)
    private UserInfo userRole;
}
