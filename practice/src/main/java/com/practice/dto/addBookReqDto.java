package com.practice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record addBookReqDto(
        @NotBlank(message = "Title cannot be Blank")
        @NotNull(message = "Title cannot be Null")
        String title,

        @NotBlank(message = "Summary cannot be Blank")
        @NotNull(message = "Summary cannot be Null")
        String summary
) {
}
