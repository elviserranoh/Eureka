package com.tech.eureka.user.infrastructure.persistance;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Entity
@Table(name = "TB_USERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements UserDetails {

	@Id
	private UUID id;

	private String name;

	@Column(unique = true)
	private String email;

	@NotEmpty
	private String password;
	
	private Boolean isEnabled;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "TB_USER_USER_ROLE",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"), 
				uniqueConstraints = {@UniqueConstraint(columnNames = { "user_id", "role_id" }) })
	private List<UserRole> roles;

	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<UserPhoneEntity> phones;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate created;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate modified;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate lastLogin;

	public UserEntity(UUID id) {
		this.id = id;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream()
				.map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getName()))
				.collect(Collectors.toList());
	}

	@PrePersist
	public void setup() {
		this.setCreated(LocalDate.now());
		this.setModified(LocalDate.now());
		this.setLastLogin(LocalDate.now());
		this.setIsEnabled(true);
	}

	@Override
	public String getUsername() {
		return this.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return this.isEnabled;
	}

	public void addPhone(UserPhoneEntity phone) {
		this.phones.add(phone);
		phone.setUser(this);
	}

}
