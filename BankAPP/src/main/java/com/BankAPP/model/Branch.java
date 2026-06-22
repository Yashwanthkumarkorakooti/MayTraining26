package com.BankAPP.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String branch_name;

    @Column(name = "ifsc_code",unique = true,nullable = false)
    private String ifscCode;

    private String address;
    private String city;
    private String state;
    private String pincode;
    private String phone;
    private String email;

    @CreationTimestamp
    private Instant created_at;
}

