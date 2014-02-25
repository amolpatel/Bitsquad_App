package com.example.bitsquadapp;

import Model.Account;
import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import Model.User;
import Model.UserDataBase;

/**
 * Created by Neal on 2/14/14.
 */
public class MyApplication extends Application {

    private UserDataBase users = new UserDataBase();
    private Random rand = new Random();
    private int id = rand.nextInt();
    private ArrayList<Account> accounts;

    public boolean userCheck(String userName, String password) {
        Log.d("Neal", "userCheck called");
        return users.verify(userName, password);
    }

    public boolean addUser(String name, String password, String userName) {
       return users.addUser(name,password,userName,id++);
    }

    public ArrayList<Account> getAccounts(){
        return accounts;
    }

    public void createAccount(String fullName, String displayName, double balance, double interestRate){
        Account account = new Account(fullName,displayName,balance,interestRate);
        accounts.add(account);
    }
}