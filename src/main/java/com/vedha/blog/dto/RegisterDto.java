package com.vedha.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "RegisterDto", description = "RegisterDTO Handles Register To The App")
public class RegisterDto {

    @Schema(name = "name", description = "Enter Your Name")
    @NotEmpty(message = "Name Should Not Be Empty")
    @Size(min = 5, message = "Name Should Be Minimum 5 Chars")
    private String name;

    @Schema(name = "email", description = "Enter Your Email")
    @NotEmpty(message = "Email Should Not Be Empty")
    @Email(message = "Invalid Email Format")
    private String email;

    @Schema(name = "password", description = "Enter Your Password")
    @NotEmpty(message = "Password Should Not Be Empty")
    @Size(min = 8, max = 15, message = "Password Should Be In 8 to 15 Chars")
    private String password;

}
