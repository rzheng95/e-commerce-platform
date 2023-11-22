package com.rzheng.userservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//
//import java.util.Properties;
//
//@Service
//public class EmailSender {
//    @Value("${app.email.username}")
//    private String emailUsername;
//    @Value("${app.email.password}")
//    private String emailPassword;
//    @Value("${app.email.host}")
//    private String emailHost;
//    @Value("${app.email.port}")
//    private String emailPort;
//    @Value("${app.email.auth}")
//    private String emailAuth;
//    @Value("${app.email.starttls}")
//    private String emailStarttls;
//    @Value("${app.email.from}")
//    private String emailFrom;
//
//
//    public void sendEmail(String to, String subject, String body) throws MessagingException {
//        // Set up mail server properties
//        Properties properties = new Properties();
////        properties.put("mail.smtp.host", emailHost);
////        properties.put("mail.smtp.port", emailPort);
////        properties.put("mail.smtp.auth", emailAuth);
////        properties.put("mail.smtp.starttls.enable", emailStarttls);
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//        properties.put("mail.smtp.auth", true);
//        properties.put("mail.smtp.starttls.enable", true);
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
//
//        System.out.println("emailUsername:" + emailUsername + emailAuth + emailFrom + emailHost + emailPassword + emailPort + emailStarttls);
//    }
//}
