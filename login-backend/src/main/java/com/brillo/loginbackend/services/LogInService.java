package com.brillo.loginbackend.services;

import com.brillo.loginbackend.Exceptions.ValidatorException;
import com.brillo.loginbackend.domains.AppResponse;
import com.brillo.loginbackend.dtos.TokenDto;
import com.brillo.loginbackend.dtos.UserDto;
import com.brillo.loginbackend.dtos.ValidationDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static javax.crypto.Cipher.SECRET_KEY;

@Service
@Slf4j
public class LogInService {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    public AppResponse generateToken(UserDto userDto) {
        List<CompletableFuture<ValidationDto>> validations = List.of(
                isUsernameValidAsync(userDto.getUsername()),
                isEmailValidAsync(userDto.getEmail()),
                isPasswordValidAsync(userDto.getPassword()),
                isDateOfBirthValidAsync(userDto.getDateOfBirth())
        );

        CompletableFuture.allOf(validations.toArray(new CompletableFuture[validations.size()]))
                .join();

        List<ValidationDto> result = validations.stream()
                .map(LogInService::getValidation)
                .filter(Objects::nonNull)
                .toList();

        if (!result.isEmpty()) {
            throw new ValidatorException("Generation failed", result);
        }
        return new AppResponse(HttpStatus.OK.value(), "Token generated successfully", "Token generated successfully",
                generateToken(userDto.getUsername()), null);
    }

    private static ValidationDto getValidation(CompletableFuture<ValidationDto> v) {
        try {
            return v.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public AppResponse verifyToken(String token) {
        if (!isTokenValid(token)) {
            throw new ValidatorException("verification failed");
        }
        log.info("verification passed");
        return new AppResponse(HttpStatus.OK.value(), "verification passed", "verification passed",
                null, null);
    }

    private CompletableFuture<ValidationDto> isUsernameValidAsync(String username) {
        return supplyAsync(() -> {
            if (username == null) {
                return new ValidationDto(username,"Username must not be empty");
            }
            if (username.length() < 4) {
                return new ValidationDto(username,"Username must have at least 4 characters");
            }
            return null;
        });
    }

    private CompletableFuture<ValidationDto> isEmailValidAsync(String email) {
        return supplyAsync(() -> {
            if (email == null) {
                return new ValidationDto(email,"Email must not be empty");
            }
            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
                return new ValidationDto(email,"Invalid email address");
            }
            return null;
        });
    }

    private CompletableFuture<ValidationDto> isPasswordValidAsync(String password) {
        return supplyAsync(() -> {
            if (password == null) {
                return new ValidationDto(password,"Password must not be empty");
            }
            if (password.length() < 8) {
                return new ValidationDto(password,"Password must have at least 8 characters");
            }
            if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
                return new ValidationDto(password,"Password must contain at least 1 uppercase, 1 lowercase, 1 number, and 1 special character");
            }
            return null;
        });
    }

    private CompletableFuture<ValidationDto> isDateOfBirthValidAsync(String dateOfBirth) {
        return supplyAsync(() -> {
            String dob = null;
            if (dateOfBirth == null) {
                return new ValidationDto(dob,"Date of Birth must not be empty");
            }
            dob = dateOfBirth.split("T")[0];
            if (!isDateOfBirthValid(LocalDate.parse(dob))) {
                return new ValidationDto(dob,"Date of Birth should be 16 years or greater");
            }
            return null;
        });
    }
    private boolean isDateOfBirthValid(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        Period period = Period.between(dateOfBirth, now);
        return period.getYears() >= 16;
    }

    private TokenDto generateToken(String username) {
        Date expiration = new Date(System.currentTimeMillis() + jwtExpirationInMs);
        String token = Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, String.valueOf(jwtSecret))
                .setExpiration(expiration)
                .compact();
        return TokenDto.builder().expiresBy(String.valueOf(expiration)).token(token).build();

    }

    private boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return claims != null;
        } catch (Exception e) {
            return false;
        }
    }


}
