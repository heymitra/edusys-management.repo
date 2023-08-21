package org.example.service;

import org.example.base.service.BaseService;
import org.example.entity.UserCredential;
import org.example.entity.UserInfo;

public interface UserCredentialService extends BaseService<UserCredential, Long> {
    boolean isUsernameAvailable (String username);
    UserInfo authenticateUser (String username, String enteredPassword);
}
