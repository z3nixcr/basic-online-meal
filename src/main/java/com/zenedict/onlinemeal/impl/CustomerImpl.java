package com.zenedict.onlinemeal.impl;

import com.zenedict.onlinemeal.dao.CustomerDAO;
import com.zenedict.onlinemeal.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.zenedict.onlinemeal.utils.MySQLConn.close;
import static com.zenedict.onlinemeal.utils.MySQLConn.getConnection;

public class CustomerImpl implements CustomerDAO {
    // For transaction connection
    private Connection transactionConn;
    // CRUD queries
    private static final String SELECT = "SELECT * FROM customers";
    private static final String INSERT = "INSERT INTO customers(name_cust, email, password, address) " +
            "VALUES(?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE customers SET name_cust = ?, email = ?, password = ?, " +
            "address = ? WHERE id_cust = ?";
    private static final String DELETE = "DELETE FROM customers WHERE id_cust = ?";

    public CustomerImpl() {
    }

    public CustomerImpl(Connection transactionConn) {
        this.transactionConn = transactionConn;
    }

    @Override
    public List<Customer> findAll() throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Customer> customers = new ArrayList<>();

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(SELECT);
            rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_cust");
                String name = rs.getString("name_cust");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String address = rs.getString("address");
                customers.add(new Customer(id, name, email, password, address));
            }
        } finally {
            assert rs != null;
            close(rs);
            close(pstm);
            if (this.transactionConn == null) {
                close(conn);
            }
        }
        return customers;
    }

    @Override
    public Customer findOne(Integer id) throws SQLException {
        Customer customer = null;
        List<Customer> customers = findAll();
        for (Customer cust : customers) {
            if (cust.getCustomerId() == id) {
                customer = cust;
                break;
            }
        }
        return customer;
    }

    @Override
    public Customer findOne(String email) throws SQLException {
        Customer customer = null;
        List<Customer> customers = findAll();
        for (Customer cust : customers) {
            if (cust.getEmail().equals(email)) {
                customer = cust;
                break;
            }
        }
        return customer;
    }

    @Override
    public int create(Customer customer) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        int row = 0;

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(INSERT);
            pstm.setString(1, customer.getName());
            pstm.setString(2, customer.getEmail());
            pstm.setString(3, customer.getPassword());
            pstm.setString(4, customer.getAddress());
            row = pstm.executeUpdate();

        } finally {
            assert pstm != null;
            close(pstm);
            if (this.transactionConn == null) {
                close(conn);
            }
        }
        return row;
    }

    @Override
    public int update(Customer customer) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        int row = 0;

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(UPDATE);
            pstm.setString(1, customer.getName());
            pstm.setString(2, customer.getEmail());
            pstm.setString(3, customer.getPassword());
            pstm.setString(4, customer.getAddress());
            pstm.setInt(5, customer.getCustomerId());
            row = pstm.executeUpdate();

        } finally {
            assert pstm != null;
            close(pstm);
            if (this.transactionConn == null) {
                close(conn);
            }
        }
        return row;
    }

    @Override
    public int delete(Customer customer) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        int row = 0;

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(DELETE);
            pstm.setInt(1, customer.getCustomerId());
            row = pstm.executeUpdate();

        } finally {
            assert pstm != null;
            close(pstm);
            if (this.transactionConn == null) {
                close(conn);
            }
        }
        return row;
    }

    public Connection makeConnection(Connection connection) {
        try {
            return connection != null ? connection : getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
