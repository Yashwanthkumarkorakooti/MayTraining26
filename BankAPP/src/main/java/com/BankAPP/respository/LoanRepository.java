package com.BankAPP.respository;

import com.BankAPP.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan,Integer> {
    @Query("""
        select count(l)>0
        from Loan l
        where
        l.customer.id=?1
        and
        l.loan_status
        in (
        APPROVED,
        DISBURSED
        )
        """)
    boolean existActiveLoan(int id);

    @Query("""
        select count(l)>0
        from Loan l
        where
        l.customer.id=?1
        and
        l.loan_status
        in (
        APPROVED,
        DISBURSED
        )
        """)
    boolean existsActiveLoan(int customerId);

    @Query("""
        select count(l)
        from Loan l
        where
        l.reviewed_by_employee.id=?1
        """)
    Long countReviewedLoans(int employeeId);

    @Query("""
            select sum(l.remaining_balance) from Loan l
            where l.customer.id = ?1
            and l.loan_status = 'APPROVED'
""")
    Double getOustandingLoan(int id);

    @Query("""
            select l from Loan l
            join l.reviewed_by_employee
            where
            l.customer.id=?1
            order by
            l.application_date
            desc
      """)
    List<Loan> getCustomerLoans(int customerId);

    @Query("""
            select distinct l
            from Loan l
            join l.customer
            join l.reviewed_by_employee
            where l.id=?1
            """)
    Optional<Loan> getLoanDetails(int loanId);

    @Query("""
        select sum(l.emi_amount)
        from Loan l
        where
        l.customer.id=?1
        and
        l.loan_status='DISBURSED'
        """)
    Double getEmiBurden(int customerId);

    @Query("""
        select sum(l.remaining_balance)
        from Loan l
        where
        l.customer.id=?1
        and
        l.loan_status='DISBURSED'
        """)
    Double getRemainingLoanBalance(int customerId);

    @Query("""
        select count(l)
        from Loan l
        where
        l.customer.id=?1
        """)
    Integer countByCustomerId(Integer id);

    @Query("""
        select sum(l.loan_amount)
        from Loan l
        where
        l.customer.branch.id=?1
        and
        l.loan_status='DISBURSED'
        """)
    Double getLoanPortfolio(int branchId);

    @Query("""
        select
        l.loan_status,
        count(l)
        from Loan l
        where
        l.reviewed_by_employee.id=?1
        group by
        l.loan_status
        """)
    List<Object[]> getLoanAnalytics(int employeeId);

    @Query("""
        select
        l.loan_type,
        count(l)
        from Loan l
        group by
        l.loan_type
        """)
    List<Object[]> getLoanPortfolios();

    @Query("""
        select
        month(l.disbursement_date),
        sum(l.interest_rate)
        from Loan l
        where
        l.disbursement_date is not null
        group by
        month(l.disbursement_date)
        order by
        month(l.disbursement_date)
        """)
    List<Object[]> getInterestRevenue();

    @Query("""
        select l
        from Loan l
        where
        l.loan_status='PENDING'
        order by
        l.application_date
        desc
        """)
    List<Loan> getPendingLoans();

    @Query("""
        select count(l)
        from Loan l
        where l.customer.id = ?1
        """)
    Long getLoanCount(int id);

    @Query("""
        select count(l)
        from Loan l
        where
        l.customer.id=?1
        and
        l.loan_status='DISBURSED'
        """)
    Integer getActiveLoans(int id);

    @Query("""
       select l
       from Loan l
       where
       l.assigned_employee.id = ?1

       and
       (?2 is null or
       lower(l.customer.full_name)
       like lower(concat('%', ?2, '%')))

       and
       (?3 is null or
       l.customer.user.phone
       like concat('%', ?3, '%'))

       and
       (?4 is null or
       cast(l.loan_type as string) = ?4)

       and
       (?5 is null or
       cast(l.loan_status as string) = ?5)

       order by l.application_date desc
       """)
    Page<Loan> getAssignedLoans(
            Integer employeeId,
            String customerName,
            String phone,
            String loanType,
            String status,
            Pageable pageable
    );


}
