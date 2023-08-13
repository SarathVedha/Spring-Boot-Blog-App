package com.vedha.blog.service;

import com.vedha.blog.dto.LoginDto;
import com.vedha.blog.dto.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
