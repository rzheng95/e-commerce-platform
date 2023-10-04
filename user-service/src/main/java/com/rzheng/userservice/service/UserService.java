package com.rzheng.userservice.service;

import com.rzheng.userservice.entity.User;
import com.rzheng.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public boolean doesEmailExist (String email) {
       if (email.isEmpty()) return false;
       return userRepository.findAllByEmail(email) != null;
    }
}
