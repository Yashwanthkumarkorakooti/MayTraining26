package com.BankAPP.dto;

import com.BankAPP.enums.EmployeeDesignation;
import com.BankAPP.enums.Status;

public record EmployeeDetailsRespDto(
        int employeeId,
        String employeeCode,
        String fullName,
        String email,
        String phone,
        String branchName,
        EmployeeDesignation designation,
        Status employeeStatus,
        Long customersHandled,
        Long accountsApproved,
        Long loansReviewed
) {
}
