package com.BankAPP.respository;

import com.BankAPP.model.Account;
import com.BankAPP.model.CustomerAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account,Integer> {
    @Query("""
        select distinct ca
        from CustomerAccount ca
        join ca.account a
        join a.customer c
        join c.user u
        join a.branch b
        join a.bankEmployee e
        join ca.customer cu
        where a.id=?1
        """)
    List<CustomerAccount> getAccountDetails(int accountId);

    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("""
        select avg(a.balance)
        from Account a
        where a.customer.id=?1
        """)
    Double calculateAverageBalance(int customerId);

    @Query("""
        select count(a)
        from Account a
        where
        a.bankEmployee.id=?1
        """)
    Long countApprovedAccounts(int employeeId);

    @Query("""
        select a from Account a
        where a.customer.id = ?1
""")
    Account findByCustomerId(int id);

    @Query("""
        select a
        from Account a
        where a.customer.id = ?1
        """)
    List<Account> findByCustomersId(int customerId);

    @Query("""
            select sum(a.balance) from Account a
            where a.customer.id = ?1
""")
    Double getTotalBalance(int id);

    @Query("""
        select count(a)>0 from Account a
        where a.customer.id = ?1
        and a.accountNumber = ?2
""")
    boolean existsOwnAccount(int id, String s);

    @Query("""
            select avg(a.balance) from Account a
            where a.customer.id = ?1
""")
    Double getAverageBalance(int customerId);

    @Query("""
        select a
        from Account a
        where
        a.customer.id=?1
        """)
    List<Account> findAccountByCustomerId(int id);

    @Query("""
        select
        coalesce(a.type,'UNKNOWN'),
        count(a)
        from Account a
        where
        a.customer.id=?1
        group by
        a.type
        """)
    List<Object[]> getAccountDistribution(int customerId);

    @Query("""
        select a
        from Account a
        where
        a.accountStatus='PENDING'
        """)
    List<Account> getPendingAccounts();

    @Query("""
        select coalesce(sum(a.balance),0)
        from Account a
        where a.customer.id = ?1
        """)
    Double getCustomerTotalBalance(int id);

    @Query("""
        select count(a)
        from Account a
        where
        a.customer.id=?1
        and
        a.accountStatus='ACTIVE'
        """)
    Integer getActiveAccounts(int id);

    @Query("""
        select sum(a.balance)
        from Account a
        where
        a.customer.id=?1
        """)
    Double getCustomerBalance(int id);

    @Query("""
       select a
       from Account a
       where
       a.assigned_employee.id = ?1

       and
       (?2 is null or
       lower(a.customer.full_name)
       like lower(concat('%', ?2, '%')))

       and
       (?3 is null or
       a.customer.user.phone
       like concat('%', ?3, '%'))

       and
       (?4 is null or
       cast(a.type as string) = ?4)

       and
       (?5 is null or
       cast(a.accountStatus as string) = ?5)

       order by a.id desc
       """)
    Page<Account> getAssignedAccounts(
            Integer employeeId,
            String customerName,
            String phone,
            String accountType,
            String status,
            Pageable pageable
    );

    @Query("""
        select a from Account a
        where a.customer.id = ?1
""")
    List<Account> findByCustomer(int id);

    @Query("""
        select a
        from Account a
        where a.customer.id = ?1
        and a.accountStatus = 'ACTIVE'
""")
    List<Account> findAccountsByCustomerId(int customerId);

    @Query("""
select a from Account  a
where a.assigned_employee.id = ?1
""")
    Account getAccounts(int id);
}
