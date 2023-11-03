package com.rzheng.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Richard
 */
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("")
    public String test() {
        return "test";
    }
}
