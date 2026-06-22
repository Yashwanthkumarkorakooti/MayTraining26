package com.BankAPP.dto;

import com.BankAPP.enums.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAccountReqDto(
        @NotNull(message = "Type is required")
        Type type
) {
}
