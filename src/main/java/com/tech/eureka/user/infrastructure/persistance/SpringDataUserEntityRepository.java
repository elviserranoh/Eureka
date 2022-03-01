package com.tech.eureka.user.infrastructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    @Query("select r from UserRole r WHERE r.name = ?1")
    Optional<UserRole> findRoleByName(String name);

    Boolean existsUserEntityByEmail(String email);

}
