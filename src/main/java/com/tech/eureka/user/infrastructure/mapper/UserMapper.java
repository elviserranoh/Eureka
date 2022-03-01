package com.tech.eureka.user.infrastructure.mapper;

import com.tech.eureka.user.application.dto.request.UserDTO;
import com.tech.eureka.user.domain.Phone;
import com.tech.eureka.user.domain.Role;
import com.tech.eureka.user.domain.User;
import com.tech.eureka.user.infrastructure.persistance.UserEntity;
import com.tech.eureka.user.infrastructure.persistance.UserPhoneEntity;
import com.tech.eureka.user.infrastructure.persistance.UserRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserDomain(UserEntity userEntity);
    Role toRoleDomain(UserRole userRole);
    Phone toPhoneDomain(UserPhoneEntity phoneEntity);
    UserRole toRoleDbo(Role role);
    UserEntity toUserDbo(User user);
    UserDTO convertToDTO(UserEntity userEntity);
}
