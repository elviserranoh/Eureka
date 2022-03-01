package com.tech.eureka.user.application.port.in;

import com.tech.eureka.user.domain.Role;
import com.tech.eureka.user.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    Optional<User> findUserByEmail(String username);
    Optional<Role> findRoleByName(String name);

    User save(User user);

    Optional<User> findById(UUID id);

    List<User> findAll();

    User edit(User user);
    void delete(User user);
    void deleteById(UUID id);
}
