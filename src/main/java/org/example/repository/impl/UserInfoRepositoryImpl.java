package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.base.repository.impl.BaseRepositoryImpl;
import org.example.entity.DTO.UserInfoDTO;
import org.example.entity.UserInfo;
import org.example.entity.enumeration.UserRoleEnum;
import org.example.repository.UserInfoRepository;

import java.util.List;

public class UserInfoRepositoryImpl extends BaseRepositoryImpl<UserInfo, Long> implements UserInfoRepository {
    public UserInfoRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<UserInfo> getEntityClass() {
        return UserInfo.class;
    }

//    @Override
//    public List<UserInfoDTO> loadProfessors() {
//        TypedQuery<UserInfoDTO> query = entityManager.createQuery(
//                "SELECT NEW org.example.entity.DTO.UserInfoDTO(u.name, u.surname, u.id) " +
//                        "FROM UserInfo u " +
//                        "WHERE u.role = :role",
//                UserInfoDTO.class
//        );
//
//        query.setParameter("role", UserRoleEnum.PROFESSOR);
//
//        return query.getResultList();
//    }

    @Override
    public List<UserInfoDTO> findUsersByRole(UserRoleEnum role) {
        TypedQuery<UserInfoDTO> query = entityManager.createQuery(
                "SELECT NEW org.example.entity.DTO.UserInfoDTO(u.name, u.surname, u.id) " +
                        "FROM UserInfo u " +
                        "WHERE u.role = :role",
                UserInfoDTO.class
        );

        query.setParameter("role", role);

        return query.getResultList();
    }
}
