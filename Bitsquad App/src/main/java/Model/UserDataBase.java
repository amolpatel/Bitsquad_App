package Model;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Neal on 2/10/14.
 */
public class UserDataBase {
    public HashMap<String,User> users;

    public UserDataBase() {
        users = new HashMap<String, User>();
        Random rand = new Random();

        User def = new User("Administrator","pass123","admin",0);
        users.put("admin",def);
    }

    public boolean addUser(String name, String password, String userName, int id) {
        if(users.containsKey(userName)){return false;}
        User user = new User(name,password,userName,id);
        users.put(userName,user);
        return true;
    }

    public Boolean verify(String userName, String password) {
        Log.d("Neal", "users.verify() called");
        if(users.containsKey(userName)) {
            return users.get(userName).checkPassword(password);
        }
        else {
            return false;
        }
    }
}
