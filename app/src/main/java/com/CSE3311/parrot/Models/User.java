package com.CSE3311.parrot.Models;

import android.app.ProgressDialog;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@ParseClassName("UserInfo")
public class User extends ParseObject {

    public User() {
        super();
    }

    public String getUserName() {
        return getString("userName");
    }

    public void setUserName(String userName) {
        put("userName", userName);
        this.saveInBackground();
    }

    public String getlName() {
        return getString("lName");
    }

    public void setlName(String lName) {
        put("lName", lName);
        this.saveInBackground();
    }

    public String getfName() {
        return getString("fName");
    }

    public void setfName(String fName) {
        put("fName", fName);
        this.saveInBackground();
    }

    public String getEmail() {
        return getString("email");
    }

    public void setEmail(String email) {
        put("email", email);
        this.saveInBackground();
    }

    public ArrayList<Income> getIncomeLists() {
        List<Map> rawIncomes = getList("incomes");
        ArrayList<Income> incomes = new ArrayList<>();
        Gson gson = new Gson();
        assert rawIncomes != null:"Error Log: No Income Data";
        for (Map rawIncome : rawIncomes) {
            incomes.add(gson.fromJson(gson.toJson(rawIncome), Income.class));
        }
        return incomes;
    }

    public void addIncome(Income inc) {
        Gson gson = new Gson();
        this.add("incomes", gson.fromJson(gson.toJson(inc), Map.class));
        this.saveInBackground();
    }

    public ArrayList<Income> deleteIncome(ArrayList<Income> objects) {
        Gson gson = new Gson();
        ArrayList<Map> toDelete = new ArrayList<>();
        for (Income income :objects){
            toDelete.add(gson.fromJson(gson.toJson(income),Map.class));
        }
        this.removeAll("incomes",toDelete);
        this.saveInBackground();
        return this.getIncomeLists();
    }

    public ArrayList<Income> updateIncome(Income updatedIncome){
        ArrayList<Income> toUpdateIncomes = new ArrayList<>();
        // find the income to be updated based on it uuid
        for (Income inc: this.getIncomeLists()){
            if (inc.getUuid().equals(updatedIncome.getUuid())) {
                toUpdateIncomes.add(updatedIncome);
                break;
            }
        }
        this.deleteIncome(toUpdateIncomes);
        this.addIncome(updatedIncome);
        return this.getIncomeLists();
    };

    public ArrayList<Expense> getExpenseLists() {
        List<Map> rawExpenses = getList("expenses");
        ArrayList<Expense> expenses = new ArrayList<>();
        Gson gson = new Gson();
        assert rawExpenses != null:"Error Log: No Expense Data";
        for (Map rawExpense : rawExpenses) {
            expenses.add(gson.fromJson(gson.toJson(rawExpense), Expense.class));
        }
        return expenses;
    }

    public void addExpense(Expense expense) {
        Gson gson = new Gson();
        this.add("expenses", gson.fromJson(gson.toJson(expense), Map.class));
        this.saveInBackground();
    }

    public ArrayList<Expense> updateExpense(Expense updatedExpense){
        ArrayList<Expense> toUpdateExpense = new ArrayList<>();
        // find the income to be updated based on it uuid
        for (Expense exp: this.getExpenseLists()){
            if (exp.getUuid().equals(updatedExpense.getUuid())) {
                toUpdateExpense.add(updatedExpense);
                break;
            }
        }
        this.deleteExpense(toUpdateExpense);
        this.addExpense(updatedExpense);
        return this.getExpenseLists();
    };

    public ArrayList<Expense> deleteExpense(ArrayList<Expense> objects) {
        Gson gson = new Gson();
        ArrayList<Map> toDelete = new ArrayList<>();
        for (Expense expense :objects){
            toDelete.add(gson.fromJson(gson.toJson(expense),Map.class));
        }
        this.removeAll("expenses",toDelete);
        this.saveInBackground();
        return this.getExpenseLists();
    }
}
