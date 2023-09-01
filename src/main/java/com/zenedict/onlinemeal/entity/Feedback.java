package com.zenedict.onlinemeal.entity;

import java.io.Serializable;
import java.sql.Date;

public class Feedback implements Serializable {
    private Integer feedBackId;
    private Integer orderId;
    private String mealName;
    private String customerName;
    private String message;
    private float rate;

    /* Constructors */
    public Feedback() {
    }
    // to perform the delete function
    public Feedback(Integer feedBackId) {
        this.feedBackId = feedBackId;
    }
    // to make a new feedback
    public Feedback(Integer orderId, String message, float rate) {
        this.orderId = orderId;
        this.message = message;
        this.rate = rate;
    }
    // to retrieve information of the feedback
    public Feedback(Integer feedBackId, Integer orderId, String mealName, String customerName, String message, float rate) {
        this.feedBackId = feedBackId;
        this.orderId = orderId;
        this.mealName = mealName;
        this.customerName = customerName;
        this.message = message;
        this.rate = rate;
    }

    /* Getters & Setters */
    public Integer getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(Integer feedBackId) {
        this.feedBackId = feedBackId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
