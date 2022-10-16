package com.CSE3311.parrot.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;
import java.util.UUID;

public class Income {
    private String incomeName; // part-time, full-time ...
    private String description;
    private String paymentAmount;
    private String paymentType; // biweekly or monthly
    private Date paymentDate;
    private Date notificationDate;
    private String uuid;

    public Income(){
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIncomeName() {
        return incomeName;
    }

    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
