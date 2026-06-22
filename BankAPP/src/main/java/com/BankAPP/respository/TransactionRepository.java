package com.BankAPP.respository;

import com.BankAPP.enums.TransactionStatus;
import com.BankAPP.enums.TransactionType;
import com.BankAPP.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    @Query("""
        select count(t)>0
        from Transaction t
        where
        (
        t.from_account.id=?1
        or
        t.to_account.id=?1
        )
        and
        t.status='PENDING'
        """)
    boolean existsPendingTransaction(Integer accountId);

    @Query("""
        select t
        from Transaction t
        left join t.beneficiary b
        where
        t.created_by_customer.user.username = :username
        
        and (
            :search is null
            or lower(t.transaction_reference)
            like lower(concat('%', :search, '%'))
        )
        
        and (
        :type is null
        or t.transaction_type = :type
        )
        
        and (
        :status is null
        or t.status = :status
        )
        
        order by t.transaction_date desc

""")
    Page<Transaction> getTransactions(
            @Param("username") String username,
            @Param("search") String search,
            @Param("type") TransactionType type,
            @Param("status") TransactionStatus status,
            Pageable pageable
    );

    @Query("""
        select sum(t.amount)
        from Transaction t
        where
        t.created_by_customer.id=?1
        and
        t.transaction_type='WITHDRAWAL'
        """)
    Double calculateMonthlySpending(int customerId);

    @Query("""
        select t
        from Transaction t
        where
        (
        t.from_account.id=?1
        or
        t.to_account.id=?1
        )
        and
        t.transaction_date
        between ?2 and ?3
        order by
        t.transaction_date desc
        """)
    List<Transaction> getStatementTransactions(int accountId,
            Instant startDate,
            Instant endDate
    );

    @Query("""
            select sum(t.amount) from Transaction t
            where t.created_by_customer.id = ?1
            and t.transaction_type = 'WITHDRAWAL'
            and month(t.transaction_date) = month(current_date)
            and year(t.transaction_date) = year(current_date)
""")
    Double getMonthlySpending(int id);

    @Query("""
        select sum(t.amount) from Transaction t
        where t.created_by_customer.id = ?1
        and t.transaction_type = 'DEPOSIT'
""")
    Double getTotalDeposits(int id);

    @Query("""
            select sum(t.amount) from Transaction t
            where t.created_by_customer.id = ?1
            and t.transaction_type = 'WITHDRAWAL'
    """)
    Double getTotalWithdrawals(int id);


    @Query("""
        select max(t.transaction_date)
        from Transaction t
        where
        t.beneficiary.id=?1
        """)
    Instant getLastTransactionDate(int id);

    @Query("""
        select sum(t.amount)
        from Transaction t
        where t.created_by_customer.id=?1 and
        t.transaction_type='DEPOSIT'
        """)
    Double getInboundCashFlow(Integer customerId);

    @Query("""
        select sum(t.amount)
        from Transaction t
        where t.created_by_customer.id=?1 and
        (t.transaction_type='WITHDRAWAL' or t.transaction_type='TRANSFER' )
        """)
    Double getOutboundCashFlow(Integer customerId);

    @Query("""
        select avg(t.amount)
        from Transaction t
        where
        t.created_by_customer.id=?1
        and
        t.transaction_type='DEPOSIT'
        """)
    Double getAverageDeposits(int id);


    @Query("""
        select avg(t.amount)
        from Transaction t
        where
        t.created_by_customer.id=?1
        and
        t.transaction_type='WITHDRAWAL'
        """)
    Double getAverageWithdrawals(int id);

    @Query("""
        select sum(t.amount)
        from Transaction t
        where
        t.transaction_type='DEPOSIT'
        and
        t.to_account.branch.id=?1
        """)
    Double getTotalDepositsForReports(int branchId);

    @Query("""
        select sum(t.amount)
        from Transaction t
        where
        t.transaction_type='WITHDRAWAL'
        and
        t.from_account.branch.id=?1
        """)
    Double getTotalWithdrawalsForReports(int branchId);

    @Query("""
        select
        month(t.transaction_date),
        sum(t.amount)
        from Transaction t
        where
        t.created_by_customer.id=?1
        and
        t.transaction_type='WITHDRAWAL'
        group by
        month(t.transaction_date)
        order by
        month(t.transaction_date)
        """)
    List<Object[]> getMonthlySpendings(int customerId);

    @Query("""
        select
        t.transaction_type,
        count(t)
        from Transaction t
        where
        t.created_by_customer.id=?1
        group by
        t.transaction_type
        """)
    List<Object[]> getTransactionTypeAnalytics(int customerId);

    @Query("""
        select sum(t.amount)
        from Transaction t
        where
        t.transaction_type='DEPOSIT'
        """)
    Double getTotalDeposit();

    @Query("""
        select sum(t.amount)
        from Transaction t
        where
        t.transaction_type='WITHDRAWAL'
        """)
    Double getTotalWithdrawal();

    @Query("""
        select sum(t.amount)
        from Transaction t
        where

        t.transaction_type='TRANSFER'
        """)
    Double getTotalTransfers();

    @Query("""
        select
        month(t.transaction_date),
        sum(
            case
            when t.transaction_type='DEPOSIT'
            then t.amount
            else 0
            end
        ),
        sum(
            case
            when t.transaction_type='WITHDRAWAL'
            then t.amount
            else 0
            end
        ),
        sum(
            case
            when t.transaction_type='TRANSFER'
            then t.amount
            else 0
            end
        )
        from Transaction t
        group by
        month(t.transaction_date)
        order by
        month(t.transaction_date)
        """)

    List<Object[]> getTransactionAnalytics();


    @Query("""
        select count(t)
        from Transaction t
        where t.created_by_customer.id = ?1
        """)
    Long getTransactionCount(int id);

    @Query("""
        select avg(t.amount)
        from Transaction t
        where
        t.created_by_customer.id=?1
        and
        t.transaction_type='DEPOSIT'
        """)
    Double getAvgDeposit(Integer customerId);

    @Query("""
        select avg(t.amount)
        from Transaction t
        where
        t.created_by_customer.id=?1
        and
        t.transaction_type='WITHDRAWAL'
        """)
    Double getAvgWithdrawal(Integer customerId);
}
