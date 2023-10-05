package com.rzheng.userservice.service;

import com.rzheng.userservice.dao.UserDao;
import com.rzheng.userservice.exception.UserAlreadyExistsException;
import com.rzheng.userservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public List<User> getAllUsers() {
        return this.userDao.getUsers();
    }

    public void addUser(User user) {
        this.userDao.getUserByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserAlreadyExistsException("Email already exists");
        });
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        int result = this.userDao.addUser(user);
        if (result != 1) {
            throw new IllegalStateException("Something went wrong, user could not be created");
        }
    }

    public boolean doesEmailExist(String email) {
        if (email == null || email.isEmpty()) {
            log.info("Email is null or empty");
            return false;
        }
        return this.userDao.getUserByEmail(email).isPresent();
    }
}
