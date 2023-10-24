package com.rzheng.userservice.service;

import com.rzheng.userservice.dao.UserDao;
import com.rzheng.userservice.exception.UserAlreadyExistsException;
import com.rzheng.userservice.model.User;
import com.rzheng.userservice.util.LoginStatus;
import com.rzheng.userservice.util.Role;
import com.rzheng.userservice.util.Util;
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

    public boolean doesEmailExist(String email) {
        if (email == null || email.isEmpty()) {
            log.info("Email is null or empty");
            return false;
        }
        return this.userDao.getUserByEmail(email).isPresent();
    }

    public LoginStatus login(String email, String password) {
        if (!Util.isStringValid(email) || !Util.isStringValid(password)) {
            log.info("Email or password is either null or empty");
            return LoginStatus.UNAUTHORIZED;
        }

        return this.userDao.getUserByEmail(email)
                .filter(user -> user.getPasswordHash().equals(password))
                .map(user -> LoginStatus.SUCCESS)
                .orElse(LoginStatus.UNAUTHORIZED);
    }


    public void addUser(User user) {
        this.userDao.getUserByEmail(user.getEmail()).ifPresent(u -> {
            throw new UserAlreadyExistsException("Email already exists");
        });
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole(Role.valueOf("CUSTOMER"));

        int result = this.userDao.addUser(user);
        if (result != 1) {
            throw new IllegalStateException("Something went wrong, user could not be created");
        }
    }

}
