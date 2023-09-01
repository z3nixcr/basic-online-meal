package com.zenedict.onlinemeal.entity;

import java.io.Serializable;

public class Customer implements Serializable {
    private int customerId;
    private String name;
    private String email;
    private String password;
    private String address;

    /* Constructors */
    public Customer() {
    }
    // to perform delete function
    public Customer(int customerId) {
        this.customerId = customerId;
    }
    // for user login validation
    public Customer(String email, String password) {
        this.email = email;
        this.password = password;
    }
    // for new user commit
    public Customer(String name, String email, String password, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }
    // for retrieving certain user information
    public Customer(int customerId, String name, String email, String password, String address) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    /* Getters & Setters */
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
