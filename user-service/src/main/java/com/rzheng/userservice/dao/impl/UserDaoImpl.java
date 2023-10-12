package com.rzheng.userservice.dao.impl;

import com.rzheng.userservice.dao.UserDao;
import com.rzheng.userservice.mapper.UserRowMapper;
import com.rzheng.userservice.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsers() {
        String sql = """
                SELECT username, email, password_hash, first_name, last_name, role, created_at, updated_at
                FROM users
                """;
        return this.jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String sql = """
                SELECT username, email, password_hash, first_name, last_name, role, created_at, updated_at 
                FROM users
                WHERE email = ?
                """;

        return this.jdbcTemplate.query(sql, new UserRowMapper(), email)
                .stream()
                .findFirst();
    }

    @Override
    public int addUser(User user) {
        String sql = """
                INSERT INTO users(username, email, password_hash, first_name, last_name, role, created_at, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        return this.jdbcTemplate.update(
                sql,
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}