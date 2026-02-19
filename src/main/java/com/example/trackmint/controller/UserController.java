package com.example.trackmint.controller;
import com.example.trackmint.model.User;
import com.example.trackmint.repository.UserRepository;
import com.example.trackmint.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}
