package com.cnpm.workingspace.dto;

import com.cnpm.workingspace.model.Account;
import com.cnpm.workingspace.model.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class CustomerAccount {
    private String username;
    private String password;
    private String email;
    private String customerName;
    private String phoneNumber;
    private String citizenId;
    private String birthday;
    private String nationality;

    public Account getAccount() {
        return new Account(username, password);
    }

    public Customer getCustomer() {
        return new Customer(email, customerName, citizenId, LocalDate.parse(birthday), nationality, phoneNumber);
    }

    @Override
    public String toString() {
        return "CustomerAccount{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", citizenId='" + citizenId + '\'' +
                ", birthday=" + birthday +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
