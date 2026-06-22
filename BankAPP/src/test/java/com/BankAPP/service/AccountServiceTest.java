package com.BankAPP.service;


import com.BankAPP.dto.ApproveAccountRespDto;
import com.BankAPP.dto.DepositMoneyReqDto;
import com.BankAPP.dto.DepositMoneyRespDto;
import com.BankAPP.dto.FreezeAccountResponseDto;
import com.BankAPP.enums.AccountStatus;
import com.BankAPP.enums.EmployeeDesignation;
import com.BankAPP.enums.TransactionStatus;
import com.BankAPP.exception.ResourceNotFoundException;
import com.BankAPP.mapper.AccountMapper;
import com.BankAPP.model.Account;
import com.BankAPP.model.BankEmployee;
import com.BankAPP.model.Branch;
import com.BankAPP.model.Customer;
import com.BankAPP.respository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private BankEmployeeRepository bankEmployeeRepository;
    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;


    private Account account;
    private Customer customer;
    private BankEmployee employee;
    private Branch branch;

    @BeforeEach
    public void sampleData(){
        branch = new Branch();
        branch.setId(1);
        branch.setBranch_name("Maverick branch");

        customer = new Customer();
        customer.setId(1);
        customer.setFull_name("Yashwanth");
        customer.setBranch(branch);

        employee = new BankEmployee();
        employee.setId(1);
        employee.setFullName("Kumar");
        employee.setDesignation(EmployeeDesignation.MANAGER);
        employee.setBranch(branch);

        account = new Account();
        account.setId(1);
        account.setCustomer(customer);
        account.setBranch(branch);
        account.setBalance(2500.0);
        account.setMinimum_balance(1000.0);
        account.setAccountNumber("MAV2525252525");
        account.setAccountStatus(AccountStatus.ACTIVE);
    }

    @Test
    public void getAccounts_mustReturnAllAccounts(){
        when(accountRepository.findAll()).thenReturn(List.of(account));
        List<Account> accounts = accountService.getAccounts();

        assertThat(accounts).hasSize(1);
        assertThat(accounts.getFirst().getAccountNumber()).isEqualTo("MAV2525252525");

        verify(accountRepository,times(1)).findAll();
    }

    @Test
    public void getAccounts_ReturnEmptyList(){
        when(accountRepository.findAll()).thenReturn(List.of());
        List<Account> actualCall = accountService.getAccounts();

        assertThat(actualCall).hasSize(0);
        assertThat(actualCall).isEmpty();
    }

    @Test
    public void freezeAccount_success(){
        account.setAccountStatus(AccountStatus.ACTIVE);
        FreezeAccountResponseDto response = new FreezeAccountResponseDto(
                100,
                "Yashwanth",
                "MAV2525252525",
                AccountStatus.ACTIVE,
                AccountStatus.FROZEN,
                "Frozen Successfully"
        );

        when(bankEmployeeRepository.findByUsername("Kumar")).thenReturn(employee);
        when(accountRepository.findById(100)).thenReturn(Optional.of(account));
        when(accountMapper.mapFrozenDto(account,AccountStatus.ACTIVE)).thenReturn(response);

        FreezeAccountResponseDto result = accountService.freezeAccount("Kumar",100);

        assertThat(result.newStatus()).isEqualTo(AccountStatus.FROZEN);

        verify(accountRepository,times(1)).save(account);
    }

    @Test
    public void freezeAccount_differentBranch(){
        Branch anotherBranch = new Branch();
        anotherBranch.setId(25);

        account.setBranch(anotherBranch);

        when(bankEmployeeRepository.findByUsername("manager")).thenReturn(employee);
        when(accountRepository.findById(100)).thenReturn(Optional.of(account));

        assertThatThrownBy(() ->
                accountService.freezeAccount("manager",100))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("You can freeze only accounts from your branch");
    }

    @Test
    public void deleteById_accountExists(){
        when(accountRepository.findById(100)).thenReturn(Optional.of(account));

        doNothing().when(accountRepository).deleteById(100);
        accountService.deleteById(100);

        verify(accountRepository,times(1)).findById(100);
        verify(accountRepository,times(1)).deleteById(100);
    }

    @Test
    public void deleteById_accountNotFound(){
        when(accountRepository.findById(100)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.deleteById(100))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Account Not Found");

        verify(accountRepository,times(1)).findById(100);
        verify(accountRepository,never()).deleteById(100);
    }



}
