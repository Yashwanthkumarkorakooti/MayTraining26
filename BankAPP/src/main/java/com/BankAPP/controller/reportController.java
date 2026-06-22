package com.BankAPP.controller;

import com.BankAPP.dto.FinancialReportRespDto;
import com.BankAPP.dto.GenerateStatementReqDto;
import com.BankAPP.dto.GenerateStatementRespDto;
import com.BankAPP.service.ReportService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class reportController {
    private final ReportService reportService;

    @PostMapping("/api/v1/reports/account-statement")
    public GenerateStatementRespDto generateAccountStatement(Principal principal,
                                                             @Valid @RequestBody GenerateStatementReqDto dto){

        return reportService.generateAccountStatement(dto, principal.getName());
    }

    @PostMapping("/api/v1/reports/financial/{branchId}")
    public FinancialReportRespDto generateFinancialReport(Principal principal, @PathVariable int branchId){
        String username = principal.getName();
        return reportService.generateFinancialReport(username,branchId);
    }
}
