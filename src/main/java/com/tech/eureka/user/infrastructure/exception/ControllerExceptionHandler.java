package com.tech.eureka.user.infrastructure.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {InfrastructureException.class})
    public ResponseEntity<?> handleRequestException(InfrastructureException e) {
        InfrastructureExceptionModel infrastructureExceptionModel = new InfrastructureExceptionModel(
                e.getMessage(),
                e.getHttpStatus(),
                ZonedDateTime.now());
        return new ResponseEntity<>(infrastructureExceptionModel, e.getHttpStatus());
    }

}
