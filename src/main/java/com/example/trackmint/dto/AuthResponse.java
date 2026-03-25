package com.example.trackmint.dto;

public record AuthResponse(
        String email,
        String accessToken,
        String refreshToken
) {}