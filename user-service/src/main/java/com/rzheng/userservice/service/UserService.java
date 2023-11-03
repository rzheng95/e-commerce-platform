package com.rzheng.userservice.service;

import com.rzheng.userservice.dao.UserDao;
import com.rzheng.userservice.model.LoginParams;
import com.rzheng.userservice.model.User;
import com.rzheng.userservice.model.UserParams;
import com.rzheng.userservice.util.LoginStatus;
import com.rzheng.userservice.util.SignupStatus;
import com.rzheng.userservice.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Richard
 */
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

    public Optional<User> getUserByEmail(String email) {
        return this.userDao.getUserByEmail(email);
    }

    public boolean doesEmailExist(String email) {
        if (email == null || email.isEmpty()) {
            log.info("Email is null or empty");
            return false;
        }
        return this.userDao.getUserByEmail(email).isPresent();
    }
// generate JWT token when user logs in successfully and return it to the client as a response header

    public LoginStatus login(LoginParams loginParams) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (!Util.isStringValid(loginParams.getEmail()) || !Util.isStringValid(loginParams.getPassword())) {
            log.info("Email or password is either null or empty");
            return LoginStatus.UNAUTHORIZED;
        }

        Optional<User> userByEmail = this.userDao.getUserByEmail(loginParams.getEmail());

        if (userByEmail.isPresent()) {
            String passwordSalt = userByEmail.get().getPasswordSalt();
            String hashedPassword = Util.hashPassword(loginParams.getPassword(), passwordSalt);

            if (hashedPassword.equals(userByEmail.get().getHashedPassword())) {
                return LoginStatus.SUCCESS;
            }
        }

        return LoginStatus.UNAUTHORIZED;
    }


    public SignupStatus addUser(UserParams userParams) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (!Util.isStringValid(userParams.getUsername())
                || !Util.isStringValid(userParams.getFirstName())
                || !Util.isStringValid(userParams.getLastName())
                || !Util.isStringValid(userParams.getEmail())
                || !Util.isStringValid(userParams.getPassword())
                || !Util.isStringValid(userParams.getRole().name())) {
            log.info("One or more fields are invalid");
            return SignupStatus.INVALID;
        }

        if (this.userDao.getUserByEmail(userParams.getEmail()).isPresent()) {
            log.info("Email already exists");
            return SignupStatus.CONFLICT;
        }

        String salt = Util.generatePasswordSalt();
        String hashedPassword = Util.hashPassword(userParams.getPassword(), salt);


        User newUser = new User(
                userParams.getEmail(),
                userParams.getFirstName(),
                userParams.getLastName(),
                userParams.getUsername(),
                salt,
                hashedPassword,
                userParams.getRole(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );


        int result = this.userDao.addUser(newUser);

        if (result == 0) {
            log.info("User creation failed");
            return SignupStatus.INTERNAL_ERROR;
        }

        log.info("User created successfully");
        return SignupStatus.SUCCESS;
    }
}

