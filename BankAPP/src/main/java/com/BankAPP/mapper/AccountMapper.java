package com.BankAPP.mapper;

import com.BankAPP.dto.*;
import com.BankAPP.enums.AccountStatus;
import com.BankAPP.enums.OwnershipType;
import com.BankAPP.model.Account;
import com.BankAPP.model.BankEmployee;
import com.BankAPP.model.CustomerAccount;
import com.BankAPP.model.Transaction;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountMapper {
    public ApproveAccountRespDto mapAccountToDto(Account account) {
        return new
                ApproveAccountRespDto(
                account.getId(),
                account.getCustomer().getFull_name(),
                account.getAccountNumber(),
                account.getType(),
                account.getAccountStatus(),
                account.getBranch().getBranch_name(),
                account.getBankEmployee().getFullName(),
                account.getApproved_date(),
                account.getOpened_date()
        );
    }

    public RejectAccountRespDto mapRejectDto(Account account) {
        return new
                RejectAccountRespDto(
                account.getId(),
                account.getCustomer().getFull_name(),
                account.getAccountNumber(),
                account.getAccountStatus(),
                account.getBankEmployee().getFullName(),
                account.getSetReason()
        );
    }

    public CloseAccountRespDto mapCloseDto(Account account) {
        return new
                CloseAccountRespDto(
                account.getId(),
                account.getCustomer().getFull_name(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getAccountStatus(),
                "Close request submitted"
        );
    }

    public AccountDetailsRespDto mapAccountDetailsDto(List<CustomerAccount> customerAccounts){

        Account account = customerAccounts.get(0).getAccount();
        List<String> jointHolders =
                customerAccounts.stream()
                        .map(ca -> ca.getCustomer().getFull_name())
                        .toList();

        OwnershipType ownershipType = customerAccounts.get(0).getOwnership_type();

        return new AccountDetailsRespDto(
                account.getId(),
                account.getAccountNumber(),
                account.getType(),
                account.getBalance(),
                account.getMinimum_balance(),
                account.getInterest_rate(),
                account.getAccountStatus(),
                account.getBranch().getBranch_name(),
                account.getBranch().getIfscCode(),
                account.getBankEmployee() != null
                        ? account.getBankEmployee().getFullName() : "Not Approved",
                jointHolders,
                ownershipType
        );
    }

    public DepositMoneyRespDto mapDepositDto(Account account, Transaction transaction) {
       return new DepositMoneyRespDto(
               account.getAccountNumber(),
               transaction.getAmount(),
               account.getBalance(),
               transaction.getStatus(),
               transaction.getTransaction_date()
       );
    }

    public WithdrawMoneyRespDto mapWithdrawDto(Account account,
                                               Transaction transaction){
        return new WithdrawMoneyRespDto(
                account.getId(),
                account.getAccountNumber(),
                transaction.getAmount(),
                account.getBalance(),
                transaction.getTransaction_type(),
                transaction.getStatus(),
                transaction.getTransaction_date(),
                transaction.getRemarks()
        );
    }

    public FreezeAccountResponseDto mapFrozenDto(Account account, AccountStatus oldStatus) {
        return new FreezeAccountResponseDto(
                account.getId(),
                account.getCustomer().getFull_name(),
                account.getAccountNumber(),
                oldStatus,
                account.getAccountStatus(),
                account.getAccountStatus() == AccountStatus.FROZEN
                        ? "Account frozen successfully"
                        : "Account activated successfully"
        );
    }

    public JointAccountRespDto mapJointAccountDto(CustomerAccount customerAccount) {
        return new JointAccountRespDto(
                customerAccount.getAccount().getId(),
                customerAccount.getAccount().getAccountNumber(),
                customerAccount.getCustomer().getFull_name(),
                customerAccount.getOwnership_type(),
                customerAccount.getRelation_type(),
                customerAccount.getAccess_level(),
                customerAccount.getAdded_by_employee().getFullName(),
                "Joint holder added successfully"
        );
    }

    public AssignedAccountRespDto mapAssignedAccountDto(
            Account account
    ) {

        return new AssignedAccountRespDto(

                account.getId(),

                account.getCustomer().getId(),

                account.getCustomer().getFull_name(),

                account.getCustomer().getUser().getEmail(),

                account.getCustomer().getUser().getPhone(),

                account.getAccountNumber(),

                account.getType(),

                account.getAccountStatus(),

                account.getCustomer().getKyc_status(),

                account.getBranch() == null
                        ? "N/A"
                        : account.getBranch().getBranch_name(),

                account.getDocumentpath() == null ? "NA" : account.getDocumentpath()
        );
    }
}
