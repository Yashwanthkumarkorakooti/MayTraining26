package com.BankAPP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransferEmployeeReqDto(
        @NotNull
        Integer newBranchId
) {
}