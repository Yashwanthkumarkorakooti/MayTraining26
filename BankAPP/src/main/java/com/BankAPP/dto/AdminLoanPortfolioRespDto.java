package com.BankAPP.dto;

import com.BankAPP.enums.LoanType;

public record AdminLoanPortfolioRespDto(
        LoanType loanType,
        Long count
) {
}
