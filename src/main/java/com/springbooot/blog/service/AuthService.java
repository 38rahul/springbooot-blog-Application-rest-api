package com.springbooot.blog.service;

import com.springbooot.blog.payload.LoginDto;
import com.springbooot.blog.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
