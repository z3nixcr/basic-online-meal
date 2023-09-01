package com.zenedict.onlinemeal.entity;

import java.io.Serializable;

public class Meal implements Serializable {
    private int mealId;
    private String name;
    private String ingredients;
    private float price;

    /* Constructors */
    public Meal() {
    }
    // in case to perform delete function (but it won't be necessary)
    public Meal(int mealId) {
        this.mealId = mealId;
    }
    // to create new meal in case is desired
    public Meal(String name, String ingredients, float price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }
    // for retrieving meal information
    public Meal(int mealId, String name, String ingredients, float price) {
        this.mealId = mealId;
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    /* Getters & Setters */
    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
