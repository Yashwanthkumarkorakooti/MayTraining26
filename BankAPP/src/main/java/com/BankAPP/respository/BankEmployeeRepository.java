package com.BankAPP.respository;

import com.BankAPP.enums.EmployeeDesignation;
import com.BankAPP.enums.Status;
import com.BankAPP.model.BankEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BankEmployeeRepository extends JpaRepository<BankEmployee,Integer> {


    @Query("""
            select b
            from BankEmployee b
            where b.user.username=?1
            """)
    BankEmployee findByUsername(String username);

    @Query("""

            select b
            from BankEmployee b
            join b.user
            join b.branch
            where b.id=?1
        """)
    Optional<BankEmployee> findEmployeeDetails(int employeeId);

    @Query("""
        select b
        from BankEmployee b
        join b.user u
        join b.branch br
        where(?1 is null or b.designation=?1)
        and
        (?2 is null or br.id=?2)
        and
        (?3 is null or b.status=?3)
        and
        (?4 is null or b.salary>=?4)
        and
        (?5 is null or b.salary<=?5 )
""")
    Page<BankEmployee> getAllEmployees(EmployeeDesignation designation,
                                       Integer branchId, Status status,
                                       Double minSalary, Double maxSalary,
                                       Pageable pageable);



}