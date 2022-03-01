package com.tech.eureka.user.infrastructure.controller;

import com.tech.eureka.user.application.dto.request.CreateUserRequest;
import com.tech.eureka.user.application.port.in.IUserService;
import com.tech.eureka.user.domain.User;
import com.tech.eureka.user.infrastructure.exception.InfrastructureException;
import com.tech.eureka.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private UserMapper userDTOConverter;

    @GetMapping(headers = {"Authorization"})
    public ResponseEntity<?> findAll(@RequestHeader HttpHeaders headers) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findAll());
    }

    @PostMapping(headers = {"Authorization"})
    public ResponseEntity<?> save(@RequestBody @Valid CreateUserRequest request, BindingResult result) {
        try {

            if(result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                result.getAllErrors().forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }

            User current = User.builder()
                    .id(UUID.randomUUID())
                    .name(request.getName())
                    .password(request.getPassword())
                    .email(request.getEmail())
                    .phones(request.getPhones())
                    .roles(request.getRoles())
                    .build();

            User userSave = userService.save(current);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userSave);

        } catch (Exception ex) {
            log.info("##### Error " + ex);
            throw new InfrastructureException(String.format("El usuario con id %s no se puede crear", request.getName()), HttpStatus.BAD_REQUEST);
        }
    }

}
