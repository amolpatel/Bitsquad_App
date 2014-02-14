package com.example.bitsquadapp;

import android.app.Application;
import android.util.Log;

import java.util.Random;

import Model.User;
import Model.UserDataBase;

/**
 * Created by Neal on 2/14/14.
 */
public class MyApplication extends Application {

    UserDataBase users = new UserDataBase();
    Random rand = new Random();
    int id = rand.nextInt();

    public boolean userCheck(String userName, String password) {
        Log.d("Neal", "userCheck called");
        return users.verify(userName, password);
    }

    public void addUser(String name, String password, String userName) {
        users.addUser(name,password,userName,id++);
    }
}