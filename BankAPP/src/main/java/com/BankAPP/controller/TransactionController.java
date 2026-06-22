package com.BankAPP.controller;

import com.BankAPP.dto.*;
import com.BankAPP.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/api/v1/transactions/transfer")
    public TransferMoneyRespDto transferMoney(Principal principal,
                                @Valid @RequestBody TransferMoneyReqDto dto){
        return transactionService.transferMoney(
                        dto,
                        principal.getName()
                );
    }
    @GetMapping("/api/v1/customers/transactions")
    public List<TransactionRespDto> getTransactions(Principal principal,
                                                    TransactionFilterReqDto dto){
        return transactionService
                .getTransactions(
                        principal.getName(),dto);
    }


}
