package com.example.trackmint.services.impl;

import com.example.trackmint.dto.AuthResponse;
import com.example.trackmint.model.User;
import com.example.trackmint.repository.UserRepository;
import com.example.trackmint.services.UserService;
import com.example.trackmint.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public List<AuthResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AuthResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private AuthResponse mapToResponse(User user) {
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(
                user.getEmail(),
                token
        );
    }
}