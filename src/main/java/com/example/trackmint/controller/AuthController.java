package com.example.trackmint.controller;

import com.example.trackmint.dto.LoginRequest;
import com.example.trackmint.dto.RegisterRequest;
import com.example.trackmint.model.User;
import com.example.trackmint.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}