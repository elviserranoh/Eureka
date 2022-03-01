package com.tech.eureka.user.infrastructure.persistance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_USER_ROLE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {

	@Id
	private UUID id;
	
	private String name;

	public UserRole(String name) {
		this.name = name;
	}
}
