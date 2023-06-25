package com.brillo.loginbackend.controllers;

import com.brillo.loginbackend.domains.AppResponse;
import com.brillo.loginbackend.dtos.UserDto;
import com.brillo.loginbackend.services.LogInService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class LogInController {
    private final LogInService logInService;

    @PostMapping("/validate")
    public ResponseEntity<AppResponse> validateFields(@Valid @RequestBody UserDto userDto) {
        log.error("Validating fields",logInService.generateToken(userDto));
        return ResponseEntity.ok().body(logInService.generateToken(userDto));
    }

    @PostMapping("/verify")
    public ResponseEntity<AppResponse> verifyToken(@RequestParam()String token) {
        return ResponseEntity.ok().body(logInService.verifyToken(token));
    }



}
