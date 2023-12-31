package com.rzheng.userservice.model;

import com.rzheng.userservice.util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class User {
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String hashedPassword;
    private String passwordSalt;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
