package com.rzheng.userservice.service;

import com.rzheng.userservice.config.JwtTokenProvider;
import com.rzheng.userservice.dao.UserDao;
import com.rzheng.userservice.model.LoginParams;
import com.rzheng.userservice.model.User;
import com.rzheng.userservice.model.UserParams;
import com.rzheng.userservice.util.LoginStatus;
import com.rzheng.userservice.util.SignupStatus;
import com.rzheng.userservice.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
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
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserDao userDao, @Lazy JwtTokenProvider jwtTokenProvider) {
        this.userDao = userDao;
        this.jwtTokenProvider = jwtTokenProvider;
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

    public String getJwtToken(String email) {
        return "Bearer " + this.jwtTokenProvider.generateToken(email);
    }


//    public static void sendEmail(String to, String subject, String body) throws MessagingException {
//
//
//        // Set up mail server properties
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//
//        // Create a session with authentication
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("ecommercemailsender@gmail.com", "uqpdufrigkrarznu");
//            }
//        });
//
//        // Create a message
//        Message message = new MimeMessage(session);
//        message.setFrom(new InternetAddress("ecommercemailsender@gmail.com"));
//        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//        message.setSubject(subject);
//        message.setText(body);
//
//        // Send the message
//        Transport.send(message);
//
//        System.out.println("Message sent successfully");
//    }
}

