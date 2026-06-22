package com.BankAPP.model;


import com.BankAPP.enums.Gender;
import com.BankAPP.enums.KycStatus;
import com.BankAPP.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Date;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User user;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "created_by_employee_id")
    private BankEmployee createdByEmployee;

    @ManyToOne
    private BankEmployee assigned_employee;

    @ManyToOne
    private BankEmployee kyc_verified_by_employee;

    @Column(nullable = false)
    private String full_name;

    @CreationTimestamp
    private Instant dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true, name = "aadhaar_number")
    private String aadhaarNumber;

    @Column(unique = true,name = "pan_number")
    private String panNumber;

    private String phone;

    @Column(length = 1000)
    private String address;

    @Enumerated(EnumType.STRING)
    private KycStatus kyc_status;

    @Enumerated(EnumType.STRING)
    private Status customer_status;

    @CreationTimestamp
    private Instant created_at;

}

