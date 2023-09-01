package com.zenedict.onlinemeal.dao;

import com.zenedict.onlinemeal.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    // Find all customers
    public List<Customer> findAll() throws SQLException;

    // Find a customer by given ID
    public Customer findOne(Integer id) throws SQLException;

    // Find a customer by given email
    public Customer findOne(String email) throws SQLException;

    // Crate a new customer
    public int create(Customer customer) throws SQLException;

    // Update customer information
    public int update(Customer customer) throws SQLException;

    // Delete a customer
    public int delete(Customer customer) throws SQLException;
}
