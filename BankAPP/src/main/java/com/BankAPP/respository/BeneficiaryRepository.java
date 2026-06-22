package com.BankAPP.respository;

import com.BankAPP.model.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary,Integer> {

    boolean existsByCustomerIdAndAccountNumber(int customerId, String s);

    boolean existsByCustomerIdAndNickname(int customerId, String nickname);

    @Query("""
            select b from Beneficiary b
            join b.customer
            where
            b.customer.id =?1
            """)
    List<Beneficiary> getBeneficiaries(int customerId);
}
