package com.brillo.loginbackend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
    private String token;
    private String expiresBy;
}
