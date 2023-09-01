package com.zenedict.onlinemeal.impl;

import com.zenedict.onlinemeal.dao.MealDAO;
import com.zenedict.onlinemeal.entity.Meal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.zenedict.onlinemeal.utils.MySQLConn.close;
import static com.zenedict.onlinemeal.utils.MySQLConn.getConnection;

public class MealImpl implements MealDAO {
    // For transaction connection
    private Connection transactionConn;

    private static final String SELECT = "SELECT * FROM meals";
    private static final String UPDATE = "UPDATE meals SET name_meal = ?, ingredients = ?, price = ? WHERE id_meal = ?";

    public MealImpl() {
    }

    public MealImpl(Connection transactionConn) {
        this.transactionConn = transactionConn;
    }

    @Override
    public List<Meal> findAll() throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Meal> meals = new ArrayList<>();

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(SELECT);
            rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_meal");
                String name = rs.getString("name_meal");
                String ingredients = rs.getString("ingredients");
                float price = rs.getFloat("price");
                Meal meal = new Meal(id, name, ingredients, price);
                meals.add(meal);
            }

        } finally {
            assert rs != null;
            close(rs);
            close(pstm);
            if (this.transactionConn == null) {
                close(conn);
            }
        }
        return meals;
    }

    @Override
    public Meal findOne(Integer id) throws SQLException {
        List<Meal> meals = findAll();
        Meal meal = null;

        for (Meal m : meals) {
            if (m.getMealId() == id) {
                meal = m;
                break;
            }
        }
        return meal;
    }

    @Override
    public Meal findOne(String name) throws SQLException {
        List<Meal> meals = findAll();
        Meal meal = null;

        for (Meal m : meals) {
            if (m.getName().equals(name)) {
                meal = m;
                break;
            }
        }
        return meal;
    }

    @Override
    public int update(Meal meal) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        int row = 0;

        try {
            conn = makeConnection(transactionConn);
            pstm = conn.prepareStatement(UPDATE);
            pstm.setString(1, meal.getName());
            pstm.setString(2, meal.getIngredients());
            pstm.setFloat(3, meal.getPrice());
            pstm.setInt(4, meal.getMealId());
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
