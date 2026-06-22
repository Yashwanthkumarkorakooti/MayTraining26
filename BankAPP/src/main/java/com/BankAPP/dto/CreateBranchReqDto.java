package com.BankAPP.dto;

public record CreateBranchReqDto(
        String branchName,
        String ifscCode,
        String city,
        String state,
        String email,
        String phone,
        String address,
        String pincode
) {
}
