package Model;

import java.util.ArrayList;

/**
 * Created by Neal on 2/10/14.
 */
public class User {

    private String userName;
    private String pass;
    private String name;
    private int id;
    private ArrayList<Account> accounts;
    private Account currentAccount;

    public User(){
        accounts = new ArrayList<Account>();
    }

    public User(String name,String password,String userName) {
        this.userName = userName;
        this.pass = password;
        this.name = name;
        accounts = new ArrayList<Account>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getPass(){
        return  pass;
    }

    public void setPass(String pass){
        this.pass = pass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ArrayList<Account> getAccounts(){
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts){
        this.accounts = accounts;
    }

    public void setCurrentAccount(Account account){currentAccount = account;}

    public Account getCurrentAccount(){return currentAccount;}
}
