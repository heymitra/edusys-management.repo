package org.example.service.impl;

import org.example.base.service.impl.BaseServiceImpl;
import org.example.entity.UserCredential;
import org.example.entity.UserInfo;
import org.example.repository.UserCredentialRepository;
import org.example.service.UserCredentialService;

public class UserCredentialServiceImpl extends BaseServiceImpl<UserCredential, Long, UserCredentialRepository>
        implements UserCredentialService {
    public UserCredentialServiceImpl(UserCredentialRepository repository) {
        super(repository);
    }

    @Override
    public boolean isUsernameAvailable (String username) {
        return repository.isUsernameAvailable(username);
    }

    @Override
    public UserInfo authenticateUser(String username, String enteredPassword) {
        return repository.authenticateUser(username, enteredPassword);
    }
}
