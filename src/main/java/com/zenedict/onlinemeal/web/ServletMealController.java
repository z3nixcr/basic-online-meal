package com.zenedict.onlinemeal.web;

import com.zenedict.onlinemeal.dao.OrderDAO;
import com.zenedict.onlinemeal.entity.Customer;
import com.zenedict.onlinemeal.entity.Meal;
import com.zenedict.onlinemeal.entity.Order;
import com.zenedict.onlinemeal.impl.MealImpl;
import com.zenedict.onlinemeal.impl.OrderImpl;
import com.zenedict.onlinemeal.utils.MySQLConn;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServletMealController", value = "/ServletMealController")
public class ServletMealController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "order":
                goToPlaceOrder(request, response);
                break;

            case "display":
                displayOrders(request, response);
                break;

            case "menu":
                goToMenu(request, response);
                break;
        }
    }

    private void goToMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            List<Meal> mealList = new MealImpl().findAll();
            session.setAttribute("meals", mealList);
            request.getRequestDispatcher("pages/customer/main-page.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void displayOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");

        try {
            List<Order> orderList = new OrderImpl().findAll(customer);
            float total = 0;
            for (Order o : orderList) {
                total += o.getTotalPrice();
            }
            session.setAttribute("total", total);
            session.setAttribute("orders", orderList);
            session.setAttribute("user", customer);
            request.getRequestDispatcher("pages/customer/my_orders.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void goToPlaceOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.valueOf(request.getParameter("id"));
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        Meal meal;

        try {
            meal = new MealImpl().findOne(id);
            session.setAttribute("this_meal", meal);
            session.setAttribute("user", customer);
            request.getRequestDispatcher("pages/customer/place-order.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "order":
                placeOrder(request, response);
                break;

            case "feedback":
                break;
        }
    }

    private void placeOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idMeal  = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("user");
        Connection conn = null;
        OrderDAO orderDAO;

        try {
            conn = MySQLConn.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            Order order = new Order(idMeal, customer.getCustomerId(), quantity);
            orderDAO = new OrderImpl(conn);
            int row = orderDAO.insertOrder(order);
            conn.commit();
            System.out.println(row + " order has placed successfully");
            request.getRequestDispatcher("pages/customer/main-page.jsp").forward(request, response);

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
