package com.brillo.loginbackend.Exceptions;

import com.brillo.loginbackend.domains.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ValidatorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppResponse handleValidatorException(final ValidatorException ex) {
        log.error("ValidationDto error: {} ", ex.getMessage());
        return new AppResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
                ex.getMessage(), ex.getValidations(), ex.getMessage());
    }
}