package com.BankAPP.dto;

import com.BankAPP.enums.EmployeeDesignation;
import com.BankAPP.enums.Status;

import java.time.Instant;

public record EmployeeRespDto(
        int employeeId,
        String employeeCode,
        String fullName,
        String email,
        String phone,
        String branchName,
        EmployeeDesignation designation,
        Double salary,
        Status status,
        Instant hireDate
) {
}
