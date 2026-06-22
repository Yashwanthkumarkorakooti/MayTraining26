package com.BankAPP.service;

import com.BankAPP.dto.*;
import com.BankAPP.enums.AccountStatus;
import com.BankAPP.enums.Status;
import com.BankAPP.enums.TransactionStatus;
import com.BankAPP.enums.TransactionType;
import com.BankAPP.mapper.TransactionMapper;
import com.BankAPP.model.Account;
import com.BankAPP.model.Beneficiary;
import com.BankAPP.model.Customer;
import com.BankAPP.model.Transaction;
import com.BankAPP.respository.AccountRepository;
import com.BankAPP.respository.BeneficiaryRepository;
import com.BankAPP.respository.CustomerRepository;
import com.BankAPP.respository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final TransactionMapper transactionMapper;
    private final CustomerRepository customerRepository;


    public TransferMoneyRespDto transferMoney(TransferMoneyReqDto dto, String name) {
        Account fromAccount = accountRepository.findByAccountNumber(dto.fromAccountNumber())
                        .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Beneficiary beneficiary = beneficiaryRepository.findById(dto.beneficiaryId())
                        .orElseThrow(() -> new RuntimeException("Beneficiary not found"));

        Account toAccount = accountRepository.findByAccountNumber(
                                beneficiary.getAccountNumber())
                        .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (fromAccount.getId() == toAccount.getId()) {
            throw new RuntimeException( "Same account transfer not allowed");
        }

        if (fromAccount.getAccountStatus() == AccountStatus.FROZEN) {
            throw new RuntimeException("Account frozen");
        }

        if (beneficiary.getStatus() != Status.ACTIVE) {
            throw new RuntimeException("Beneficiary blocked");
        }

        if (dto.amount() > 100000) {
            throw new RuntimeException("Transfer limit exceeded");
        }

        if (beneficiary.getStatus() == Status.BLOCKED) {
            throw new RuntimeException("Blocked beneficiary cannot receive transfer");
        }

        Double senderBalance = fromAccount.getBalance() == null ? 0.0 : fromAccount.getBalance();
        if (senderBalance < dto.amount()) {
            throw new RuntimeException("Insufficient balance");
        }

        fromAccount.setBalance(senderBalance - dto.amount());

        Double receiverBalance = toAccount.getBalance() == null ? 0.0 : toAccount.getBalance();
        toAccount.setBalance(receiverBalance + dto.amount());
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setTransaction_reference("TXN" + System.currentTimeMillis());
        transaction.setTransaction_type(TransactionType.TRANSFER);
        transaction.setAmount(dto.amount());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setFrom_account(fromAccount);
        transaction.setTo_account(toAccount);
        transaction.setBeneficiary(beneficiary);
        transaction.setRemarks(dto.remarks());
        transaction.setCreated_by_customer(fromAccount.getCustomer());
        transaction = transactionRepository.save(transaction);

        return transactionMapper
                .mapTransferDto(
                        transaction
                );
    }


    public List<TransactionRespDto> getTransactions(String name, TransactionFilterReqDto dto) {
        Pageable pageable = PageRequest.of(
                dto.page() == null ? 0 : dto.page(),
                dto.size() == null ? 10 : dto.size(),
                Sort.by(Sort.Direction.fromString(dto.direction() == null ? "desc" : dto.direction()),
                dto.sortBy() == null ? "transaction_date" : dto.sortBy())
                );

        List<Transaction> list = transactionRepository.getTransactions(name,dto.search(),
                                            dto.type(),dto.status() , pageable).getContent();

        return list
                .stream()
                .map(transactionMapper:: mapTransactionDto)
                .toList();
    }


}
