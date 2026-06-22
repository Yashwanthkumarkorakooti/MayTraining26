package com.BankAPP.controller;

import com.BankAPP.dto.*;
import com.BankAPP.service.AdminService;
import com.BankAPP.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final AdminService adminService;
    private final LoanService loanService;

    @GetMapping("/api/v1/admin/analytics/branch-performance")
    public List<BranchPerformanceRespDto> getBranchPerformance(Principal principal){
        String username = principal.getName();
        return adminService.getBranchPerformance(username);
    }

    @GetMapping("/api/v1/admin/analytics/transactions")
    public List<AdminTransactionAnalyticsRespDto> getTransactionAnalytics(Principal principal){
        String username = principal.getName();
        return adminService.getTransactionAnalytics(username);
    }

    @GetMapping("/api/v1/admin/analytics/loan-portfolio")
    public List<AdminLoanPortfolioRespDto> getLoanPortfolio(Principal principal){
        String username = principal.getName();
        return adminService.getLoanPortfolio(username);
    }

    @GetMapping("/api/v1/admin/analytics/revenue")
    public List<RevenueAnalyticsRespDto> getRevenueAnalytics(Principal principal){
        String username = principal.getName();
        return adminService.getRevenueAnalytics(username);
    }

    @PutMapping("/api/v1/admin/customers/{customerId}/assign-employee/{employeeId}")
    public String assignCustomerToEmployee(@PathVariable Integer customerId,  @PathVariable Integer employeeId){
        return adminService.assignCustomerToEmployee(customerId, employeeId);
    }

    @PutMapping("/api/v1/admin/loans/{loanId}/assign/{employeeId}")
    public String assignLoanToEmployee(@PathVariable Integer loanId, @PathVariable Integer employeeId){
        return adminService.assignLoanToEmployee(loanId, employeeId);
    }

    @PutMapping("/api/v1/admin/accounts/{accountId}/assign/{employeeId}")
    public String assignAccountToEmployee(@PathVariable Integer accountId, @PathVariable Integer employeeId){
        return adminService.assignAccountToEmployee(accountId, employeeId);
    }

    @PutMapping("/api/v1/admin/customers/{customerId}/assign-kyc/{employeeId}")
    public String assignKycVerification(@PathVariable Integer customerId, @PathVariable Integer employeeId){
        return adminService.assignKycVerification(customerId, employeeId);
    }

    @GetMapping("/api/v1/admin/pending-loans")
    public List<LoanPendingRespDto> getPendingLoans(){
        return loanService.getPendingLoans();
    }

    @GetMapping("/api/v1/admin/delete/{adminId}")
    public void delete(@PathVariable int adminId){
        adminService.delete(adminId);
    }
}
