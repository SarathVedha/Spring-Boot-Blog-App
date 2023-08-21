package com.vedha.blog.controller;

import com.vedha.blog.dto.UserDto;
import com.vedha.blog.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blog")
@AllArgsConstructor
@Tag(name = "REST APIs For Users")
public class UserController {

    private final UserService userService;

    // http://localhost:8080/api/blog/v1/getUser?name=Vedha
    @Operation(summary = "Get Register User", description = "Get Register User By Name From DataBase")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    @GetMapping(value = "/v1/getUserByName",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserDto> getByUserName(@RequestParam("name") String name) {

        UserDto userByName = userService.getUserByName(name);

        return ResponseEntity.ok(userByName);
    }

    @Operation(summary = "Get Register User", description = "Get Register User By Email From DataBase")
    @ApiResponse(responseCode = "200", description = "Http Status 200 Ok")
    @GetMapping(value = { "v1/getUserByEmail" },
            consumes = { MediaType.ALL_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<UserDto> getByUserEmail(@RequestParam("email") String email) {

        UserDto userByEmail = userService.getUserByEmail(email);

        return ResponseEntity.ok(userByEmail);
    }
}
