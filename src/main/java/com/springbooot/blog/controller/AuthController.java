package com.springbooot.blog.controller;

import com.springbooot.blog.service.AuthService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Build Login Rest Api

}
