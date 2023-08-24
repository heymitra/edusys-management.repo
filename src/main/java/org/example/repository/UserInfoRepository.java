package org.example.repository;

import org.example.base.repository.BaseRepository;
import org.example.entity.DTO.UserInfoDTO;
import org.example.entity.UserInfo;
import org.example.entity.enumeration.UserRoleEnum;

import java.util.List;

public interface UserInfoRepository extends BaseRepository<UserInfo,Long> {
//    List<UserInfoDTO> loadProfessors();
    List<UserInfoDTO> findUsersByRole(UserRoleEnum role);
}
