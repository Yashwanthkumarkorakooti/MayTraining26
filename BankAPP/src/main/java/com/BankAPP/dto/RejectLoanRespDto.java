package com.BankAPP.dto;

import com.BankAPP.enums.LoanStatus;
import com.BankAPP.enums.LoanType;

public record RejectLoanRespDto(
        int loanId,
        String customerName,
        LoanType loanType,
        Double loanAmount,
        LoanStatus loanStatus,
        String rejectionReason,
        String reviewedBy
) {
}
