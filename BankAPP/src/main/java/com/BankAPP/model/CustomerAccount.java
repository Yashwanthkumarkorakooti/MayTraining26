package com.BankAPP.model;


import com.BankAPP.enums.AccessLevel;
import com.BankAPP.enums.OwnershipType;
import com.BankAPP.enums.RelationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class CustomerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Account account;

    @ManyToOne
    private BankEmployee added_by_employee;

    @Enumerated(EnumType.STRING)
    private OwnershipType ownership_type;

    @Enumerated(EnumType.STRING)
    private RelationType relation_type;

    @Enumerated(EnumType.STRING)
    private AccessLevel access_level;

    private Boolean is_active;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant joined_date;

    @UpdateTimestamp
    private Instant removed_date;

    private String remarks;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant created_at;

    @UpdateTimestamp
    private Instant updated_at;

}

