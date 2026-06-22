package com.BankAPP.dto;

import com.BankAPP.enums.EmployeeDesignation;
import com.BankAPP.enums.Role;
import com.BankAPP.enums.Status;

public record CreateEmployeeRespDto(
        int employeeId,
        String employeeCode,
        String fullName,
        String username,
        String email,
        String branchName,
        EmployeeDesignation designation,
        Role role,
        Status status
) {
}
