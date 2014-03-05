package com.example.bitsquadapp;

import Model.Account;
import android.app.Application;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Random;

import Model.User;
import Model.UserDataBase;

/**
 * Created by Neal on 2/14/14.
 */
public class MyApplication extends Application {

    private UserDataBase users;
    private int id;
    private User currentUser;

    public MyApplication(){
        users = new UserDataBase();
        Random rand = new Random();
        id = rand.nextInt();
        currentUser = null;
    }

    public boolean userCheck(String userName, String password) {
        Log.d("Neal", "userCheck called");
        this.currentUser = users.getCurrentUser(userName,password);
        return users.verify(userName, password);
    }

    public boolean addUser(String name, String password, String userName) {
       return users.addUser(name,password,userName,id++);
    }

    public ArrayList<Account> getAccounts(){
        return currentUser.getAccounts();
    }

    public void createAccount(String fullName, String displayName, double balance, double interestRate){
      currentUser.createAccount(fullName,displayName,balance,interestRate);
    }

    public void setCurrentAccount(Account account){currentUser.setCurrentAccount(account);}

    public Account getCurrentAccount(){return currentUser.getCurrentAccount();}
}