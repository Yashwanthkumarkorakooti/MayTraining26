package com.practice.dto;

import com.practice.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterReqDto(
        @NotNull(message = "Username cannot be Null")
        @NotBlank(message = "Username cannot be Blank")
        String username,

        @NotNull(message = "password cannot be Null")
        @NotBlank(message = "password cannot be Blank")
        String password,

        Role role
) {
}
