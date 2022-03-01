package com.tech.eureka.user.application;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
public class ApplicationException extends RuntimeException implements Serializable {

    private final HttpStatus httpStatus;
    private final String errorCode;

    public ApplicationException(String errorMessage, String errorCode, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
