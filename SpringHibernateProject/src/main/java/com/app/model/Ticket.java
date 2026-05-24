package com.app.model;

import com.app.enums.Priority;
import com.app.enums.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String subject;

    @Column(length = 1000)
    private String details;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private Instant createdAt;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Executive executive;

    public Ticket() {
    }

    public Ticket(String subject, String details, Priority priority) {
        this.subject = subject;
        this.details = details;
        this.priority = priority;
    }

    public Ticket(int id, String subject, String details, Priority priority, Status status, Instant createdAt, Customer customer, Executive executive) {
        this.id = id;
        this.subject = subject;
        this.details = details;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
        this.customer = customer;
        this.executive = executive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getCreateAt() {
        return createdAt;
    }

    public void setCreateAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Executive getExecutive() {
        return executive;
    }

    public void setExecutive(Executive executive) {
        this.executive = executive;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", details='" + details + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                ", createAt=" + createdAt +
                ", customer=" + customer +
                ", executive=" + executive +
                '}';
    }
}