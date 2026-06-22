package com.BankAPP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransferMoneyReqDto(
        @NotBlank
        String fromAccountNumber,

        @NotNull
        Integer beneficiaryId,

        @NotNull
        Double amount,

        @NotBlank
        String remarks
) {
}
