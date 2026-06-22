package com.BankAPP.service;

import com.BankAPP.dto.*;
import com.BankAPP.enums.*;
import com.BankAPP.exception.ResourceNotFoundException;
import com.BankAPP.mapper.LoanMapper;
import com.BankAPP.model.*;
import com.BankAPP.respository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LoanService {
    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final LoanMapper loanMapper;
    private final BankEmployeeRepository bankEmployeeRepository;
    private final LoanRepaymentRepository loanRepaymentRepository;

    public ApplyLoanRespDto applyLoan( ApplyLoanReqDto dto, String name) {
        Customer customer = customerRepository.findByUserName(name);

        if (customer.getCustomer_status() == Status.BLOCKED) {
            throw new RuntimeException("Blacklisted customer");
        }
        if (customer.getCustomer_status() != Status.ACTIVE) {
            throw new RuntimeException("Inactive customer");
        }

        boolean activeLoan = loanRepository.existsActiveLoan(customer.getId());
        if (activeLoan) {
            throw new RuntimeException("Existing overdue loan found");
        }

        Double averageBalance = accountRepository.calculateAverageBalance(customer.getId());
        Double monthlySpending = transactionRepository.calculateMonthlySpending(customer.getId());

        if (averageBalance == null) {averageBalance = 0.0;}
        if (monthlySpending == null) {monthlySpending = 0.0;}
        if (dto.loanAmount() > averageBalance * 10) {
            throw new RuntimeException("Loan eligibility failed");
        }

        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setLoan_type(dto.loanType());
        loan.setLoan_amount(dto.loanAmount());
        loan.setLoan_term_months(dto.loanTermMonths());
        loan.setLoan_status(LoanStatus.PENDING);
        loan = loanRepository.save(loan);

        return loanMapper
                .mapLoanToDto(
                        loan
                );

    }

    public ApproveLoanRespDto approveLoan(int loanId, String name) {
        System.out.println("USERNAME: " + name);
        BankEmployee employee = bankEmployeeRepository.findByUsername(name);

        System.out.println(
                "EMPLOYEE: " +
                        employee.getFullName()
        );

        System.out.println(
                "DESIGNATION: " +
                        employee.getDesignation()
        );

        if (employee.getDesignation() != EmployeeDesignation.MANAGER &&
                employee.getDesignation() != EmployeeDesignation.LOAN_OFFICER) {
                     throw new RuntimeException("Not authorized");
        }


        Loan loan = loanRepository.findById(loanId)
                        .orElseThrow(() -> new RuntimeException("Loan not found"));

        System.out.println(
                "LOAN STATUS: " +
                        loan.getLoan_status()
        );

        if (loan.getLoan_status()  != LoanStatus.PENDING) {
            throw new RuntimeException("Loan already processed");
        }

        float interestRate = 8.5F;

        double principal = loan.getLoan_amount();
        int months = loan.getLoan_term_months();
        double monthlyRate = interestRate / 12 / 100;
        double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) /
                        (Math.pow(1 + monthlyRate, months) - 1);

        loan.setReviewed_by_employee(employee);
        loan.setApproval_date(Instant.now());
        loan.setInterest_rate(interestRate);
        loan.setEmi_amount(emi);
        loan.setRemaining_balance(principal);
        loan.setLoan_status(LoanStatus.APPROVED);
        loan = loanRepository.save(loan);


        return loanMapper
                .mapApproveLoanDto(
                        loan
                );
    }

    public RejectLoanRespDto rejectLoan(int loanId, RejectLoanReqDto dto, String name) {
        if (dto.reason() == null || dto.reason().isBlank()) {
            throw new RuntimeException("Rejection reason mandatory");
        }

        BankEmployee employee = bankEmployeeRepository.findByUsername(name);


        Loan loan = loanRepository.findById(loanId).orElseThrow(() ->
                        new RuntimeException("Loan not found"));


        if (loan.getLoan_status() != LoanStatus.PENDING) {
            throw new RuntimeException("Loan already processed");
        }

        loan.setLoan_status(LoanStatus.REJECTED);
        loan.setRejection_reason(dto.reason());
        loan.setReviewed_by_employee(employee);

        loan = loanRepository.save(loan);

        return loanMapper
                .mapRejectLoanDto(
                        loan
                );
    }

    public LoanDisburseRespDto disburseLoan(String username, int loanId) {
        BankEmployee employee = bankEmployeeRepository.findByUsername(username);

        if (employee.getDesignation()
                != EmployeeDesignation.MANAGER
                &&
                employee.getDesignation()
                        != EmployeeDesignation.LOAN_OFFICER) {

            throw new RuntimeException(
                    "Unauthorized access"
            );
        }

        Loan loan =
                loanRepository.findById(
                        loanId
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan not found"
                        ));

        if(loan.getCustomer().getBranch().getId() != employee.getBranch().getId()){
            throw new RuntimeException("Loan should be applied in your branch");
        }

        if (loan.getLoan_status()
                != LoanStatus.APPROVED) {

            throw new RuntimeException(
                    "Only approved loan can be disbursed"
            );
        }

        List<Account> accounts =
                accountRepository.findByCustomersId(
                        loan.getCustomer().getId()
                );

        if (accounts.isEmpty()) {
            throw new RuntimeException(
                    "Customer account not found"
            );
        }

        Account account =
                accounts.get(0);

        Double balance =
                account.getBalance() == null
                        ? 0.0
                        : account.getBalance();

        account.setBalance(
                balance +
                        loan.getLoan_amount()
        );

        accountRepository.save(account);

        Transaction transaction =
                new Transaction();

        transaction.setTransaction_reference(
                UUID.randomUUID().toString()
        );

        transaction.setTransaction_type(
                TransactionType.DEPOSIT
        );

        transaction.setAmount(
                loan.getLoan_amount()
        );

        transaction.setStatus(
                TransactionStatus.SUCCESS
        );

        transaction.setTo_account(account);

        transaction.setCreated_by_customer(
                loan.getCustomer()
        );

        transaction.setRemarks(
                "Loan Disbursement"
        );

        transaction =
                transactionRepository
                        .save(transaction);

        loan.setLoan_status(
                LoanStatus.DISBURSED
        );

        loan.setDisbursement_date(
                Instant.now()
        );

        loan =
                loanRepository.save(loan);

        return loanMapper
                .mapLoanDisburseDto(
                        loan,
                        account,
                        transaction
                );
    }
    public List<GetCustomerLoanRespDto> getCustomerLoans(String username) {
        Customer customer = customerRepository.findByUserName(username);

//        if (customer.getId()!= customerId) {
//            throw new RuntimeException("Unauthorized access");
//        }

        List<Loan> loans = loanRepository.getCustomerLoans(customer.getId());

        return loans
                .stream()
                .map(loanMapper:: mapCustomerLoanDto)
                .toList();
    }

    public LoanDetailsRespDto getLoanDetails(String username, int loanId) {
        Customer customer = customerRepository.findByUserName(username);

        Loan loan = loanRepository.getLoanDetails(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));


        if (loan.getCustomer().getId() != customer.getId()) {
            throw new RuntimeException("Unauthorized access");
        }

        List<LoanRepayment> repayments = loanRepaymentRepository.getLoanRepayments(loanId);

        return loanMapper.mapLoanDetailsDto(loan, repayments);
    }

    public PayEmiRespDto payEmi(String username, int loanId,PayEmiReqDto dto) {
        Customer customer = customerRepository.findByUserName(username);

        Loan loan = loanRepository.findById(loanId)
                        .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        if (dto.amount() <= 0) {
            throw new RuntimeException("Invalid EMI amount");
        }

        if (loan.getCustomer().getId() != customer.getId()) {
            throw new RuntimeException("Unauthorized access");
        }
        if (loan.getLoan_status() == LoanStatus.CLOSED) {
            throw new RuntimeException("Loan already closed");
        }
        if (loan.getLoan_status() != LoanStatus.DISBURSED) {
            throw new RuntimeException("Loan is not active");
        }

        Account account = accountRepository.findById(dto.accountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        
        if(account.getCustomer().getId() != customer.getId()){
            throw new RuntimeException(
                    "You can only use your own account"
            );
        }

        if(account.getAccountStatus() != AccountStatus.ACTIVE){
            throw new RuntimeException(
                    "Selected account is not active"
            );
        }
        if (account == null) {
            throw new RuntimeException("Account not found");
        }

        if (account.getBalance() < dto.amount()) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - dto.amount());
        accountRepository.save(account);

        Double updatedRemainingBalance = loan.getRemaining_balance() - dto.amount();
        loan.setRemaining_balance(updatedRemainingBalance);

        if (updatedRemainingBalance <= 0) {
            loan.setLoan_status(LoanStatus.CLOSED);
            loan.setRemaining_balance(0.0);
        }
        loanRepository.save(loan);

        LoanRepayment repayment = new LoanRepayment();
        repayment.setLoan(loan);
        repayment.setAmount_paid(dto.amount());
        repayment.setRemaining_balance(loan.getRemaining_balance());
        repayment.setPayment_method(PaymentMethod.BANK_TRANSFER);
        repayment.setPayment_status(PaymentStatus.SUCCESS);
        repayment = loanRepaymentRepository.save(repayment);

        Transaction transaction = new Transaction();
        transaction.setTransaction_reference(UUID.randomUUID().toString());
        transaction.setTransaction_type(TransactionType.WITHDRAWAL);
        transaction.setAmount(dto.amount());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setFrom_account(account);
        transaction.setCreated_by_customer(customer);
        transaction.setRemarks("Loan EMI Payment");
        transaction = transactionRepository.save(transaction);


        return loanMapper.mapPayEmiDto(loan, account, transaction, repayment);
    }

    public List<RepaymentHistoryRespDto> getRepaymentHistory(String username, int loanId) {
        Customer customer = customerRepository.findByUserName(username);

        Loan loan = loanRepository.findById(loanId)
                        .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        if (loan.getCustomer().getId() != customer.getId()) {
            throw new RuntimeException("Unauthorized access");
        }

        List<LoanRepayment> repayments = loanRepaymentRepository.getRepaymentHistory(loanId);

        return repayments
                .stream()
                .map(loanMapper:: mapRepaymentHistoryDto)
                .toList();
    }

    public LoanEligibilityRespDto getLoanEligibility(String username) {
        Customer customer = customerRepository.findByUserName(username);

//        if (customer.getId() != customerId) {
//            throw new ResourceNotFoundException("Unauthorized access");
//        }

        Double averageBalance = accountRepository.getAverageBalance(customer.getId());
        Double inboundCashFlow = transactionRepository.getInboundCashFlow(customer.getId());
        Double outboundCashFlow = transactionRepository.getOutboundCashFlow(customer.getId());
        Double emiBurden = loanRepository.getEmiBurden(customer.getId());
        Double remainingLoanBalance = loanRepository.getRemainingLoanBalance(customer.getId());

        averageBalance = averageBalance == null ? 0.0 : averageBalance;
        inboundCashFlow = inboundCashFlow == null ? 0.0 : inboundCashFlow;
        outboundCashFlow = outboundCashFlow == null ? 0.0 : outboundCashFlow;
        emiBurden = emiBurden == null ? 0.0 : emiBurden;
        remainingLoanBalance = remainingLoanBalance == null ? 0.0 : remainingLoanBalance;


        Double netDisposableIncome = inboundCashFlow - outboundCashFlow - emiBurden;
        Double eligibleAmount = netDisposableIncome * 24;

        if (eligibleAmount < 0)  eligibleAmount = 0.0;
        int tenure;
        if (eligibleAmount <= 200000)  tenure = 12;
        else if (eligibleAmount <= 500000) tenure = 36;
        else tenure = 60;


        String riskScore;
        double emiPercentage = inboundCashFlow == 0 ? 0 : (emiBurden /inboundCashFlow) * 100;

        if (remainingLoanBalance > 500000 || emiPercentage > 50) riskScore = "HIGH";
        else if (emiPercentage >= 30) riskScore = "MEDIUM";
        else riskScore = "LOW";

        return loanMapper.mapLoanEligibilityDto(averageBalance, inboundCashFlow,
                        outboundCashFlow, emiBurden, remainingLoanBalance,
                        netDisposableIncome, eligibleAmount, tenure, riskScore
                );

    }

    public EmployeeLoanReviewRespDto reviewLoan(String username, int loanId) {
        BankEmployee employee = bankEmployeeRepository.findByUsername(username);
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan Not Found"));

        Customer customer = loan.getCustomer();
        List<Account> accounts = accountRepository.findAccountByCustomerId(customer.getId());

        if(accounts.isEmpty()){
            throw new RuntimeException("Customer account not found");
        }

        Account account = accounts.get(0);

        Double avgDeposit = transactionRepository.getAverageDeposits(customer.getId());
        Double avgWithdrawal = transactionRepository.getAverageWithdrawals(customer.getId());
        Integer existingLoans = loanRepository.countByCustomerId(customer.getId());

        avgDeposit = avgDeposit == null ? 0.0 : avgDeposit;
        avgWithdrawal = avgWithdrawal == null ? 0.0 : avgWithdrawal;
        existingLoans = existingLoans == null ? 0 : existingLoans;

        double balance = account.getBalance() == null ? 0.0 : account.getBalance();
        double loanAmount = loan.getLoan_amount();

        int score = 0;

        if(avgDeposit > avgWithdrawal){
            score += 30;
        }
        if(balance > 50000){
            score += 25;
        }
        if(existingLoans == 0){
            score += 20;
        }
        if(loanAmount < balance * 2){
            score += 15;
        }

        if(customer.getKyc_status() == KycStatus.VERIFIED){
            score += 10;
        }

        String risk;

        if(score >= 70){
            risk = "LOW";
        }
        else if(score >= 40){
            risk = "MEDIUM";
        }
        else{
            risk = "HIGH";
        }

        return loanMapper.mapReviewDto(
                loan,
                customer,
                account,
                avgDeposit,
                avgWithdrawal,
                existingLoans,
                risk,
                score
        );
    }


    public List<LoanPendingRespDto> getPendingLoans() {
        List<Loan> list = loanRepository.findAll();

        return list
                .stream()
                .map(loanMapper :: mapToDto )
                .toList();
    }
}
