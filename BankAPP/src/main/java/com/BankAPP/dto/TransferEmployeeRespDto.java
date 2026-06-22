package com.BankAPP.dto;

public record TransferEmployeeRespDto(
        int employeeId,
        String employeeName,
        String employeeCode,
        String oldBranch,
        String newBranch,
        String message
) {
}
