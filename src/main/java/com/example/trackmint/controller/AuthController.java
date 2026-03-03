package com.example.trackmint.controller;

import com.example.trackmint.dto.JwtResponse;
import com.example.trackmint.dto.LoginRequest;
import com.example.trackmint.dto.RegisterRequest;
import com.example.trackmint.model.User;
import com.example.trackmint.services.AuthService;
import com.example.trackmint.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        // Return message instead of raw User
        return ResponseEntity.ok("User registered successfully with email: " + user.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Login returns Optional<User> or throws error if invalid
        User user = authService.login(request);
        // Generate JWT using user's email
        String token = jwtUtil.generateToken(user.getEmail());
        // Return JWT response instead of raw User
        return ResponseEntity.ok(new JwtResponse(token));
    }
}