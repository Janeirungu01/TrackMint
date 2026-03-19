package com.example.trackmint.services;

import com.example.trackmint.dto.AuthResponse;
import com.example.trackmint.dto.LoginRequest;
import com.example.trackmint.dto.UserRequest;

public interface AuthService {
    AuthResponse register(UserRequest request);
    AuthResponse login(LoginRequest login);
}