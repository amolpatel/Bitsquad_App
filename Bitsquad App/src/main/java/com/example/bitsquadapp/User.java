package com.example.bitsquadapp;

/**
 * Created by Neal on 2/10/14.
 */
public class User {
    private String userName;
    private String pass;
    private String name;
    private int id;

    /* For later implementation */
    // private Account[] accounts;

    public User(String name,String password,String userName,int id) {
        this.userName = userName;
        this.pass = password;
        this.name = name;
        this.id = id;
    }

    public Boolean checkPassword(String password) {
        return this.pass.equals(password);
    }
}
