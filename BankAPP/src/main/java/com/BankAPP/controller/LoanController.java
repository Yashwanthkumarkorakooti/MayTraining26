package com.BankAPP.controller;

import com.BankAPP.dto.*;
import com.BankAPP.service.AccountService;
import com.BankAPP.service.LoanService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class LoanController {
    private final LoanService loanService;
    private final AccountService accountService;

    @PostMapping("/api/v1/customers/loans/apply")
    public ApplyLoanRespDto applyLoan(
            Principal principal,
            @Valid @RequestBody ApplyLoanReqDto dto){

        return loanService.applyLoan( dto, principal.getName());
    }
    @PutMapping("/api/v1/employees/loans/approve/{loanId}")
    public ApproveLoanRespDto approveLoan(Principal principal, @PathVariable int loanId){
        return loanService
                .approveLoan(loanId, principal.getName());
    }
    @PutMapping("/api/v1/employees/loans/reject/{loanId}")
    public RejectLoanRespDto rejectLoan(Principal principal,
                                        @PathVariable int loanId,
                                        @Valid @RequestBody RejectLoanReqDto dto){

        return loanService.rejectLoan(loanId, dto, principal.getName());
    }


    @PostMapping("/api/v1/employees/loans/{loanId}/disburse")
    public LoanDisburseRespDto disburseLoan(Principal principal,
                                            @PathVariable int loanId){
        String username = principal.getName();
        return loanService.disburseLoan(username, loanId);
    }

    @GetMapping("/api/v1/customers/loans")
    public List<GetCustomerLoanRespDto> getCustomerLoans(Principal principal){
        String username = principal.getName();
        return loanService.getCustomerLoans(username);
    }

    @GetMapping("/api/v1/loans/{loanId}")
    public LoanDetailsRespDto getLoanDetails(Principal principal, @PathVariable int loanId){
        String username = principal.getName();
        return loanService.getLoanDetails(username, loanId);
    }

    @PostMapping("/api/v1/loans/{loanId}/pay-emi")
    public PayEmiRespDto payEmi(Principal principal, @PathVariable int loanId,@RequestBody PayEmiReqDto dto){
        String username = principal.getName();
        return loanService.payEmi(username, loanId,dto);
    }

    @GetMapping("/api/v1/loans/{loanId}/repayments")
    public List<RepaymentHistoryRespDto> getRepaymentHistory(Principal principal, @PathVariable int loanId){
        String username = principal.getName();
        return loanService.getRepaymentHistory(username, loanId);
    }

    @GetMapping("/api/v1/customers/loan-eligibility")
    public LoanEligibilityRespDto getLoanEligibility(Principal principal){
        String username = principal.getName();
        return loanService.getLoanEligibility(username);
    }
    @GetMapping("/api/v1/employees/loans/review/{loanId}")
    public EmployeeLoanReviewRespDto reviewLoan(Principal principal, @PathVariable int loanId){
        String username = principal.getName();
        return loanService.reviewLoan(username, loanId);
    }

}
