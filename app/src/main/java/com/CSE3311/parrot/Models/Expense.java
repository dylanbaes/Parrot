package com.CSE3311.parrot.Models;

import java.util.Date;
import java.util.UUID;

public class Expense {
    private final String userId;
    private String subscriptionName;
    private String description;
    private String paymentType;
    private String subscriptionType;
    private Date startDate;
    private Date endDate;
    private Date notificationDate;


    public Expense(String userId){
        /*
         *   userId = UserId of the signed-in user
         */
        this.userId = userId;
        UUID objectId = UUID.randomUUID();
    }

    public String getUserId(){
        return this.userId;
    }

    public String getSubscriptionName(){
        return this.subscriptionName;
    }

    public  String getSubscriptionType(){
        return this.subscriptionType;
    }

    public String getDescription(){
        return this.description;
    }

    public String getPaymentType(){
        return this.paymentType;
    }

    public Date getStartDate(){
        return this.startDate;
    }

    public Date getEndDate(){
        return this.endDate;
    }

    public Date getNotificationDate() {
        return this.notificationDate;
    }

    public void setSubscription(String subscriptionName){
        this.subscriptionName = subscriptionName;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPaymentType(String paymentType){
        this.paymentType = paymentType;
    }

    public void setSubscriptionType(String subscriptionType){
        this.subscriptionType = subscriptionType;
    }

    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    public void setNotificationDate(Date notificationDate){
        this.notificationDate = notificationDate;
    }



}
