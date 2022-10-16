package com.CSE3311.parrot.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;
import java.util.UUID;

public class Expense {
    private String categoryName;
    private String description;
    private String paymentType;
    private String categoryType;
    private final String uuid;
    private Date startDate;
    private Date endDate;
    private Date notificationDate;

    public Expense() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public String getCategoryType() {
        return this.categoryType;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public Date getNotificationDate() {
        return this.notificationDate;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getUuid() {
        return uuid;
    }
}
