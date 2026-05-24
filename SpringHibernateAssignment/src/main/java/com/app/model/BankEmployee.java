package com.app.model;

import com.app.enums.Designation;
import com.app.enums.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Date;

@Entity
public class BankEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true,nullable = false)
    private String employee_code;

    @Column(nullable = false)
    private String full_name;

    @Enumerated(EnumType.STRING)
    private Designation designation;

    private Double salary;

    private Date hire_date;

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

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getHire_date() {
        return hire_date;
    }

    public void setHire_date(Date hire_date) {
        this.hire_date = hire_date;
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
        return "BankEmployee{" +
                "id=" + id +
                ", employee_code='" + employee_code + '\'' +
                ", full_name='" + full_name + '\'' +
                ", designation=" + designation +
                ", salary=" + salary +
                ", hire_date=" + hire_date +
                ", status=" + status +
                ", created_at=" + created_at +
                ", users=" + users +
                ", branch=" + branch +
                '}';
    }
}
