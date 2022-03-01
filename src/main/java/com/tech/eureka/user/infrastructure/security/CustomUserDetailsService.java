package com.tech.eureka.user.infrastructure.security;

import com.tech.eureka.user.infrastructure.persistance.SpringDataUserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final SpringDataUserEntityRepository repository;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return repository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email + "no se encuentra"));
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserById(UUID id) throws UsernameNotFoundException {
		 return repository.findById(id)
				 .orElseThrow(() -> new UsernameNotFoundException("usuario con ID " + id + " no se encuentra"));
	}

}
