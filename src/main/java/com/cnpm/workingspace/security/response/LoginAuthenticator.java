package com.cnpm.workingspace.security.response;

import com.cnpm.workingspace.model.Customer;

public class LoginAuthenticator {
    private String jwtToken;
    private String refreshToken;
    private Customer customer;

    public LoginAuthenticator(String jwtToken, Customer customer) {
        this.jwtToken = jwtToken;
        this.customer = customer;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
