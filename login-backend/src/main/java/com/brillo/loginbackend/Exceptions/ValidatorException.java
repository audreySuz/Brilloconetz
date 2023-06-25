package com.brillo.loginbackend.Exceptions;

import com.brillo.loginbackend.dtos.ValidationDto;
import lombok.Getter;

import java.util.List;


public class ValidatorException extends RuntimeException {

    @Getter
    private List<ValidationDto> validations;

    public ValidatorException(String message, Exception cause) {
        super(message, cause);
    }

    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(String message, Exception cause, List<ValidationDto> validations) {
        super(message,cause);
        this.validations = validations;
    }

    public ValidatorException(String message, List<ValidationDto> validations) {
        super(message);
        this.validations = validations;
    }
}

