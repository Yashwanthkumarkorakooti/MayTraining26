package com.BankAPP.dto;

import com.BankAPP.enums.Gender;
import com.BankAPP.enums.KycStatus;
import com.BankAPP.enums.Status;

import java.time.Instant;

public record CustomerProfileDto(
        int customerId,
        String fullName,
        Instant dob,
        Gender gender,
        String email,
        String phone,
        String aadhaarNumber,
        String panNumber,
        String address,
        KycStatus kycStatus,
        Status customerStatus,
        String branchName,
        String ifscCode,
        String createdByEmployee
) {
}
