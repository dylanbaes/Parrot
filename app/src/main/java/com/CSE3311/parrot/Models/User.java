package com.CSE3311.parrot.Models;

import java.util.ArrayList;
import java.util.LinkedList;

public class User {
    private final String userId;
    private String email;
    private String fName;
    private String lName;
    private String userName;

    private LinkedList<Expense> expenseLists;
    private LinkedList<Income> incomeLists;

    public User(String userId){
        this.userId = userId;
        this.expenseLists = new LinkedList<>();
        this.incomeLists = new LinkedList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LinkedList<Income> getIncomeLists() {
        return this.incomeLists;
    }

    public void addIncome(Income e){
        this.incomeLists.add(e);
    }

    public void deleteIncome(int index){
        this.incomeLists.remove(index);
    }

    public void setIncomeLists(LinkedList<Income> incomeLists) {
        this.incomeLists = incomeLists;
    }

    public LinkedList<Expense> getExpenseLists() {
        return expenseLists;
    }
    public void addExpense(Expense e){
        this.expenseLists.add(e);
    }

    public void deleteExpense(int index){
        this.expenseLists.remove(index);
    }

    public void setExpenseLists(LinkedList<Expense> expenseLists) {
        this.expenseLists= expenseLists;
    }

}
