package com.vedha.blog.service;

import com.vedha.blog.dto.UserDto;

public interface UserService {

    UserDto getUserByName(String name);

    UserDto getUserByEmail(String email);
}
