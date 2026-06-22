package com.BankAPP.controller;

import com.BankAPP.dto.*;
import com.BankAPP.enums.EmployeeDesignation;
import com.BankAPP.enums.Status;
import com.BankAPP.model.BankEmployee;
import com.BankAPP.service.BankEmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BankEmployeeController {
    private final BankEmployeeService employeeService;

    @GetMapping("/api/v1/employes")
    public List<BankEmployee> getEmployees(){
        return employeeService.getEmployees();
    }

    @PostMapping("/api/v1/admin/employees")
    public CreateEmployeeRespDto createEmployee(Principal principal,@Valid
                                                @RequestBody CreateEmployeeReqDto dto){

        return employeeService.createEmployee(dto, principal.getName());
    }

    @GetMapping("/api/v1/employees")
    public EmployeeDetailsRespDto getEmployeeDetails(Principal principal){

        return employeeService.getEmployeeDetails(principal.getName());
    }

    @PutMapping("/api/v1/admin/employees/transfer/{employeeId}")
    public TransferEmployeeRespDto transferEmployee(Principal principal,
                                                    @PathVariable int employeeId,
                                                    @Valid @RequestBody TransferEmployeeReqDto dto){

        return employeeService
                .transferEmployee(employeeId, dto, principal.getName());
    }

    @GetMapping("/api/v1/admin/employees")
    public List<EmployeeRespDto> getAllEmployees(
            Principal principal, @RequestParam(required = false) EmployeeDesignation designation,
            @RequestParam(required = false) Integer branchId, @RequestParam(required = false) Status status,
            @RequestParam(required = false) Double minSalary, @RequestParam(required = false) Double maxSalary,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ){
        return employeeService.getAllEmployees(
                        designation, branchId, status, minSalary, maxSalary, page, size
                );
    }

    @GetMapping("/api/v1/employees/analytics/transactions")
    public EmployeeTransactionAnalyticsRespDto getTransactionAnalytics(Principal principal){

        String username = principal.getName();
        return employeeService.getTransactionAnalytics(username);
    }

    @GetMapping("/api/v1/employees/analytics/loan-status")
    public List<EmployeeLoanAnalyticsRespDto> getLoanAnalytics(Principal principal){
        String username = principal.getName();
        return employeeService.getLoanAnalytics(username);
    }

    @GetMapping("/api/v1/employees/loans/pending")
    public List<PendingLoanRespDto> getPendingLoans(Principal principal){
        String username = principal.getName();
        return employeeService.getPendingLoans(username);
    }

    @GetMapping("/api/v1/employees/accounts/pending")
    public List<PendingAccountRespDto> getPendingAccounts(Principal principal){
        String username = principal.getName();
        return employeeService.getPendingAccounts(username);
    }

    @GetMapping("/api/v1/employees/assign-customers")
    public List<EmployeeCustomerRespDto> getAssignedCustomers(
            Principal principal,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String aadhaar,
            @RequestParam(required = false) String pan,
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {

        return employeeService.getAssignedCustomers(
                principal.getName(), name,
                phone, aadhaar, pan, filter, page, size
        );
    }

    @GetMapping("/api/v1/employees/assigned-loans")
    public List<AssignedLoanRespDto> getAssignedLoans(
            Principal principal,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String loanType,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return employeeService.getAssignedLoans(
                principal.getName(), customerName, phone,
                loanType, status, page, size
        );
    }

    @GetMapping("/api/v1/employees/assigned-accounts")
    public List<AssignedAccountRespDto> getAssignedAccounts(
            Principal principal,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String accountType,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return employeeService.getAssignedAccounts(
                principal.getName(), customerName,
                phone, accountType, status, page, size
        );
    }

}
