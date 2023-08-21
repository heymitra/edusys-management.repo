package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.base.repository.impl.BaseRepositoryImpl;
import org.example.entity.UserCredential;
import org.example.entity.UserInfo;
import org.example.repository.UserCredentialRepository;

public class UserCredentialRepositoryImpl extends BaseRepositoryImpl<UserCredential, Long> implements UserCredentialRepository {
    public UserCredentialRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<UserCredential> getEntityClass() {
        return UserCredential.class;
    }

    public boolean isUsernameAvailable(String username) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(u) FROM UserCredential u WHERE u.username = :username", Long.class
        );
        query.setParameter("username", username);

        Long count = query.getSingleResult();
        return count == 0;
    }

    @Override
    public UserInfo authenticateUser(String username, String enteredPassword) {
        TypedQuery<UserInfo> query = entityManager.createQuery(
                "SELECT u.userInfo FROM UserCredential u WHERE u.username = :username", UserInfo.class
        );
        query.setParameter("username", username);

        try {
            UserInfo userInfo = query.getSingleResult();

            if (userInfo.getUserCredential().getPassword().equals(enteredPassword)) {
                return userInfo; // Authentication successful
            } else {
                return null; // Password doesn't match
            }
        } catch (NoResultException ex) {
            return null; // No user with the provided username found
        }
    }

}
