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
import java.util.Arrays;
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

    public ArrayList<Income> deleteIncome(Income income) {
        Gson gson = new Gson();
        List<Map> rawIncomes = getList("incomes");
        if (rawIncomes!=null && rawIncomes.contains(gson.fromJson(gson.toJson(income), Map.class))){
            rawIncomes.remove(gson.fromJson(gson.toJson(income), Map.class));
            put("incomes",rawIncomes);
            saveInBackground();
        }
        return this.getIncomeLists();
    }

    public ArrayList<Income> updateIncome(Income updatedIncome){
        for (Income inc:this.getIncomeLists()){
            if (inc.getUuid().equals(updatedIncome.getUuid())){
                this.deleteIncome(inc);
                break;
            }
        }
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
        for (Expense exp:this.getExpenseLists()){
            if (exp.getUuid().equals(updatedExpense.getUuid())){
                this.deleteExpense(exp);
                break;
            }
        }
        this.addExpense(updatedExpense);
        return this.getExpenseLists();
    };

    public ArrayList<Expense> deleteExpense(Expense expense) {
        Gson gson = new Gson();

        List<Map> rawExpenses = getList("expenses");
        if (rawExpenses!=null && rawExpenses.contains(gson.fromJson(gson.toJson(expense), Map.class))){
            rawExpenses.remove(gson.fromJson(gson.toJson(expense), Map.class));
            put("expenses",rawExpenses);
            saveInBackground();
        }
        return this.getExpenseLists();
    }
}
