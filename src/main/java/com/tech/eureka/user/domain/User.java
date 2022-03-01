package com.tech.eureka.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    private UUID id;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    private Boolean isEnabled;
    private List<Role> roles;
    private List<Phone> phones;
    private LocalDate created;
    private LocalDate modified;
    private LocalDate lastLogin;

}
