package com.example.trackmint.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegisterRequest {
    @NotBlank(message = "Full name is Required")
    private String fullName;

    @Email( message= "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[^a-zA-Z0-9]).{6,}$",
            message = "Password must be at least 6 characters, include a number and a special character"
    )
    private String password;
}
