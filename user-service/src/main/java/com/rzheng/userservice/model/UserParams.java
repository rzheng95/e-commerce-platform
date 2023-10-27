package com.rzheng.userservice.model;

import com.rzheng.userservice.util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserParams {
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;
}
