package com.BankAPP.service;

import com.BankAPP.dto.AdminLoanPortfolioRespDto;
import com.BankAPP.dto.AdminTransactionAnalyticsRespDto;
import com.BankAPP.dto.BranchPerformanceRespDto;
import com.BankAPP.dto.RevenueAnalyticsRespDto;
import com.BankAPP.enums.LoanType;
import com.BankAPP.enums.Status;
import com.BankAPP.exception.ResourceNotFoundException;
import com.BankAPP.model.*;
import com.BankAPP.respository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {


    private final AdminRepository adminRepository;
    private final BranchRepository branchRepository;
    private final TransactionRepository transactionRepository;
    private final LoanRepository loanRepository;
    private final LoanRepaymentRepository loanRepaymentRepository;
    private final CustomerRepository customerRepository;
    private final BankEmployeeRepository bankEmployeeRepository;
    private final AccountRepository accountRepository;

    public List<BranchPerformanceRespDto> getBranchPerformance(String username) {
        Admin admin = adminRepository.findByUsername(username);

        if(admin == null){
            throw new RuntimeException("Unauthorized access");
        }
        List<Object[]> results = branchRepository.getBranchPerformance();
        List<BranchPerformanceRespDto> response = new ArrayList<>();

        for(Object[] row : results){
            response.add(new BranchPerformanceRespDto(row[0].toString(),
                            row[1] == null ? 0.0 : ((Number) row[1]).doubleValue(),
                            row[2] == null ? 0L : ((Number) row[2]).longValue(),
                            row[3] == null ? 0L : ((Number) row[3]).longValue(),
                            row[4] == null ? 0L : ((Number) row[4]).longValue()
                    )
            );
        }

        return response;
    }

    public List<AdminTransactionAnalyticsRespDto> getTransactionAnalytics(String username) {
        Admin admin = adminRepository.findByUsername(username);
        if(admin == null){
            throw new RuntimeException("Unauthorized access");
        }

        List<Object[]> results = transactionRepository.getTransactionAnalytics();
        List<AdminTransactionAnalyticsRespDto> response = new ArrayList<>();
        String[] months = {"",
                "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
        };

        for(Object[] row : results){
            int month = ((Number) row[0]).intValue();
            response.add(new AdminTransactionAnalyticsRespDto(months[month],
                    row[1] == null ? 0.0 : ((Number) row[1]).doubleValue(),
                    row[2] == null ? 0.0 : ((Number) row[2]).doubleValue(),
                    row[3] == null ? 0.0 : ((Number) row[3]).doubleValue())
            );
        }

        return response;
    }

    public List<AdminLoanPortfolioRespDto> getLoanPortfolio(String username) {
        Admin admin = adminRepository.findByUsername(username);
        if(admin == null){
            throw new RuntimeException("Unauthorized access");
        }

        List<Object[]> results = loanRepository.getLoanPortfolios();
        List<AdminLoanPortfolioRespDto> response = new ArrayList<>();

        for(Object[] row : results){
            response.add(new AdminLoanPortfolioRespDto(
                    (LoanType) row[0],
                    ((Number) row[1]).longValue())
            );
        }

        return response;
    }

    public List<RevenueAnalyticsRespDto> getRevenueAnalytics(String username) {
        Admin admin = adminRepository.findByUsername(username);
        if(admin == null){
            throw new RuntimeException("Unauthorized access");
        }
        List<Object[]> interestData = loanRepository.getInterestRevenue();
        List<Object[]> emiData = loanRepaymentRepository.getEmiRevenue();

        String[] months = {"",
                "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
        };

        List<RevenueAnalyticsRespDto> response = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            double interest = 0.0;
            double emi = 0.0;

            for(Object[] row : interestData){
                int month = ((Number) row[0]).intValue();
                if(month == i){
                    interest = ((Number) row[1]).doubleValue();
                }
            }

            for(Object[] row : emiData){
                int month = ((Number) row[0]).intValue();
                if(month == i){
                    emi = ((Number) row[1]).doubleValue();
                }
            }

            response.add(
                    new RevenueAnalyticsRespDto(
                            months[i], interest, emi
                    )
            );
        }

        return response;
    }

    public String assignCustomerToEmployee(Integer customerId, Integer employeeId) {
        Customer customer = customerRepository.findById(customerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        BankEmployee employee = bankEmployeeRepository.findById(employeeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));


        if(employee.getUser().getStatus() != Status.ACTIVE){
            throw new RuntimeException("Employee is inactive");
        }

        if(customer.getBranch().getId() != employee.getBranch().getId()){
            throw new RuntimeException(
                    "Customer and Employee must belong to same branch"
            );
        }

        customer.setAssigned_employee(employee);
        customerRepository.save(customer);
        return "Customer assigned successfully";
    }

    public String assignLoanToEmployee(Integer loanId, Integer employeeId) {
        Loan loan = loanRepository.findById(loanId)
                        .orElseThrow(() -> new RuntimeException("Loan not found"));

        BankEmployee employee = bankEmployeeRepository.findById(employeeId)
                        .orElseThrow(() -> new RuntimeException("Employee not found"));

        if(employee.getUser().getStatus() != Status.ACTIVE){
            throw new RuntimeException("Employee is inactive");
        }
        if(loan.getCustomer().getBranch().getId() != employee.getBranch().getId()){
            throw new RuntimeException(
                    "Customer and Employee must belong to same branch"
            );
        }

        String designation = String.valueOf(employee.getDesignation());

        if(!designation.equals("LOAN_OFFICER") && !designation.equals("MANAGER")){
            throw new RuntimeException("Only LOAN_OFFICER or MANAGER can receive loans");
        }

        loan.setAssigned_employee(employee);
        loanRepository.save(loan);

        return "Loan assigned successfully";
    }

    public String assignAccountToEmployee(Integer accountId, Integer employeeId) {
        Account account = accountRepository.findById(accountId)
                        .orElseThrow(() -> new RuntimeException("Account not found"));

        BankEmployee employee = bankEmployeeRepository.findById(employeeId)
                        .orElseThrow(() -> new RuntimeException("Employee not found"));

        if(employee.getUser().getStatus() != Status.ACTIVE){
            throw new RuntimeException("Employee is inactive");
        }

        if(account.getCustomer().getBranch().getId() != employee.getBranch().getId()){
            throw new RuntimeException("Customer and Employee must belong to same branch");
        }

        account.setAssigned_employee(employee);
        accountRepository.save(account);

        return "Account assigned successfully";
    }

    public String assignKycVerification(Integer customerId, Integer employeeId) {
        Customer customer = customerRepository.findById(customerId)
                        .orElseThrow(() -> new RuntimeException("Customer not found"));

        BankEmployee employee = bankEmployeeRepository.findById(employeeId)
                        .orElseThrow(() -> new RuntimeException("Employee not found"));


        if(employee.getUser().getStatus() != Status.ACTIVE){
            throw new RuntimeException("Employee is inactive");
        }

        if(customer.getBranch().getId() != employee.getBranch().getId()){
            throw new RuntimeException(
                    "Customer and Employee must belong to same branch"
            );
        }

        customer.setKyc_verified_by_employee(employee);
        customerRepository.save(customer);

        return "KYC verification assigned successfully";
    }

    public Admin getById(int adminId){
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin Not Found"));
    }

    public void delete(int adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Admin Not Found"));

        adminRepository.deleteById(adminId);
    }
}
