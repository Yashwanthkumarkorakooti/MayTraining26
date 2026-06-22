package com.BankAPP.dto;

import com.BankAPP.enums.Status;
import com.BankAPP.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record DepositMoneyReqDto(

        String accountNumber,

        @NotNull
        Double amount,

        @NotBlank
        String remarks
) {

}
