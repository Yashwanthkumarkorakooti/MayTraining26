package com.BankAPP.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WithdrawMoneyReqDto(
        @NotBlank(message = "Account Number cannot be blank")
        String accountNumber,

        @NotNull
        Double amount,

        @NotBlank
        String remarks

) {
}
