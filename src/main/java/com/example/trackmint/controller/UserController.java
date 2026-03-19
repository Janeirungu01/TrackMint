package com.example.trackmint.controller;
import com.example.trackmint.dto.AuthResponse;
import com.example.trackmint.services.UserService;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<AuthResponse> getUsers() {
        return userService.getAllUsers();
    }

}
