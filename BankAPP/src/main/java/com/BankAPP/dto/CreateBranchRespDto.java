package com.BankAPP.dto;

import java.time.Instant;

public record CreateBranchRespDto(
        int branchId,
        String branchName,
        String ifscCode,
        String city,
        String state,
        String email,
        String phone,
        Instant createdAt,
        String message
) {
}
