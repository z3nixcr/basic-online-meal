package com.zenedict.onlinemeal.web;

import com.zenedict.onlinemeal.dao.FeedbackDAO;
import com.zenedict.onlinemeal.entity.Customer;
import com.zenedict.onlinemeal.entity.Feedback;
import com.zenedict.onlinemeal.entity.Order;
import com.zenedict.onlinemeal.impl.FeedbackImpl;
import com.zenedict.onlinemeal.impl.OrderImpl;
import com.zenedict.onlinemeal.utils.MySQLConn;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServletFeedback", value = "/ServletFeedback")
public class ServletFeedback extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "display":
                displayFeedbacks(request, response);
                break;

            case "edit":
                goToEditPage(request, response);
                break;

            case "delete":
                deleteFeedback(request, response);
                break;
        }
    }

    private void deleteFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idFeedback = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        Connection conn = null;

        try {
            conn = MySQLConn.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            FeedbackDAO feedbackDAO = new FeedbackImpl(conn);
            int row = feedbackDAO.delete(idFeedback);
            conn.commit();
            System.out.println(row + " feedback has been deleted successfully");
            List<Feedback> feedbackList = feedbackDAO.listAll(customer);
            session.setAttribute("feedbacks", feedbackList);
            response.sendRedirect("pages/customer/my_feedbacks.jsp");
        } catch (ClassNotFoundException | SQLException e) {
            try {
                assert conn != null;
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    private void goToEditPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idFeedback = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        Feedback feedback;
        Order order;

        try {
            feedback = new FeedbackImpl().findOne(idFeedback);
            order = new OrderImpl().findOne(feedback.getOrderId());
            session.setAttribute("order", order);
            session.setAttribute("feedback", feedback);
            session.setAttribute("user", customer);
            request.getRequestDispatcher("pages/customer/edit-feedback.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void displayFeedbacks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");

        try {
            List<Feedback> feedbackList = new FeedbackImpl().listAll(customer);
            session.setAttribute("feedbacks", feedbackList);
            session.setAttribute("user", customer);
            request.getRequestDispatcher("pages/customer/my_feedbacks.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idFeedback = Integer.parseInt(request.getParameter("id"));
        float rate = Float.parseFloat(request.getParameter("rate"));
        String msg = request.getParameter("comment");
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        Connection conn = null;

        try {
            conn = MySQLConn.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            FeedbackDAO feedbackDAO = new FeedbackImpl(conn);
            Feedback feedback = feedbackDAO.findOne(idFeedback);
            feedback.setRate(rate);
            feedback.setMessage(msg);
            int row = feedbackDAO.update(feedback);
            conn.commit();
            System.out.println(row + " feedback has been updated successfully");
            List<Feedback> feedbackList = feedbackDAO.listAll(customer);
            session.setAttribute("feedbacks", feedbackList);
            session.setAttribute("user", customer);
            request.getRequestDispatcher("pages/customer/my_feedbacks.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            try {
                assert conn != null;
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
