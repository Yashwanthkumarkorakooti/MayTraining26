package com.BankAPP.model;


import com.BankAPP.enums.ReportStatus;
import com.BankAPP.enums.ReportType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Date;

@Entity
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Account account;

    @ManyToOne
    private BankEmployee generated_by_employee;

    @Enumerated(EnumType.STRING)
    private ReportType report_type;

    @CreationTimestamp
    private Instant start_date;

    @CreationTimestamp
    private Instant end_date;

    @CreationTimestamp
    private Instant generated_date;

    private String file_path;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;
}

