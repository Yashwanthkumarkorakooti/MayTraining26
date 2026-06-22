package com.BankAPP.controller;

import com.BankAPP.dto.*;
import com.BankAPP.enums.Type;
import com.BankAPP.model.Account;
import com.BankAPP.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/api/v1/accounts")
    public List<Account> getAccounts(){
        return accountService.getAccounts();
    }

    @PostMapping(value = "/api/v1/customer/accounts/request",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void requestAccount(@RequestParam("type") Type type , Principal principal,
                               @RequestParam("aadhar")MultipartFile aadhar,
                               @RequestParam("pan")MultipartFile pan,
                               @RequestParam("photo")MultipartFile photo
                               ) throws IOException {
        accountService.requestAccount(type,principal.getName(),aadhar,pan,photo);
    }

    @PutMapping("/api/v1/customer/accounts/approve/{accountId}")
    public ApproveAccountRespDto approveAccount(@PathVariable int accountId,Principal principal){
        return accountService.approveAccount(accountId,principal.getName());
    }

    @PutMapping("/api/v1/customer/accounts/reject/{accountId}")
    public RejectAccountRespDto rejectAccount(@PathVariable int accountId,@RequestBody RejectAccountReqDto dto, Principal principal){
        return accountService.rejectAccount(accountId,dto,principal.getName());
    }

    @PostMapping("api/v1/customer/accounts/close-request/{accountId}")
    public CloseAccountRespDto closeAccountRequest(Principal principal, @PathVariable int accountId){
        return accountService.closeAccountRequest(accountId, principal.getName());
    }

    @GetMapping("/api/v1/accounts/{accountId}")
    public AccountDetailsRespDto getAccountDetails(Principal principal,
                                                   @PathVariable int accountId){
        return accountService.getAccountDetails(accountId, principal.getName());
    }

    @PostMapping("api/v1/accounts/employee/deposit")
    public DepositMoneyRespDto depositMoney(
            Principal principal,
            @Valid @RequestBody DepositMoneyReqDto dto){
        String userName = principal.getName();
        return accountService.depositMoney(userName,dto);
    }
    @PostMapping("api/v1/accounts/employee/withdraw")
    public WithdrawMoneyRespDto withdrawMoney(Principal principal,
                                              @Valid @RequestBody WithdrawMoneyReqDto dto
    ){
        String userName = principal.getName();
        return accountService.withdrawMoney(dto, userName);
    }

    @PutMapping("/api/v1/employees/accounts/freeze/{accountId}")
    public FreezeAccountResponseDto freezeAccount(Principal principal,@PathVariable int accountId){
        String employee = principal.getName();
        return accountService.freezeAccount(employee,accountId);
    }
    @PutMapping("/api/v1/employees/accounts/activate/{accountId}")
    public FreezeAccountResponseDto activateAccount(Principal principal, @PathVariable int accountId){
        String employee = principal.getName();
        return accountService.activateAccount(employee, accountId);
    }


    @PostMapping("/api/v1/accounts/joint-holder/{accountId}")
    public JointAccountRespDto addJointHolder(Principal principal,@PathVariable int accountId,
                                             @RequestBody JointAccountReqDto dto){
        String employee = principal.getName();
        return accountService.addJointHolder(employee,accountId,dto);
    }

    @GetMapping("/api/v1/customers/accounts")
    public List<CustomerAccountDto> getAccounts(Principal principal){
        return accountService.getCustomerAccounts(principal.getName());
    }

    @DeleteMapping("/api/v1/customer/delete/{accountId}")
    public void deleteById(@PathVariable int accountId){
        accountService.deleteById(accountId);
    }
    
}

