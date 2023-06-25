package com.brillo.loginbackend;

import com.brillo.loginbackend.Exceptions.ValidatorException;
import com.brillo.loginbackend.domains.AppResponse;
import com.brillo.loginbackend.dtos.TokenDto;
import com.brillo.loginbackend.dtos.UserDto;
import com.brillo.loginbackend.dtos.ValidationDto;
import com.brillo.loginbackend.services.LogInService;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import static org.mockito.Mockito.when;

@TestPropertySource()
class LogInServiceTest {
  
    @Mock
    private UserDto userDto;

    @InjectMocks
    private LogInService logInService;

    @Mock
    private Environment environment;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void generateToken_ValidUserDto_ReturnsAppResponseWithToken() {
        when(userDto.getUsername()).thenReturn("usernNAME77&&");
        when(userDto.getEmail()).thenReturn("test@example.com");
        when(userDto.getPassword()).thenReturn("Password123!NAME77&&");
        when(userDto.getDateOfBirth()).thenReturn("2000-01-01T00:00:00");

        AppResponse response = logInService.generateToken(userDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assertions.assertEquals("Token generated successfully", response.getMessage());
        Assertions.assertNotNull(response.getData());
    }

    @Test
    void generateToken_InvalidUserDto_ThrowsValidatorException() {
        when(userDto.getUsername()).thenReturn(null);
        when(userDto.getEmail()).thenReturn(null);
        when(userDto.getPassword()).thenReturn(null);
        when(userDto.getDateOfBirth()).thenReturn(null);

        Assertions.assertThrows(ValidatorException.class, () -> {
            logInService.generateToken(userDto);
        });
    }


    @Test
    void verifyToken_InvalidToken_ThrowsValidatorException() {
        String invalidToken = "token";

        Assertions.assertThrows(ValidatorException.class, () -> {
            logInService.verifyToken(invalidToken);
        });
    }


}
