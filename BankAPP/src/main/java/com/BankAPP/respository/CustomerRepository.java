package com.BankAPP.respository;

import com.BankAPP.enums.KycStatus;
import com.BankAPP.enums.Status;
import com.BankAPP.model.Customer;
import com.BankAPP.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    boolean existsByAadhaarNumber(String s);

    boolean existsByPanNumber(String s);

    @Query("""
        select c
        from Customer c
        join c.user u
        join c.branch b
        where c.id=?1
        """)
    Optional<Customer> getCustomerProfile(int customerId);

    @Query("""
        select c
        from Customer c
        join c.user u
        join c.branch b
        where
        (:name is null
        or lower(c.full_name)
        like lower(
        concat('%',:name,'%')
        ))
        and
        (:phone is null
        or c.phone=:phone)
        and
        (:aadhaar is null
        or c.aadhaarNumber=
        :aadhaar)
        and
        (:pan is null
        or c.panNumber=:pan)
        and
        (:branchId is null
        or b.id=:branchId)
        and
        (:status is null
        or c.customer_status=
        :status)
        and
        (:kycStatus is null
        or c.kyc_status=
        :kycStatus)
        """)
    Page<Customer> searchCustomers(String name, String phone, String aadhaar,
                                   String pan, Integer branchId, Status status,
                                   KycStatus kycStatus, Pageable pageable);


    @Query("""
        select count(c)
        from Customer c
        where
        c.createdByEmployee.id=?1
        """)
    Long countByCreatedByEmployeeId(int employeeId);

    @Query("""
           select c from Customer c
           where c.user.username = ?1
""")
    Customer findByUserName(String userName);


    String user(User user);

    @Query("""
        select c
        from Customer c
        where
        c.assigned_employee.id=?1
        """)
    List<Customer> getAssignedCustomers(int id);

@Query("""
        select c
        from Customer c
        join c.assigned_employee e
        where e.id = ?1
        and
        (?2 is null or
         lower(c.full_name)
         like lower(concat('%', ?2, '%')))
        and
        (?3 is null or
         c.phone = ?3)
        and
        (?4 is null or
         c.aadhaarNumber = ?4)
        and
        (?5 is null or
         c.panNumber = ?5)
        """)
Page<Customer> getAssignedCustomer(
        Integer employeeId,
        String name,
        String phone,
        String aadhaar,
        String pan,
        Pageable pageable
);
}
