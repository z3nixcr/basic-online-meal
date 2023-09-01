package com.zenedict.onlinemeal.web;

import com.zenedict.onlinemeal.dao.CustomerDAO;
import com.zenedict.onlinemeal.entity.Customer;
import com.zenedict.onlinemeal.entity.Meal;
import com.zenedict.onlinemeal.impl.CustomerImpl;
import com.zenedict.onlinemeal.impl.MealImpl;
import com.zenedict.onlinemeal.utils.MySQLConn;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServletUser", value = "/ServletUser")
public class ServletUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Switch the action base on user process
        switch (action) {
            case "login":
                loginValidation(request, response);
                break;

            case "register":
                registerValidation(request, response);
                break;
        }
    }

    private void registerValidation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        HttpSession session  = request.getSession();
        Customer customer;
        CustomerDAO customerDAO;
        Connection connection = null;
        String error_register = "";

        try {
            connection = MySQLConn.getConnection();
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }
            customerDAO = new CustomerImpl(connection);
            customer = customerDAO.findOne(email);
            if (customer == null) {
                customer = new Customer(name, email, password, address);
                int row = customerDAO.create(customer);
                connection.commit();
                System.out.println(row + " customer added successfully!");
                session.setAttribute("error_register", error_register);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                error_register = "* User email already exists. Please login";
                session.setAttribute("error_register", error_register);
                response.sendRedirect("register.jsp");
            }
        } catch (ClassNotFoundException | SQLException e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    private void loginValidation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session  = request.getSession();
        Customer customer;
        CustomerDAO customerDAO = new CustomerImpl();
        List<Meal> mealList;
        String error_login = "";

        try {
            customer = customerDAO.findOne(email);
            mealList = new MealImpl().findAll();
            if (customer != null && customer.getPassword().equals(password)) {
                session.setAttribute("user", customer);
                session.setAttribute("error_login", error_login);
                session.setAttribute("meals", mealList);
                request.getRequestDispatcher("pages/customer/main-page.jsp").forward(request, response);
            } else {
                error_login = "* Email or password incorrect";
                session.setAttribute("error_login", error_login);
                response.sendRedirect("index.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
