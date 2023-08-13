package com.vedha.blog.controller;

import com.vedha.blog.dto.JwtAuthDto;
import com.vedha.blog.dto.LoginDto;
import com.vedha.blog.dto.RegisterDto;
import com.vedha.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Tag(name = "REST APIs For Authenticate")
public class AuthController {

    private AuthService authService;

    // http://localhost:8080/api/auth/logIn
    @Operation(summary = "Login", description = "Login In To Blog App")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    @PostMapping(value = { "/v1/logIn", "/v1/signIn" },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<JwtAuthDto> login(@RequestBody LoginDto loginDto) {

        String token = authService.login(loginDto);

        JwtAuthDto jwtAuthDto = new JwtAuthDto();
        jwtAuthDto.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthDto);
    }

    // http://localhost:8080/api/auth/register
    @Operation(summary = "Register", description = "Register To The Blog App")
    @ApiResponse(responseCode = "201", description = "Http Status 201 Created")
    @PostMapping(value = { "/v1/register", "/v1/signUp" },
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.ALL_VALUE }
    )
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {

        String register = authService.register(registerDto);

        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }
}
