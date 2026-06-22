package com.BankAPP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RejectLoanReqDto(
        @NotBlank
        @NotNull
        String reason
) {
}
