package com.cnpm.workingspace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @JsonIgnore
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "citizen_id", nullable = false)
    private String citizenId;

    @Column(name = "birthday", nullable = false ,columnDefinition = "DATE")
    private LocalDate birthday;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    public Customer() {
    }

    public Customer(String email, String customerName, String phoneNumber) {
        this.email = email;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String email, String customerName, String citizenId, LocalDate birthday, String nationality, String phoneNumber) {
        this.email = email;
        this.customerName = customerName;
        this.citizenId = citizenId;
        this.birthday = birthday;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", account=" + account +
                ", email='" + email + '\'' +
                ", customerName='" + customerName + '\'' +
                ", citizenId='" + citizenId + '\'' +
                ", birthday=" + birthday +
                ", nationality='" + nationality + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
