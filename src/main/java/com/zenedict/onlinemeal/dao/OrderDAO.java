package com.zenedict.onlinemeal.dao;

import com.zenedict.onlinemeal.entity.Customer;
import com.zenedict.onlinemeal.entity.Order;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    // Find all orders
    public List<Order> findAll() throws SQLException;

    // Find an order by ID
    public Order findOne(Integer id) throws SQLException;

    // Find an orders by certain customer
    public List<Order> findAll(Customer customer) throws SQLException;

    // Place an order for a customer
    public int insertOrder(Order order) throws SQLException;

    // Update an order by ID
    public int updateById(Integer id, Integer quantity) throws SQLException;

    // Delete an order by ID
    public int deleteById(Integer id) throws SQLException;
}
