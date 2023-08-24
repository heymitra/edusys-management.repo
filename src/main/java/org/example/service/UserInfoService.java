package org.example.service;

import org.example.base.service.BaseService;
import org.example.entity.DTO.UserInfoDTO;
import org.example.entity.UserInfo;
import org.example.entity.enumeration.UserRoleEnum;

import java.util.List;

public interface UserInfoService extends BaseService<UserInfo, Long> {
//    List<UserInfoDTO> loadProfessors();
List<UserInfoDTO> findUsersByRole(UserRoleEnum role);
}
