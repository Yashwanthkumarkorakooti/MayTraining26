package com.practice.repository;

import com.practice.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employer,Integer> {
    @Query("""
        select e from Employer e
        where e.user.username = ?1
""")
    Employer findByUsername(String employerName);
}
