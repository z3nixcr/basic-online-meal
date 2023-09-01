package com.zenedict.onlinemeal.impl;

import com.zenedict.onlinemeal.dao.FeedbackDAO;
import com.zenedict.onlinemeal.entity.Customer;
import com.zenedict.onlinemeal.entity.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.zenedict.onlinemeal.utils.MySQLConn.close;
import static com.zenedict.onlinemeal.utils.MySQLConn.getConnection;

public class FeedbackImpl implements FeedbackDAO {
    // For transaction connection
    private Connection transactionConn;

    private static final String SELECT1 = "SELECT * FROM feedbacks f " +
            "JOIN orders o ON f.id_order = f.id_order " +
            "JOIN meals m ON m.id_meal = o.id_meal " +
            "JOIN customers c on c.id_cust = o.id_cust";
    private static final String SELECT2 = "SELECT * FROM feedbacks f " +
            "JOIN orders o ON o.id_order = f.id_order " +
            "JOIN meals m ON m.id_meal = o.id_meal " +
            "JOIN customers c on c.id_cust = o.id_cust WHERE o.id_cust = ?";
    private static final String INSERT  = "INSERT INTO feedbacks(id_order, message, rate) VALUES(?, ?, ?)";
    private static final String UPDATE  = "UPDATE feedbacks SET message = ?, rate = ? WHERE id = ?";
    private static final String DELETE  = "DELETE FROM feedbacks WHERE id = ?";

    public FeedbackImpl() {
    }

    public FeedbackImpl(Connection transactionConn) {
        this.transactionConn = transactionConn;
    }

    @Override
    public List<Feedback> listAll() throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Feedback> feedbackList = new ArrayList<>();

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(SELECT1);
            rs = pstm.executeQuery();

            getFeedbacks(rs, feedbackList);
        } finally {
            assert rs != null;
            close(rs);
            close(pstm);
            if (this.transactionConn == null) {
                close(conn);
            }
        }
        return feedbackList;
    }

    @Override
    public List<Feedback> listAll(Customer customer) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Feedback> feedbackList = new ArrayList<>();

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(SELECT2);
            pstm.setInt(1, customer.getCustomerId());
            rs = pstm.executeQuery();

            getFeedbacks(rs, feedbackList);
        } finally {
            assert rs != null;
            close(rs);
            close(pstm);
            if (this.transactionConn == null) {
                close(conn);
            }
        }
        return feedbackList;
    }

    private void getFeedbacks(ResultSet rs, List<Feedback> feedbackList) throws SQLException {
        Feedback feedback;
        while (rs.next()) {
            int id = rs.getInt("id");
            int id_order = rs.getInt("id_order");
            String meal = rs.getString("name_meal");
            String cust = rs.getString("name_cust");
            String msg = rs.getString("message");
            float rate = rs.getFloat("rate");
            feedback = new Feedback(id, id_order, meal, cust, msg, rate);
            feedbackList.add(feedback);
        }
    }

    @Override
    public Feedback findOne(Integer id) throws SQLException {
        List<Feedback> feedbackList = listAll();
        Feedback feedback = null;
        for (Feedback f : feedbackList) {
            if (Objects.equals(f.getFeedBackId(), id)) {
                feedback = f;
                break;
            }
        }
        return feedback;
    }

    @Override
    public int insert(Feedback feedback) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        int row = 0;

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(INSERT);
            pstm.setInt(1, feedback.getOrderId());
            pstm.setString(2, feedback.getMessage());
            pstm.setFloat(3, feedback.getRate());
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
    public int update(Feedback feedback) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        int row = 0;

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(UPDATE);
            pstm.setString(1, feedback.getMessage());
            pstm.setFloat(2, feedback.getRate());
            pstm.setInt(3, feedback.getFeedBackId());
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
    public int delete(Integer id) throws SQLException {
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
