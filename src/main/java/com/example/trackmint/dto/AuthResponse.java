package com.example.trackmint.dto;

public record UserResponse(
        Long id,
        String fullName,
        String email
) {}