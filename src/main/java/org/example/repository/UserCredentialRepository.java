package org.example.repository;

import org.example.base.repository.BaseRepository;
import org.example.entity.UserCredential;
import org.example.entity.UserInfo;

public interface UserCredentialRepository extends BaseRepository<UserCredential,Long> {
    boolean isUsernameAvailable (String username);
    UserInfo authenticateUser (String username, String enteredPassword);
}
