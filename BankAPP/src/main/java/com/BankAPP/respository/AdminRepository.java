package com.BankAPP.respository;

import com.BankAPP.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    @Query("""
            select a from Admin a
            where a.user.username = ?1
""")
    Admin findByUsername(String username);
}
