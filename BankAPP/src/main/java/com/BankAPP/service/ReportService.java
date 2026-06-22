package com.BankAPP.service;

import com.BankAPP.dto.FinancialReportRespDto;
import com.BankAPP.dto.GenerateStatementReqDto;
import com.BankAPP.dto.GenerateStatementRespDto;
import com.BankAPP.enums.ReportStatus;
import com.BankAPP.enums.ReportType;
import com.BankAPP.exception.ResourceNotFoundException;
import com.BankAPP.mapper.ReportMapper;
import com.BankAPP.model.*;
import com.BankAPP.respository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final BankEmployeeRepository bankEmployeeRepository;

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final LoanRepaymentRepository loanRepaymentRepository;
    private final BranchRepository branchRepository;

    public GenerateStatementRespDto generateAccountStatement(GenerateStatementReqDto dto, String name) {
        Customer customer = customerRepository.findByUserName(name);

        Account account = accountRepository.findByAccountNumber(dto.accountNumber())
                        .orElseThrow(() -> new RuntimeException("Account not found"));
        if(account.getCustomer().getId() != customer.getId()){
            throw new RuntimeException(
                    "Unauthorized Access");
        }
        if(dto.startDate().isAfter(Instant.now())){
            throw new RuntimeException("Start Date cannot be greater than today");
        }

        if(dto.endDate().isAfter(Instant.now())){
            throw new RuntimeException("End Date cannot be greater than today");
        }

        if(dto.startDate().isAfter(dto.endDate())){
            throw new RuntimeException("Start Date cannot be greater than End Date");
        }


        BankEmployee employee = bankEmployeeRepository.findByUsername(name);

        List<Transaction> transactions = transactionRepository
                .getStatementTransactions(account.getId(), dto.startDate(), dto.endDate());

        Double totalBalance = accountRepository.getCustomerTotalBalance(customer.getId());
        Double totalDeposits = transactionRepository.getTotalDeposits(customer.getId());
        Double totalWithdrawals = transactionRepository.getTotalWithdrawals(customer.getId());
        Long totalTransactions = transactionRepository.getTransactionCount(customer.getId());
        Long totalLoans = loanRepository.getLoanCount(customer.getId());

        String filePath = "reports/account_statement_" + System.currentTimeMillis() + ".pdf";

        Report report = new Report();
        report.setCustomer(customer);
        report.setAccount(account);
        report.setGenerated_by_employee(employee);
        report.setReport_type(ReportType.ACCOUNT_STATEMENT);
        report.setStart_date(dto.startDate());
        report.setEnd_date(dto.endDate());
        report.setFile_path(filePath);
        report.setStatus(ReportStatus.GENERATED);

        report = reportRepository.save(report);

        // Step 7 :
        // Mapper

        return reportMapper
                .mapStatementDto(
                        report, totalBalance,totalDeposits,totalWithdrawals,totalTransactions,totalLoans
                );
    }

    public FinancialReportRespDto generateFinancialReport(String username, int branchId) {
        User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch Not Found"));

        Double totalDeposits = transactionRepository.getTotalDepositsForReports(branchId);
        Double totalWithdrawals = transactionRepository.getTotalWithdrawalsForReports(branchId);
        Double loanPortfolio = loanRepository.getLoanPortfolio(branchId);
        Double emiCollection = loanRepaymentRepository.getEmiCollection(branchId);

        totalDeposits = totalDeposits == null ? 0.0 : totalDeposits;
        totalWithdrawals = totalWithdrawals == null ? 0.0 : totalWithdrawals;
        loanPortfolio = loanPortfolio == null ? 0.0 : loanPortfolio;
        emiCollection = emiCollection == null ? 0.0 : emiCollection;

        return reportMapper.mapFinancialDto(totalDeposits, totalWithdrawals,
                loanPortfolio, emiCollection, username
                );
    }
}
