package com.BankAPP.respository;

import com.BankAPP.model.LoanRepayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRepaymentRepository extends JpaRepository<LoanRepayment,Integer> {
    @Query("""
            select lr from LoanRepayment lr
            where lr.loan.id=?1
            order by
            lr.payment_date desc
    """)
    List<LoanRepayment> getLoanRepayments(int loanId);

    @Query("""
            select lr
            from LoanRepayment lr
            join lr.loan
            where lr.loan.id=?1
            order by lr.payment_date desc
            """)
    List<LoanRepayment> getRepaymentHistory(int loanId);

    @Query("""
        select sum(lr.amount_paid)
        from LoanRepayment lr
        where
        lr.loan.customer.branch.id=?1
        and
        lr.payment_status='SUCCESS'
        """)
    Double getEmiCollection(int branchId);

    @Query("""
        select
        month(lr.payment_date),
        sum(lr.amount_paid)
        from LoanRepayment lr
        group by
        month(lr.payment_date)
        order by
        month(lr.payment_date)
        """)

    List<Object[]> getEmiRevenue();
}
