package com.BankAPP.mapper;

import com.BankAPP.dto.BeneficiaryRespDto;
import com.BankAPP.dto.BlockBeneficiaryRespDto;
import com.BankAPP.dto.GetBeneficiaryRespDto;
import com.BankAPP.model.Beneficiary;
import com.BankAPP.respository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class BeneficiaryMapper {
    private final TransactionRepository transactionRepository;

    public BeneficiaryMapper(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public BeneficiaryRespDto mapBeneficiaryDto(Beneficiary beneficiary) {
        return new BeneficiaryRespDto(
                beneficiary.getId(),
                beneficiary.getBeneficiaryName(),
                beneficiary.getBankName(),
                beneficiary.getIfscCode(),
                beneficiary.getAccountNumber(),
                beneficiary.getNickname(),
                beneficiary.getStatus().name(),
                "Beneficiary added successfully"
        );
    }

    public BlockBeneficiaryRespDto mapBlockBeneficiaryDto(Beneficiary beneficiary, String oldStatus) {
        return new BlockBeneficiaryRespDto(
                beneficiary.getId(),
                beneficiary.getBeneficiaryName(),
                beneficiary.getAccountNumber(),
                oldStatus,
                beneficiary.getStatus().name(),
                "Beneficiary blocked successfully"
        );
    }

    public GetBeneficiaryRespDto mapGetBeneficiaryDto(Beneficiary beneficiary) {
        Instant lastTransaction = transactionRepository.getLastTransactionDate(beneficiary.getId());

        return new GetBeneficiaryRespDto(
                beneficiary.getId(),
                beneficiary.getBeneficiaryName(),
                beneficiary.getNickname(),
                beneficiary.getBankName(),
                beneficiary.getIfscCode(),
                beneficiary.getAccountNumber(),
                beneficiary.getStatus().name(),
                lastTransaction
        );
    }
}
