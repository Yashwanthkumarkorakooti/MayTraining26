package com.BankAPP.model;

import com.BankAPP.enums.AccountStatus;
import com.BankAPP.enums.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_number",unique = true)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private Double balance;

    private Double minimum_balance;

    private float interest_rate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus accountStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant opened_date;

    private String setReason;

    @CreationTimestamp
    private Instant setRejectionDate;

    @UpdateTimestamp
    private Instant approved_date;

    @UpdateTimestamp
    private Instant closed_date;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Branch branch;


    @ManyToOne
    @JoinColumn(name = "approved_by_employee_id")
    private BankEmployee bankEmployee;

    @ManyToOne
    private BankEmployee assigned_employee;

    @Column
    private String documentpath;

}

