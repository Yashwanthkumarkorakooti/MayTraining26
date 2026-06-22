package com.BankAPP.model;


import com.BankAPP.enums.TransactionStatus;
import com.BankAPP.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,nullable = false)
    private String transaction_reference;

    @Enumerated(EnumType.STRING)
    private TransactionType transaction_type;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne
    private Account from_account;

    @ManyToOne
    private Account to_account;

    @ManyToOne
    private Beneficiary beneficiary;

    @ManyToOne
    private Customer created_by_customer;

    private String remarks;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant transaction_date;


}

