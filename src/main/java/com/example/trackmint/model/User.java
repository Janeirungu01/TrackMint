package com.example.trackmint.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(unique = true)
    private String email;
//    @Pattern(
//            regexp = "^(?=.*\\d)(?=.*[^a-zA-Z0-9]).{6,}$",
//            message = "Password must be at least 6 characters, include a number and a special character"
//    )
    private String password;
}


