package com.BankAPP.mapper;

import com.BankAPP.dto.*;
import com.BankAPP.model.Account;
import com.BankAPP.model.Customer;
import com.BankAPP.model.CustomerAccount;
import com.BankAPP.model.User;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public User mapDtoToUser(CustomerReqDto dto){
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());

        return user;
    }

    public Customer mapDtoToCustomer(CustomerReqDto dto){
        Customer customer = new Customer();
        customer.setFull_name(dto.fullName());
        customer.setDob(dto.dob());
        customer.setGender(dto.gender());
        customer.setPhone(dto.phone());
        customer.setAadhaarNumber(dto.aadhaarNumber());
        customer.setPanNumber(dto.panNumber());
        customer.setAddress(dto.address());
        return customer;
    }

    public CustomerProfileDto mapEntityToDto(Customer customer) {
        return new CustomerProfileDto(
                customer.getId(),
                customer.getFull_name(),
                customer.getDob(),
                customer.getGender(),
                customer.getUser().getEmail(),
                customer.getPhone(),
                customer.getAadhaarNumber(),
                customer.getPanNumber(),
                customer.getAddress(),
                customer.getKyc_status(),
                customer.getCustomer_status(),
                customer.getBranch().getBranch_name(),
                customer.getBranch().getIfscCode(),
                customer.getCreatedByEmployee() != null  ?
                        customer.getCreatedByEmployee().getFullName() : null

        );
    }
    public CustomerAccountRespDto mapAccountToDto(CustomerAccount customerAccount){

        Account account = customerAccount.getAccount();
        return new CustomerAccountRespDto(
                account.getId(),
                account.getAccountNumber(),
                account.getType(),
                account.getBalance(),
                account.getAccountStatus(),
                account.getBranch().getBranch_name(),
                customerAccount.getOwnership_type(),
                customerAccount.getRelation_type(),
                customerAccount.getAccess_level(),

                account.getBankEmployee() != null ?
                        account.getBankEmployee().getFullName() : "Not Approved"
        );
    }

    public CustomerSearchRespDto mapCustomerSearchDto(Customer customer) {
        return new CustomerSearchRespDto(
                customer.getId(),
                customer.getFull_name(),
                customer.getUser().getEmail(),
                customer.getPhone(),
                customer.getAadhaarNumber(),
                customer.getPanNumber(),
                customer.getBranch().getBranch_name(),
                customer.getCustomer_status(),
                customer.getKyc_status()
        );
    }

    public FinancialSummaryRespDto mapFinancialSummaryDto(Double totalBalance, Double monthlySpending,
                                                          Double loanOutStanding, Double totalDeposits,
                                                          Double totalWithdrawals) {
        return new FinancialSummaryRespDto(
                totalBalance == null  ? 0.0  : totalBalance,
                monthlySpending == null ? 0.0 : monthlySpending,
                loanOutStanding == null ? 0.0 : loanOutStanding,
                totalDeposits == null ? 0.0 : totalDeposits,
                totalWithdrawals == null ? 0.0 : totalWithdrawals
        );
    }
}
