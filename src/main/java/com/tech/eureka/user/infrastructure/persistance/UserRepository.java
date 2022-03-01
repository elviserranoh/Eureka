package com.tech.eureka.user.infrastructure.persistance;

import com.tech.eureka.user.application.port.out.IUserRepository;
import com.tech.eureka.user.domain.Phone;
import com.tech.eureka.user.domain.Role;
import com.tech.eureka.user.domain.User;
import com.tech.eureka.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@Component
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {

    private UserMapper mapper;
    private final SpringDataUserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        return mapper.toUserDomain(userEntityRepository.findByEmail(email).orElse(null));
    }

    @Override
    public Role findRoleByName(String name) {
        return mapper.toRoleDomain(userEntityRepository.findRoleByName(name).orElse(null));
    }

    @Override
    public User save(User user) {
        UserEntity current = new UserEntity();
        current.setId(user.getId());
        current.setName(user.getName());
        current.setEmail(user.getEmail());
        current.setPassword(passwordEncoder.encode(user.getPassword()));
        current.setRoles(user.getRoles().stream().map(rol -> userEntityRepository.findRoleByName(rol.getName()).orElse(null)).collect(Collectors.toList()));

        List<UserPhoneEntity> phoneEntities = user.getPhones().stream().map(phone -> new UserPhoneEntity(phone.getNumber(), phone.getCityCode(), phone.getCountryCode(), current)).collect(Collectors.toList());

        current.setPhones(phoneEntities);

        UserEntity userEntity = userEntityRepository.save(current);

        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .roles(userEntity.getRoles().stream().map(rol -> new Role(rol.getName())).collect(Collectors.toList()))
                .phones(userEntity.getPhones().stream().map(phone -> new Phone(phone.getId(), phone.getNumber(), phone.getCityCode(), phone.getCountryCode())).collect(Collectors.toList()))
                .created(userEntity.getCreated())
                .modified(userEntity.getModified())
                .lastLogin(userEntity.getLastLogin())
                .isEnabled(userEntity.getIsEnabled())
                .build();

    }

    @Override
    public Optional<User> findById(UUID id) {
        User current = mapper.toUserDomain(userEntityRepository.findById(id).orElse(null));
        return Optional.ofNullable(current);
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> list = userEntityRepository.findAll();
        return list.stream().map(userEntity -> User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .roles(userEntity.getRoles().stream().map(rol -> new Role(rol.getName())).collect(Collectors.toList()))
                .phones(userEntity.getPhones().stream().map(phone -> new Phone(phone.getId(), phone.getNumber(), phone.getCityCode(), phone.getCountryCode())).collect(Collectors.toList()))
                .created(userEntity.getCreated())
                .modified(userEntity.getModified())
                .lastLogin(userEntity.getLastLogin())
                .isEnabled(userEntity.getIsEnabled())
                .build()).collect(Collectors.toList());
    }

    @Override
    public User edit(User user) {
        UserEntity entity = mapper.toUserDbo(user);
        return mapper.toUserDomain(userEntityRepository.save(entity));
    }

    @Override
    public void delete(User user) {
        userEntityRepository.delete(mapper.toUserDbo(user));
    }

    @Override
    public void deleteById(UUID id) {
        userEntityRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userEntityRepository.existsUserEntityByEmail(email);
    }

}
