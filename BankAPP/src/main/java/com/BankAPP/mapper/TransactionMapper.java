package com.BankAPP.mapper;

import com.BankAPP.dto.TransactionRespDto;
import com.BankAPP.dto.TransferMoneyRespDto;
import com.BankAPP.model.Transaction;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public TransferMoneyRespDto mapTransferDto(Transaction transaction) {
        return new TransferMoneyRespDto(
                transaction.getTransaction_reference(),
                transaction.getFrom_account().getAccountNumber(),
                transaction.getTo_account().getAccountNumber(),
                transaction.getAmount(),
                transaction.getFrom_account().getBalance(),
                transaction.getStatus(),
                transaction.getTransaction_date()
        );
    }

    public TransactionRespDto mapTransactionDto(Transaction transaction) {
                return new
                        TransactionRespDto(
                        transaction.getTransaction_reference(),
                        transaction.getAmount(),
                        transaction.getTransaction_type(),
                        transaction.getBeneficiary() != null ?
                                transaction.getBeneficiary().getBeneficiaryName() :
                                "SELF",
                        transaction.getFrom_account() != null ?
                                transaction.getFrom_account().getAccountNumber() : null,
                        transaction.getTo_account() != null ?
                                transaction.getTo_account().getAccountNumber() : null,
                        transaction.getStatus(),
                        transaction.getRemarks(),
                        transaction.getTransaction_date()
                );
            }



}
