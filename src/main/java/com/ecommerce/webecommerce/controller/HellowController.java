package com.ecommerce.webecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HellowController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
