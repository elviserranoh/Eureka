package com.tech.eureka.user.application.service;

import com.tech.eureka.user.application.port.in.IUserService;
import com.tech.eureka.user.application.port.out.IUserRepository;
import com.tech.eureka.user.domain.Role;
import com.tech.eureka.user.domain.User;
import com.tech.eureka.user.infrastructure.exception.InfrastructureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

	private final IUserRepository repository;

	@Transactional(readOnly = true)
	public Optional<User> findUserByEmail(String email) {
		return Optional.ofNullable(this.repository.findByEmail(email));
	}

	@Transactional(readOnly = true)
	public Optional<Role> findRoleByName(String name) {
		return Optional.ofNullable(this.repository.findRoleByName(name));
	}

	@Transactional
	@Override
	public User save(User user) {

		if(!repository.existsByEmail(user.getEmail())) {
			throw new InfrastructureException(String.format("El email %s ya esta en uso", user.getEmail()), HttpStatus.BAD_REQUEST);
		}

		return repository.save(user);
	}

	@Override
	public Optional<User> findById(UUID id) {
		return Optional.empty();
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User edit(User user) {
		return repository.save(user);
	}

	@Override
	public void delete(User user) {
		repository.delete(user);
	}

	@Override
	public void deleteById(UUID id) {
		repository.deleteById(id);
	}


}
