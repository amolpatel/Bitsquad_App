package com.example.bitsquadapp;

import Model.*;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import database.DataSource;

/**
 * Created by Neal on 2/14/14.
 */
public class MyApplication extends Application {

    private static DataSource dataSource;
    private User currentUser;
    private Account currentAccount;
    private HashMap<String,User> users;
    private Context context;

    public void onCreate(){
        super.onCreate();

        context = getApplicationContext();
        dataSource = new DataSource(context);
        dataSource.open();

        users = dataSource.getUsers();

        currentUser = null;
        currentAccount = null;
    }

    //not guaranteed to execute i.e. force kill etc.
    public void onTerminate() {
        super.onTerminate();
        dataSource.close();
        dataSource = null;
    }

    public boolean userCheck(String userName, String password) {
        Log.d("Neal", "userCheck called");
        User checkUser = users.get(userName);
        if(!userName.equals(checkUser.getUserName()) || !password.equals(checkUser.getPass())){
            return false;
        }
        else{
            this.currentUser = checkUser;
            //gather all users data from database
            currentUser.setAccounts(dataSource.getAccountList(currentUser.getId()));
            for(Account account:currentUser.getAccounts()){
                account.setTransactionHistory(dataSource.getTransactionList(account.getId()));
            }
            return true;
        }
    }

    public boolean addUser(String name, String password, String userName){
        User newUser = new User(name,password,userName);
        newUser.setId((int) dataSource.createUser(newUser));
        users.put(name,newUser);
        return true;
    }

    public ArrayList<Account> getAccounts(){
        return currentUser.getAccounts();
    }

    public void createAccount(String fullName, String displayName, double balance, double interestRate){
        Account newAccount = new Account(fullName,displayName,balance,interestRate);
        newAccount.setId((int)dataSource.createAccount(currentUser.getId(),newAccount));
        getAccounts().add(newAccount);
    }

    public void createTransaction(Date realTime, Date userTime, double amount, String moneySource){

        Transaction transaction = new DepositTransaction(realTime,userTime,amount,moneySource);
        transaction.setId((int)dataSource.createTransaction(currentAccount.getId(), transaction));

        currentAccount.processTransaction(transaction);
        dataSource.updateAccount(currentAccount);
    }

    public void createTransaction(Date realTime, Date userTime, double amount, String withdrawReason, String category){

        Transaction transaction = new WithdrawTransaction(realTime,userTime,amount,withdrawReason,category);
        transaction.setId((int) dataSource.createTransaction(currentAccount.getId(), transaction));

        currentAccount.processTransaction(transaction);
        dataSource.updateAccount(currentAccount);
    }

    public Account getCurrentAccount(){
        return  currentAccount;
    }

    public void setCurrentAccount(Account currentAccount){
        this.currentAccount = currentAccount;
    }
}