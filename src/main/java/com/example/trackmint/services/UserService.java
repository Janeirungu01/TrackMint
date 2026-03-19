package com.example.trackmint.services;

import com.example.trackmint.dto.AuthResponse;
import java.util.List;

public interface UserService {

    List<AuthResponse> getAllUsers();

    AuthResponse getUserById(Long id);

    void deleteUser(Long id);
}
