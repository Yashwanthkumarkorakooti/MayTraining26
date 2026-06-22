package com.BankAPP.respository;

import com.BankAPP.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch,Integer> {

    Branch findByIfscCode(String s);

    boolean existsByIfscCode(String s);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query("""
            select count(be)
            from BankEmployee be
            where
            be.branch.id=?1
            """)
    int getEmployeesCount(int branchId);

    @Query("""
            select count(c)
            from Customer c
            where
            c.branch.id=?1
            """)
    int getCustomersCount(int branchId);

    @Query("""
            select count(a)
            from Account a
            where
            a.branch.id=?1
            """)
    int getAccountsCount(int branchId);

    @Query("""
            select sum(l.loan_amount)
            from Loan l
            where
            l.customer.branch.id=?1
            """)
    Double getLoanPortfolio(int branchId);

    @Query("""
        select
        b.branch_name,
        sum(t.amount),
        count(distinct c.id),
        count(distinct a.id),
        count(distinct l.id)
        from Branch b
        left join Customer c
        on c.branch.id=b.id
        left join Account a
        on a.branch.id=b.id
        left join Loan l
        on l.customer.branch.id=b.id
        left join Transaction t
        on t.created_by_customer.branch.id=b.id
        group by
        b.branch_name
        """)
    List<Object[]> getBranchPerformance();
}
