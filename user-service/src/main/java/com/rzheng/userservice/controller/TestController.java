package com.rzheng.userservice.controller;

import com.rzheng.userservice.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Richard
 */
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    private EmailService emailService;

    public TestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("")
    public String test() {
        this.emailService.sendEmail("ruixiangzheng95@gmail.com", "Test subject", "Test content");
        return "test";
    }
}

