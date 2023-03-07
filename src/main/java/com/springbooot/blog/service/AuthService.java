package com.springbooot.blog.service;

import com.springbooot.blog.payload.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
