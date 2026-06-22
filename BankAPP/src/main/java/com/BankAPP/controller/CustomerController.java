package com.BankAPP.controller;

import com.BankAPP.dto.*;
import com.BankAPP.enums.KycStatus;
import com.BankAPP.enums.Status;
import com.BankAPP.model.Customer;
import com.BankAPP.service.CustomerService;
import com.BankAPP.util.JwtUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {
    private final CustomerService customerService;
    private final JwtUtility jwtUtility;

    @GetMapping("/api/v1/customers")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @PostMapping("/api/v1/employees/customers")
    public void add(Principal principal, @Valid @RequestBody CustomerReqDto customerReqDto){
        String employeeUserName = principal.getName();
        customerService.createCustomer(customerReqDto,employeeUserName);
    }

    @GetMapping("/api/v1/customer-details")
    public CustomerProfileDto getCustomerProfile(Principal principal){
        return customerService.getCustomerProfile(principal.getName());
    }

    @GetMapping("/api/v1/customer-accounts/accounts")
    public List<CustomerAccountRespDto> getCustomerAccounts(Principal principal){
        return customerService.getCustomerAccounts(principal.getName());
     }

     @GetMapping("/api/v1/customers/search")
    public List<CustomerSearchRespDto> searchCustomers(Principal principal,
                          @RequestParam(required = false) String name, @RequestParam(required = false) String phone,
                          @RequestParam(required = false) String aadhaar, @RequestParam(required = false) String pan,
                          @RequestParam(required = false) Integer branchId, @RequestParam(required = false) Status status,
                          @RequestParam(required = false) KycStatus kycStatus,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size){

        return customerService.searchCustomer(page,size, name,phone,aadhaar,pan,branchId,status,kycStatus);

     }

     @PutMapping("/api/v1/employee/kyc-update/{customerId}")
    public UpdateKycRespDto updateKycStatus(@PathVariable int customerId,@Valid @RequestBody UpdateKycReqDto dto,Principal principal){
        return customerService.updateKycStatus(customerId,dto,principal.getName());
     }

    @GetMapping("/api/v1/customer/financial-summary")
    public FinancialSummaryRespDto getFinancialSummary(Principal principal){
        String username = principal.getName();
        return customerService.getFinancialSummary(username);
    }

    @GetMapping("/api/v1/customers/analytics/monthly-spending")
    public List<MonthlySpendingRespDto> getMonthlySpending(Principal principal){
        String username = principal.getName();
        return customerService.getMonthlySpending(username);
    }

    @GetMapping("/api/v1/customers/analytics/account-distribution")
    public
    List<AccountDistributionRespDto> getAccountDistribution(Principal principal){
        String username = principal.getName();
        return customerService.getAccountDistribution(username);
    }

    @GetMapping("/api/v1/customers/analytics/transaction-types")
    public List<TransactionTypeAnalyticsRespDto> getTransactionTypeAnalytics(Principal principal){

        String username = principal.getName();
        return customerService.getTransactionTypeAnalytics(username);
    }

}
