package com.brillo.loginbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ValidationDto {
        private String field;
        private String message;
    }