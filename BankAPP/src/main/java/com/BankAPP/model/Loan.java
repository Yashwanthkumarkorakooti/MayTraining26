package com.BankAPP.model;


import com.BankAPP.enums.LoanStatus;
import com.BankAPP.enums.LoanType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private BankEmployee reviewed_by_employee;

    @ManyToOne
    private BankEmployee assigned_employee;

    @Enumerated(EnumType.STRING)
    private LoanType loan_type;

    
    private Double loan_amount;

    private float interest_rate;

    private int loan_term_months;

    private Double emi_amount;

    private Double remaining_balance;

    @Enumerated(EnumType.STRING)
    private LoanStatus loan_status;

    private String rejection_reason;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant application_date;

    @UpdateTimestamp
    private Instant approval_date;

    @CreationTimestamp
    private Instant disbursement_date;

}

