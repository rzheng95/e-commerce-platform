package com.rzheng.userservice.controller;

import com.rzheng.userservice.entity.User;
import com.rzheng.userservice.service.UserService;
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
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = userService.addUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("user/check-email")
    public Boolean checkEmailExists(@RequestParam String email) {
        return userService.checkUserByEmail(email) != null;
    }
}
