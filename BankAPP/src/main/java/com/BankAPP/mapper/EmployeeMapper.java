package com.BankAPP.mapper;

import com.BankAPP.dto.*;
import com.BankAPP.model.Account;
import com.BankAPP.model.BankEmployee;
import com.BankAPP.model.Customer;
import com.BankAPP.model.Loan;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeMapper {
    public CreateEmployeeRespDto mapEmployeeDto(BankEmployee employee) {
        return new CreateEmployeeRespDto(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFullName(),
                employee.getUser().getUsername(),
                employee.getUser().getEmail(),
                employee.getBranch().getBranch_name(),
                employee.getDesignation(),
                employee.getUser().getRole(),
                employee.getStatus()
        );
    }

    public EmployeeDetailsRespDto mapEmployeeDetailsDto(BankEmployee employee, Long customersHandled, Long accountsApproved, Long loansReviewed) {
        return new EmployeeDetailsRespDto(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFullName(),
                employee.getUser().getEmail(),
                employee.getUser().getPhone(),
                employee.getBranch().getBranch_name(),
                employee.getDesignation(),
                employee.getStatus(),
                customersHandled,
                accountsApproved,
                loansReviewed
        );
    }

    public TransferEmployeeRespDto mapTransferEmployeeDto(BankEmployee employee, String oldBranch) {
        return new TransferEmployeeRespDto(
                employee.getId(),
                employee.getFullName(),
                employee.getEmployeeCode(),
                oldBranch,
                employee.getBranch().getBranch_name(),
                "Employee transferred successfully"
        );
    }

    public EmployeeRespDto mapEmployeeRespDto(BankEmployee employee){
        return new EmployeeRespDto(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFullName(),
                employee.getUser().getEmail(),
                employee.getUser().getPhone(),
                employee.getBranch().getBranch_name(),
                employee.getDesignation(),
                employee.getSalary(),
                employee.getStatus(),
                employee.getHireDate()
        );
    }

    public EmployeeCustomerRespDto mapEmployeeCustomerDto(Customer customer, Integer accounts,
                                                          Integer loans, Double balance, String riskLevel) {
        return new EmployeeCustomerRespDto(
                customer.getId(),
                customer.getFull_name(),
                customer.getUser().getEmail(),
                customer.getUser().getPhone(),
                customer.getKyc_status(),
                accounts,
                loans,
                balance,
                riskLevel
        );
    }

    public AssignedLoanRespDto mapAssignedLoanDto(Loan loan, Double avgDeposit, Double avgWithdrawal, String riskLevel) {
        return new AssignedLoanRespDto(
                loan.getId(),
                loan.getCustomer().getFull_name(),
                loan.getLoan_type().toString(),
                loan.getLoan_amount(),
                loan.getEmi_amount(),
                loan.getRemaining_balance(),
                loan.getLoan_status().toString(),
                avgDeposit,
                avgWithdrawal,
                riskLevel
        );
    }

//    public AssignedAccountRespDto mapAssignedAccountDto(Account account) {
//        return new AssignedAccountRespDto(
//                account.getId(),
//                account.getCustomer().getFull_name(),
//                account.getCustomer().getUser() != null ?
//                        account.getCustomer().getUser().getEmail() : "Not Assigned",
//                account.getCustomer().getUser() != null ?
//                        account.getCustomer().getUser().getPhone() : "Not Assigned",
//                account.getAccountNumber(),
//                account.getType(),
//                account.getAccountStatus(),
//                account.getCustomer().getKyc_status(),
//                account.getBranch() != null
//                        ? account.getBranch().getBranch_name()
//                        : "Not Assigned"
//        );
//    }
}
