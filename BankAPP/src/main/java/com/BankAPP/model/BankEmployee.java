package com.BankAPP.model;

import com.BankAPP.enums.EmployeeDesignation;
import com.BankAPP.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class BankEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Branch branch;

    @Column(unique = true, name = "employee_code")
    private String employeeCode;

    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Enumerated(EnumType.STRING)
    private EmployeeDesignation designation;

    private Double salary;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant hireDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

}