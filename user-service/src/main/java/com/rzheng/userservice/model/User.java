package com.rzheng.userservice.model;

import com.rzheng.userservice.util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}