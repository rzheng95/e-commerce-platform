package com.rzheng.userservice.controller;

import com.rzheng.userservice.model.User;
import com.rzheng.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        this.userService.addUser(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/check-email")
    public boolean doesEmailExist(@RequestParam String email) {
        return this.userService.doesEmailExist(email);
    }
}
