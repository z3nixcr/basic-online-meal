package com.zenedict.onlinemeal.dao;

import com.zenedict.onlinemeal.entity.Meal;

import java.sql.SQLException;
import java.util.List;

public interface MealDAO {
    // Find all meals
    public List<Meal> findAll() throws SQLException;

    // Find one meal by id
    public Meal findOne(Integer id) throws SQLException;

    // Find one meal by name
    public Meal findOne(String name) throws SQLException;

    // Update meal information
    public int update(Meal meal) throws SQLException;
}
