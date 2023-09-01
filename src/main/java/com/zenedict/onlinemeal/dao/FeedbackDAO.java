package com.zenedict.onlinemeal.dao;

import com.zenedict.onlinemeal.entity.Customer;
import com.zenedict.onlinemeal.entity.Feedback;

import java.sql.SQLException;
import java.util.List;

public interface FeedbackDAO {
    // List all the feedbacks
    public List<Feedback> listAll() throws SQLException;

    // List the feedbacks issued by a certain customer
    public List<Feedback> listAll(Customer customer) throws SQLException;

    // Find a feedback by an ID
    public Feedback findOne(Integer id) throws SQLException;

    // Make or insert a feedback
    public int insert(Feedback feedback) throws SQLException;

    // Update a feedback information by ID
    int update(Feedback feedback) throws SQLException;

    // Delete a feedback by ID
    public int delete(Integer id) throws SQLException;
}
