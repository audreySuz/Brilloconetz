package com.brillo.loginbackend.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
public  class UserDto {
        @NotEmpty(message = "Username is required")
        @Pattern(regexp = ".{4,}", message = "Username should have at least 4 characters")
        private String username;

        @NotEmpty(message = "Email is required")
        @Email(message = "Invalid email address")
        private String email;

        @NotEmpty(message = "Password is required")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Password should have at least 8 characters, 1 uppercase, 1 special character, and 1 number")
        private String password;

        @NotNull(message = "Date of Birth is required")
        private String dateOfBirth;


    }