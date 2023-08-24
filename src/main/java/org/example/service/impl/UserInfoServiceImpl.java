package org.example.service.impl;

import org.example.base.service.impl.BaseServiceImpl;
import org.example.entity.DTO.UserInfoDTO;
import org.example.entity.UserInfo;
import org.example.entity.enumeration.UserRoleEnum;
import org.example.repository.UserInfoRepository;
import org.example.service.UserInfoService;

import java.util.List;

public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Long, UserInfoRepository>
        implements UserInfoService {
    public UserInfoServiceImpl(UserInfoRepository repository) {
        super(repository);
    }

//    @Override
//    public List<UserInfoDTO> loadProfessors() {
//        return repository.loadProfessors();
//    }

    @Override
    public List<UserInfoDTO> findUsersByRole(UserRoleEnum role) {
        return repository.findUsersByRole(role);
    }
}
