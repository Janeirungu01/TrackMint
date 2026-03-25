
package com.example.trackmint.services.impl;

import com.example.trackmint.dto.AuthResponse;
import com.example.trackmint.dto.LoginRequest;
import com.example.trackmint.dto.RefreshTokenRequest;
import com.example.trackmint.dto.UserRequest;
import com.example.trackmint.exception.UserNotFoundException;
import com.example.trackmint.model.Role;
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
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        return generateAndSaveTokens(savedUser);

    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid email or password");
        }

        return generateAndSaveTokens(user);

    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {

        String refreshToken = request.refreshToken();
        if (!jwtUtil.isTokenValid(refreshToken)) {
            throw new UserNotFoundException("Invalid refresh token");
        }

            String email = jwtUtil.extractEmail(refreshToken);

        User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getRefreshToken() == null ||
                !user.getRefreshToken().equals(refreshToken)){
            throw new UserNotFoundException("Invalid refresh token");
        }
        return generateAndSaveTokens(user);

    }

    public void logout(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setRefreshToken(null);
        userRepository.save(user);
    }

    private AuthResponse generateAndSaveTokens(User user) {
        String accessToken = jwtUtil.generateAccessToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new AuthResponse(
                user.getEmail(),
                accessToken,
                refreshToken
        );

    }
}