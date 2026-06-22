package com.BankAPP.mapper;

import com.BankAPP.dto.*;
import com.BankAPP.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanMapper {
    public ApplyLoanRespDto mapLoanToDto(Loan loan) {
        return new ApplyLoanRespDto(
                loan.getId(),
                loan.getCustomer().getFull_name(),
                loan.getLoan_type(),
                loan.getLoan_amount(),
                loan.getLoan_term_months(),
                loan.getLoan_status(),
                "Loan application submitted",
                loan.getApplication_date()

        );
    }

    public ApproveLoanRespDto mapApproveLoanDto(Loan loan) {
        return new
                ApproveLoanRespDto(
                loan.getId(),
                loan.getCustomer().getFull_name(),
                loan.getLoan_type(),
                loan.getLoan_amount(),
                loan.getInterest_rate(),
                loan.getLoan_term_months(),
                loan.getEmi_amount(),
                loan.getRemaining_balance(),
                loan.getLoan_status(),
                loan.getReviewed_by_employee().getFullName(),
                loan.getApproval_date()
        );
    }

    public RejectLoanRespDto mapRejectLoanDto(Loan loan) {
        return new
                RejectLoanRespDto(
                loan.getId(),
                loan.getCustomer().getFull_name(),
                loan.getLoan_type(),
                loan.getLoan_amount(),
                loan.getLoan_status(),
                loan.getRejection_reason(),
                loan.getReviewed_by_employee().getFullName()
        );
    }

    public LoanDisburseRespDto mapLoanDisburseDto(Loan loan, Account account, Transaction transaction) {
        return new LoanDisburseRespDto(
                loan.getId(),
                loan.getCustomer().getFull_name(),
                account.getAccountNumber(),
                loan.getLoan_type(),
                loan.getLoan_status(),
                loan.getLoan_amount(),
                account.getBalance(),
                transaction.getTransaction_reference(),
                loan.getLoan_status().name(),
                loan.getDisbursement_date(),
                "Loan disbursed successfully"
        );
    }

    public GetCustomerLoanRespDto mapCustomerLoanDto(Loan loan) {
        return new GetCustomerLoanRespDto(
                loan.getId(),
                loan.getLoan_type().name(),
                loan.getLoan_amount(),
                loan.getRemaining_balance(),
                loan.getEmi_amount(),
                loan.getReviewed_by_employee() != null ?
                        loan.getReviewed_by_employee().getFullName() : null,
                loan.getLoan_status().name()
        );
    }

    public LoanDetailsRespDto mapLoanDetailsDto(Loan loan, List<LoanRepayment> repayments) {
        List<LoanRepaymentDto> repaymentDtos = repayments
                                                    .stream()
                                                    .map(r ->
                                     new LoanRepaymentDto(
                                        r.getId(),
                                        r.getAmount_paid(),
                                        r.getRemaining_balance(),
                                        r.getPayment_method().name(),
                                        r.getPayment_status().name(),
                                        r.getPayment_date()
                                )).toList();

        return new LoanDetailsRespDto(
                loan.getId(),
                loan.getCustomer().getFull_name(),
                loan.getLoan_type().name(),
                loan.getLoan_amount(),
                loan.getInterest_rate(),
                loan.getLoan_term_months(),
                loan.getEmi_amount(),
                loan.getRemaining_balance(),
                loan.getLoan_status().name(),
                loan.getReviewed_by_employee() != null ?
                        loan.getReviewed_by_employee().getFullName() : null,
                loan.getApplication_date(),
                loan.getApproval_date(),
                loan.getDisbursement_date(),
                repaymentDtos
        );


    }

    public PayEmiRespDto mapPayEmiDto(Loan loan, Account account, Transaction transaction, LoanRepayment repayment) {
        return new PayEmiRespDto(
                loan.getId(),
                loan.getCustomer().getFull_name(),
                repayment.getAmount_paid(),
                loan.getRemaining_balance(),
                account.getBalance(),
                transaction.getTransaction_reference(),
                repayment.getPayment_status().name(),
                repayment.getPayment_date(),
                "EMI paid successfully"
        );
    }

    public RepaymentHistoryRespDto mapRepaymentHistoryDto(LoanRepayment repayment) {
        return new
                RepaymentHistoryRespDto(
                repayment.getId(),
                repayment.getAmount_paid(),
                repayment.getPayment_method().name(),
                repayment.getPayment_date(),
                repayment.getPayment_status().name(),
                repayment.getRemaining_balance()
        );
    }

    public LoanEligibilityRespDto mapLoanEligibilityDto(Double averageBalance, Double inboundCashFlow,
                                  Double outboundCashFlow, Double emiBurden, Double remainingLoanBalance,
                                   Double netDisposableIncome, Double eligibleAmount, int tenure, String riskScore) {

        return new LoanEligibilityRespDto(
                averageBalance,
                inboundCashFlow,
                outboundCashFlow,
                emiBurden,
                remainingLoanBalance,
                netDisposableIncome,
                eligibleAmount,
                tenure,
                riskScore
        );
    }

    public EmployeeLoanReviewRespDto mapReviewDto(Loan loan, Customer customer, Account account,
                           Double avgDeposit, Double avgWithdrawal, int existingLoans, String risk, int score) {
        return new EmployeeLoanReviewRespDto(
                loan.getLoan_amount(),
                customer.getFull_name(),
                account.getAccountNumber(),
                avgDeposit,
                avgWithdrawal,
                existingLoans,
                risk,
                score
        );
    }

    public LoanPendingRespDto mapToDto(Loan loan) {
        return new LoanPendingRespDto(
                loan.getId(),
                loan.getCustomer().getFull_name(),
                loan.getLoan_type(),
                loan.getLoan_amount(),
                loan.getCustomer().getBranch().getBranch_name()
        );
    }
}
