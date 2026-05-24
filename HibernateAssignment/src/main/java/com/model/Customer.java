package com.model;

import com.enums.Gender;
import com.enums.KycStatus;
import com.enums.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Time;
import java.time.Instant;
import java.util.Date;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 1000,nullable = false)
    private String full_name;

    private Date date;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    @Column(length = 1000)
    private String address;

    @Enumerated(EnumType.STRING)
    private KycStatus kyc_status;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private Instant created_at;

    @OneToOne
    private Users users;

    @ManyToOne
    private Branch branch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public KycStatus getKyc_status() {
        return kyc_status;
    }

    public void setKyc_status(KycStatus kyc_status) {
        this.kyc_status = kyc_status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", full_name='" + full_name + '\'' +
                ", date=" + date +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", kyc_status=" + kyc_status +
                ", status=" + status +
                ", created_at=" + created_at +
                ", users=" + users +
                ", branch=" + branch +
                '}';
    }
}
