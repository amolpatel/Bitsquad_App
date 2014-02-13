package com.example.bitsquadapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Neal on 2/10/14.
 */
public class UserDataBase {
    private Map<String,User> users;
    private int idGen;

    public UserDataBase() {
        users = new HashMap<String, User>();
        Random rand = new Random();
        idGen = rand.nextInt();
    }

    public void addUser(String name, String password, String userName, int id) {
        User user = new User(name,password,userName,idGen++);
        users.put(userName,user);
    }

    public Boolean verify(String userName, String password) {
        return users.get(userName).checkPassword(password);
    }
}
