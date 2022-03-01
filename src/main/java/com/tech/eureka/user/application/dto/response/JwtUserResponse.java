package com.tech.eureka.user.application.dto.response;

import com.tech.eureka.user.application.dto.request.PhoneDTO;
import com.tech.eureka.user.application.dto.request.RoleDTO;
import com.tech.eureka.user.application.dto.request.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends UserDTO {
	
	private String token;
	
	@Builder(builderMethodName = "jwtUserResponseBuilder")
	public JwtUserResponse(UUID id, String name, String email, Boolean isEnabled, List<RoleDTO> roles, List<PhoneDTO> phones, LocalDate created, LocalDate modified, LocalDate lastLogin, String token) {
		super(id, name, email, roles, phones, created, modified, lastLogin, isEnabled);
		this.token = token;
	}
	
}
