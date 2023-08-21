package com.vedha.blog.service.impl;

import com.vedha.blog.dto.UserDto;
import com.vedha.blog.entity.UserEntity;
import com.vedha.blog.exception.BlogApiException;
import com.vedha.blog.repository.UserRepository;
import com.vedha.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public UserDto getUserByName(String name) {

        UserEntity userEntity = userRepository.getUserByName(name).orElseThrow(() -> new BlogApiException("User Not Found : " + name));

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {

        UserEntity userEntity = userRepository.getUserByEmail(email).orElseThrow(() -> new BlogApiException("User Not Found : " + email));

        return modelMapper.map(userEntity, UserDto.class);
    }
}
