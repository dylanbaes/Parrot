package com.CSE3311.parrot.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;
import java.util.UUID;

public class Expense {
    private String subscriptionName;
    private String description;
    private String paymentType;
    private String subscriptionType;
    private final String uuid;
    private Date startDate;
    private Date endDate;
    private Date notificationDate;

    public Expense() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getSubscriptionName() {
        return this.subscriptionName;
    }

    public String getSubscriptionType() {
        return this.subscriptionType;
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

    public void setSubscription(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
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
