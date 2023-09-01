package com.zenedict.onlinemeal.entity;

import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable {
    private int orderId;
    private int mealId;
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String mealName;
    private float price;
    private int quantity;
    private float totalPrice;
    private Date orderDate;

    /* Constructors */
    public Order() {
    }
    // to perform delete function
    public Order(int orderId) {
        this.orderId = orderId;
    }
    // to perform the insertion of new order
    public Order(int mealId, int customerId, int quantity) {
        this.mealId = mealId;
        this.customerId = customerId;
        this.quantity = quantity;
    }
    // to make a new order
    public Order(String customerName, String customerAddress, String mealName, float price, int quantity, float totalPrice, Date orderDate) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.mealName = mealName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }
    // to retrieve information of the order
    public Order(int orderId, int mealId,String customerName, String customerAddress, String mealName, float price, int quantity, float totalPrice, Date orderDate) {
        this.orderId = orderId;
        this.mealId = mealId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.mealName = mealName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    /* Getters & Setters */
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
