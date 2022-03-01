package com.tech.eureka.user.application.dto.response;

import com.tech.eureka.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponse {
    private final List<User> users;
}