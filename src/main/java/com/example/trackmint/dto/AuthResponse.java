package com.example.trackmint.dto;

public record AuthResponse(
        String email,
        String token
) {}