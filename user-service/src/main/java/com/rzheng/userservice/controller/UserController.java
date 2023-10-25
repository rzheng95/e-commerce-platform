package com.rzheng.userservice.controller;

import com.rzheng.userservice.model.User;
import com.rzheng.userservice.service.UserService;
import com.rzheng.userservice.util.LoginStatus;
import com.rzheng.userservice.util.SignupStatus;
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


//    @GetMapping("/check-email")
//    public boolean doesEmailExist(@RequestParam String email) {
//        return this.userService.doesEmailExist(email);
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        LoginStatus loginStatus = this.userService.login(email, password);
        if (loginStatus == LoginStatus.SUCCESS) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Login failed", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        SignupStatus signupStatus = this.userService.addUser(user);

        if (signupStatus == SignupStatus.INVALID) {
            return new ResponseEntity<>("One or more fields are invalid", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (signupStatus == SignupStatus.CONFLICT) {
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        }

        if (signupStatus == SignupStatus.INTERNAL_ERROR) {
            return new ResponseEntity<>("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);

    }
}
