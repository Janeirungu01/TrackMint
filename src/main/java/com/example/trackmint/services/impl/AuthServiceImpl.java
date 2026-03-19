
package com.example.trackmint.services.impl;

import com.example.trackmint.dto.AuthResponse;
import com.example.trackmint.dto.LoginRequest;
import com.example.trackmint.dto.UserRequest;
import com.example.trackmint.exception.UserNotFoundException;
import com.example.trackmint.model.User;
import com.example.trackmint.repository.UserRepository;
import com.example.trackmint.services.AuthService;
import com.example.trackmint.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse register(UserRequest request) {

        if(!request.password().equals(request.confirmPassword())) {
            throw new UserNotFoundException("Passwords don't match");
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserNotFoundException("User already Exists");
        }

        //create new user
        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(
                user.getEmail(),
                token
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(
                user.getEmail(),
                token
        );
    }
}