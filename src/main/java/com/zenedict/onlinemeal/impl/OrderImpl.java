package com.zenedict.onlinemeal.impl;

import com.zenedict.onlinemeal.dao.OrderDAO;
import com.zenedict.onlinemeal.entity.Customer;
import com.zenedict.onlinemeal.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.zenedict.onlinemeal.utils.MySQLConn.close;
import static com.zenedict.onlinemeal.utils.MySQLConn.getConnection;

public class OrderImpl implements OrderDAO {
    // For transaction connection
    private Connection transactionConn;

    private static final String SELECT1 = "SELECT * FROM orders o " +
            "JOIN meals m ON o.id_meal = m.id_meal " +
            "JOIN customers c ON o.id_cust = c.id_cust";
    private static final String SELECT2 = "SELECT * FROM orders o " +
            "JOIN meals m ON o.id_meal = m.id_meal " +
            "JOIN customers c ON o.id_cust = c.id_cust " +
            "WHERE o.id_cust = ?";
    private static final String INSERT  = "INSERT INTO orders(id_meal, id_cust, quantity, date_order) VALUES(?, ?, ?, NOW())";
    private static final String UPDATE  = "UPDATE orders SET quantity = ? WHERE id_order = ?";
    private static final String DELETE  = "DELETE FROM orders WHERE id_order = ?";

    public OrderImpl() {
    }

    public OrderImpl(Connection transactionConn) {
        this.transactionConn = transactionConn;
    }

    @Override
    public List<Order> findAll() throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Order> orderList = new ArrayList<>();
        Order order;

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(SELECT1);
            rs = pstm.executeQuery();

            getOrders(rs, orderList);

        } finally {
            assert rs != null;
            close(rs);
            close(pstm);
            if (this.transactionConn == null) {
                close(conn);
            }
        }
        return orderList;
    }

    @Override
    public Order findOne(Integer id) throws SQLException {
        List<Order> orderList = findAll();
        Order order = null;

        for (Order o : orderList) {
            if (o.getOrderId() == id) {
                order = o;
                break;
            }
        }
        return order;
    }

    @Override
    public List<Order> findAll(Customer customer) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Order> orderList = new ArrayList<>();
        Order order;

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(SELECT2);
            pstm.setInt(1, customer.getCustomerId());
            rs = pstm.executeQuery();

            getOrders(rs, orderList);

        } finally {
            assert rs != null;
            close(rs);
            close(pstm);
            if (this.transactionConn == null) {
                close(conn);
            }
        }
        return orderList;
    }

    @Override
    public int insertOrder(Order order) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        int row = 0;

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(INSERT);
            pstm.setInt(1, order.getMealId());
            pstm.setInt(2, order.getCustomerId());
            pstm.setInt(3, order.getQuantity());
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

    private void getOrders(ResultSet rs, List<Order> orderList) throws SQLException {
        Order order;
        while (rs.next()) {
            int id = rs.getInt("id_order");
            int idMeal = rs.getInt("id_meal");
            String name = rs.getString("name_cust");
            String address = rs.getString("address");
            String meal = rs.getString("name_meal");
            float price = rs.getFloat("price");
            int quantity = rs.getInt("quantity");
            float total = price * quantity;
            Date date = rs.getDate("date_order");

            order = new Order(id, idMeal, name, address, meal, price, quantity, total, date);
            orderList.add(order);
        }
    }

    @Override
    public int updateById(Integer id, Integer quantity) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        int row = 0;

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(UPDATE);
            pstm.setInt(1, quantity);
            pstm.setInt(2, id);
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
    public int deleteById(Integer id) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        int row = 0;

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(DELETE);
            pstm.setInt(1, id);
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
