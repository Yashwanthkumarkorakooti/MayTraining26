package com.BankAPP.respository;

import com.BankAPP.model.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CustomerAccountRepository extends JpaRepository<CustomerAccount,Integer> {
    @Query("""
            select ca from CustomerAccount ca
            join ca.account a
            join a.branch b
            join a.bankEmployee e
            where ca.customer.id = ?1
""")
    List<CustomerAccount> getCustomerAccounts(int customerId);

    @Query("""
            select count(ca)>0 from CustomerAccount ca
            where ca.account.id = ?1 and
            ca.customer.id = ?2
""")
    boolean existsJointAccountHolder(int accountId, int customerId);
}
