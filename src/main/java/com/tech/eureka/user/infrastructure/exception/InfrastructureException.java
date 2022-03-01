package com.tech.eureka.user.infrastructure.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InfrastructureException extends RuntimeException {

    private final HttpStatus httpStatus;

    public InfrastructureException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
