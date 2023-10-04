package com.rzheng.userservice.dao;

import com.rzheng.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> getUsers();
    Optional<User> getUserByEmail(String email);
    int addUser(User user);
}
