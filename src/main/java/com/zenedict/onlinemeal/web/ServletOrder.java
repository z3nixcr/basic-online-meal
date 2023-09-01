package com.zenedict.onlinemeal.web;

import com.zenedict.onlinemeal.dao.FeedbackDAO;
import com.zenedict.onlinemeal.dao.OrderDAO;
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

@WebServlet(name = "ServletOrder", value = "/ServletOrder")
public class ServletOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "edit":
                goToEditPage(request, response);
                break;

            case "delete":
                deleteOrder(request, response);
                break;

            case "comment":
                goToCommentPage(request, response);
                break;
        }
    }

    private void goToCommentPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String file = "feedback.jsp";
        initResources(request, response, file);
    }

    private void initResources(HttpServletRequest request, HttpServletResponse response, String file) throws ServletException, IOException {
        int idOrder = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        Order order;

        try {
            order = new OrderImpl().findOne(idOrder);
            session.setAttribute("order", order);
            session.setAttribute("user", customer);
            request.getRequestDispatcher("pages/customer/" + file).forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idOrder = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        Connection conn = null;
        OrderDAO orderDAO;

        try {
            conn = MySQLConn.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            orderDAO = new OrderImpl(conn);
            int row = orderDAO.deleteById(idOrder);
            conn.commit();
            System.out.println(row + " order deleted successfully");
            modifyMyOrder(session, customer, orderDAO);
            response.sendRedirect("pages/customer/my_orders.jsp");

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
        String file = "edit-order.jsp";
        initResources(request, response, file);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "edit":
                editOrder(request, response);
                break;

            case "comment":
                makeComment(request, response);
                break;
        }
    }

    private void makeComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idOrder = Integer.parseInt(request.getParameter("id"));
        float rate = Float.parseFloat(request.getParameter("rate"));
        String comment = request.getParameter("comment");
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        FeedbackDAO feedbackDAO;
        Connection conn = null;

        try {
            conn = MySQLConn.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            feedbackDAO = new FeedbackImpl(conn);
            Feedback feedback = new Feedback(idOrder, comment, rate);
            int row = feedbackDAO.insert(feedback);
            conn.commit();
            System.out.println(row + " feedback sent successfully");
            request.getRequestDispatcher("pages/customer/my_orders.jsp").forward(request, response);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void editOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int idOrder = Integer.parseInt(request.getParameter("id"));
        Connection conn = null;
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");

        try {
            conn = MySQLConn.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            OrderDAO orderDAO = new OrderImpl(conn);
            int row = orderDAO.updateById(idOrder, quantity);
            conn.commit();
            System.out.println(row + " order updated successfully");
            modifyMyOrder(session, customer, orderDAO);
            request.getRequestDispatcher("pages/customer/my_orders.jsp").forward(request, response);
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

    private void modifyMyOrder(HttpSession session, Customer customer, OrderDAO orderDAO) throws SQLException {
        List<Order> orderList = orderDAO.findAll(customer);
        float total = 0;
        for (Order o : orderList) {
            total += o.getTotalPrice();
        }
        session.setAttribute("total", total);
        session.setAttribute("orders", orderList);
    }
}
