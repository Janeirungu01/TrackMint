package com.example.trackmint.services;

import com.example.trackmint.model.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    void deleteUser(Long id);
}
