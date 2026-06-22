package com.BankAPP.mapper;

import com.BankAPP.dto.FinancialReportRespDto;
import com.BankAPP.dto.GenerateStatementRespDto;
import com.BankAPP.model.Report;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ReportMapper {
    public GenerateStatementRespDto mapStatementDto(Report report,Double totalBalance, Double totalDeposits,
                                                    Double totalWithdrawals, Long totalTransactions, Long totalLoans) {
        return new
                GenerateStatementRespDto(
                report.getId(),
                report.getCustomer().getFull_name(),
                report.getAccount().getAccountNumber(),
                report.getReport_type(),
                report.getFile_path(),
                report.getStatus(),
                report.getGenerated_date(),
                totalBalance,
                totalDeposits,
                totalWithdrawals,
                totalTransactions,
                totalLoans
        );
    }

    public FinancialReportRespDto mapFinancialDto(Double totalDeposits, Double totalWithdrawals,
                                  Double loanPortfolio, Double emiCollection, String username) {
        return new FinancialReportRespDto(
                totalDeposits,
                totalWithdrawals,
                loanPortfolio,
                emiCollection,
                Instant.now(),
                username,
                "Financial report generated successfully"
        );
    }


}
