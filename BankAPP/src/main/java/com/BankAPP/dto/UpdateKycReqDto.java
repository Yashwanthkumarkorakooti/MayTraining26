package com.BankAPP.dto;

import com.BankAPP.enums.KycStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateKycReqDto(
        @NotNull
        KycStatus kycStatus
) {
}
