package com.BankAPP.model;


import com.BankAPP.enums.PaymentMethod;
import com.BankAPP.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class LoanRepayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Loan loan;

    @Column(nullable = false)
    private Double amount_paid;

    private Double remaining_balance;

    @CreationTimestamp
    private Instant payment_date;

    @Enumerated(EnumType.STRING)
    private PaymentMethod payment_method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus payment_status;


}
