package com.rzheng.userservice.controller;

import com.rzheng.userservice.model.LoginParams;
import com.rzheng.userservice.model.User;
import com.rzheng.userservice.model.UserParams;
import com.rzheng.userservice.service.UserService;
import com.rzheng.userservice.util.LoginStatus;
import com.rzheng.userservice.util.SignupStatus;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * @author Richard
 */
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginParams loginParams, HttpServletResponse response) throws NoSuchAlgorithmException, InvalidKeySpecException {
        LoginStatus loginStatus = this.userService.login(loginParams);
        if (loginStatus == LoginStatus.SUCCESS) {
            response.addHeader("Authorization", this.userService.getJwtToken(loginParams.getEmail()));
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Login failed", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> addUser(@RequestBody UserParams userParams) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SignupStatus signupStatus = this.userService.addUser(userParams);

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

    @GetMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
//        if (!this.userService.doesEmailExist(email)) {
//            return new ResponseEntity<>("Email does not exist", HttpStatus.NOT_FOUND);
//        }

        this.userService.sendResetPasswordEmail(email);
        return new ResponseEntity<>("Email sent", HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String token, @RequestParam String newPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (!this.userService.doesEmailExist(email)) {
            return new ResponseEntity<>("Email does not exist", HttpStatus.NOT_FOUND);
        }

        if (!this.userService.isResetPasswordTokenValid(email, token)) {
            return new ResponseEntity<>("Token is invalid", HttpStatus.UNAUTHORIZED);
        }

        this.userService.resetPassword(email, newPassword);
        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }

}
