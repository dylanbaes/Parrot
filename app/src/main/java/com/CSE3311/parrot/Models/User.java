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
        List<HashMap> rawIncomes = getList("income");
        ArrayList<Income> incomes = new ArrayList<>();
        Gson gson = new Gson();
        assert rawIncomes != null:"Error Log: No Income Data";
        for (HashMap rawIncome : rawIncomes) {
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
        ArrayList<HashMap> toDelete = new ArrayList<>();
        for (Income income :objects){
            toDelete.add(gson.fromJson(gson.toJson(income),HashMap.class));
        }
        this.removeAll("income",toDelete);
        this.saveInBackground();
        return this.getIncomeLists();
    }

    public ArrayList<Expense> getExpenseLists() {
        List<HashMap> rawExpenses = getList("expenses");
        ArrayList<Expense> expenses = new ArrayList<>();
        Gson gson = new Gson();
        assert rawExpenses != null:"Error Log: No Expense Data";
        for (HashMap rawExpense : rawExpenses) {
            expenses.add(gson.fromJson(gson.toJson(rawExpenses), Expense.class));
        }
        return expenses;
    }

    public void addExpense(Expense expense) {
        Gson gson = new Gson();
        this.add("expenses", gson.fromJson(gson.toJson(expense), Map.class));
        this.saveInBackground();
    }

    public ArrayList<Expense> deleteExpense(ArrayList<Expense> objects) {
        Gson gson = new Gson();
        ArrayList<HashMap> toDelete = new ArrayList<>();
        for (Expense expense :objects){
            toDelete.add(gson.fromJson(gson.toJson(expense),HashMap.class));
        }
        this.removeAll("expense",toDelete);
        this.saveInBackground();
        return this.getExpenseLists();
    }
}
