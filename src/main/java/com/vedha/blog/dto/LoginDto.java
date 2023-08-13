package com.vedha.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "LoginDto", description = "LoginDTO Handles Generate Bearer Token Gen")
public class LoginDto {

    @Schema(name = "nameOrEmail", description = "Enter Your UserName Or Email")
    @NotEmpty(message = "UserName Or Email Should Not Be Empty")
    private String nameOrEmail;

    @Schema(name = "password", description = "Enter Your User Password")
    @NotEmpty(message = "Password Should Not Be Empty")
    private String password;
}
