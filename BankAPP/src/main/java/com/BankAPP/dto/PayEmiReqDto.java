package com.BankAPP.dto;

public record PayEmiReqDto(
        Integer accountId,
        Double amount
) {
}
