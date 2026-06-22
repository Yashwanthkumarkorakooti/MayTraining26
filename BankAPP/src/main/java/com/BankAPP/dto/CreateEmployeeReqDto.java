package com.BankAPP.dto;

import com.BankAPP.enums.EmployeeDesignation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateEmployeeReqDto(
        @NotBlank
        @NotNull
        String username,
        @NotBlank
        @NotNull
        String password,
        @NotBlank
        @NotNull
        String email,
        @NotBlank
        @NotNull
        String phone,
        @NotBlank
        @NotNull
        String fullName,
        
        @NotNull
        int branchId,
        EmployeeDesignation designation,

        @NotNull
        Double salary
) {
}
