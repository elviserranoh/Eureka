package com.tech.eureka.user.infrastructure.controller;

import com.tech.eureka.user.application.dto.request.LoginRequest;
import com.tech.eureka.user.application.dto.request.PhoneDTO;
import com.tech.eureka.user.application.dto.request.RoleDTO;
import com.tech.eureka.user.application.dto.response.JwtUserResponse;
import com.tech.eureka.user.infrastructure.mapper.UserMapper;
import com.tech.eureka.user.infrastructure.persistance.UserEntity;
import com.tech.eureka.user.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private UserMapper converter;

	@PostMapping
	public ResponseEntity<JwtUserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserEntity user = (UserEntity) authentication.getPrincipal();
		String jwtToken = jwtTokenProvider.generarToken(authentication);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(convertUserEntityAndTokenToJwtUserResponse(user, jwtToken));

	}

	private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(UserEntity user, String jwtToken) {

		return JwtUserResponse.jwtUserResponseBuilder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.roles(user.getAuthorities().stream().map(rol -> new RoleDTO(rol.getAuthority())).collect(Collectors.toList()))
				.phones(user.getPhones().stream()
						.map(phone -> new PhoneDTO(phone.getId(), phone.getNumber(), phone.getCityCode(), phone.getCountryCode()))
						.collect(Collectors.toList()))
				.created(user.getCreated())
				.modified(user.getModified())
				.lastLogin(user.getLastLogin())
				.isEnabled(user.getIsEnabled())
				.token(jwtToken)
				.build();
	}

}
