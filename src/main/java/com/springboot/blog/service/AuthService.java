package com.springboot.blog.service;

import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegisterDto;

public interface AuthService {
    public String login(LoginDto loginDto);
    public String registerUser(RegisterDto registerDto);
}
