package com.BankAPP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RejectAccountReqDto(
        @NotBlank
        @NotNull
        String reason
) {
}
