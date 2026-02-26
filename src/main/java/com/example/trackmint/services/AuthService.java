package com.example.trackmint.services;

import com.example.trackmint.dto.LoginRequest;
import com.example.trackmint.dto.RegisterRequest;
import com.example.trackmint.model.User;

public interface AuthService {
    User register(RegisterRequest request);
    User login(LoginRequest login);
}