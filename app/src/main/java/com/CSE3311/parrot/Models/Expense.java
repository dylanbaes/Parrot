package com.CSE3311.parrot.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Expense implements Serializable {
    private String categoryName;
    private String description;
    private String paymentType;
    private String categoryType;
    private final String uuid;
    private String startDate;
    private String endDate;
    private String notificationType;
    private String notificationDate;
    private String cost;

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

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public String getNotificationDate() {
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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getUuid() {
        return uuid;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
