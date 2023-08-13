package com.vedha.blog.service.impl;

import com.vedha.blog.dto.LoginDto;
import com.vedha.blog.dto.RegisterDto;
import com.vedha.blog.entity.RoleEntity;
import com.vedha.blog.entity.UserEntity;
import com.vedha.blog.exception.BlogApiException;
import com.vedha.blog.repository.RoleRepository;
import com.vedha.blog.repository.UserRepository;
import com.vedha.blog.security.JwtTokenProvider;
import com.vedha.blog.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDto loginDto) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getNameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return jwtTokenProvider.createToken(authenticate);
    }

    @Override
    public String register(RegisterDto registerDto) {

        if (userRepository.existsByName(registerDto.getName())) {

            throw new BlogApiException("User Already Exist : " + registerDto.getName());
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {

            throw new BlogApiException("User Already Exist : " + registerDto.getEmail());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setName(registerDto.getName());
        userEntity.setEmail(registerDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<RoleEntity> roleEntities = new HashSet<>();
        RoleEntity roleUser = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new BlogApiException("Role Not Found : ROLE_USER"));
        roleEntities.add(roleUser);
        userEntity.setRoles(roleEntities);

        userRepository.save(userEntity);

        return "User Registered SuccessFully : " + registerDto.getName();
    }
}
