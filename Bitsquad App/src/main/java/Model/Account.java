package Model;

import java.util.ArrayList;
public class Account {

    private int id;
    private String fullName;
    private String displayName;
    private double balance;
    private double interestRate;
    private ArrayList<Transaction> transactionHistory;

    public Account(){
        transactionHistory = new ArrayList<Transaction>();
    }

    public Account(String fullName, String displayName, double balance, double interestRate){
        this.fullName = fullName;
        this.displayName = displayName;
        this.balance = balance;
        this.interestRate = interestRate;
        transactionHistory = new ArrayList<Transaction>();
    }

    public void processTransaction(Transaction transaction){
        balance += transaction.process();
        transactionHistory.add(transaction);
    }

    public ArrayList<Transaction> getTransactionHistory(){
        return transactionHistory;
    }

    public void setTransactionHistory(ArrayList<Transaction> transactionHistory){
        this.transactionHistory = transactionHistory;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public String getDisplayName(){
        return displayName;
    }

    public void setDisplayName(String displayName){
        this.displayName = displayName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate){
        this.interestRate = interestRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    public String toString(){
        return displayName;
    }


}
