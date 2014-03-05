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

    public User(){}

    public User(String name,String password,String userName,int id) {
        this.userName = userName;
        this.pass = password;
        this.name = name;
        this.id = id;
        accounts = new ArrayList<Account>();
    }

    public Boolean checkPassword(String password) {
        return this.pass.equals(password);
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

    public void createAccount(String fullName, String displayName, double balance, double interestRate){
        Account account = new Account(fullName,displayName,balance,interestRate);
        accounts.add(account);
    }

    public void setCurrentAccount(Account account){currentAccount = account;}

    public Account getCurrentAccount(){return currentAccount;}
}
