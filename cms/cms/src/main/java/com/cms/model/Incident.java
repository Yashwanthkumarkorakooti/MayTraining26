package com.cms.model;

import com.cms.enums.IncidentStatus;
import com.cms.enums.IncidentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Enumerated(EnumType.STRING)
    private IncidentType incidentType;

    private String progressDetails;

    @Enumerated(EnumType.STRING)
    private IncidentStatus incidentStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
