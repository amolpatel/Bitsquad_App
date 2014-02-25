package Model;

import java.util.List;

public class Account {

    private String fullName;
    private String displayName;
    private double balance;
    private double interestRate;
    private List<Transaction> transactionHistory;

    public Account(String fullName, String displayName, double balance, double interestRate){
        this.fullName = fullName;
        this.displayName = displayName;
        this.balance = balance;
        this.interestRate = interestRate;
    }

    public void processTransaction(Transaction transaction){
        return ;
    }

    public String getFullName() {
        return fullName;
    }

    public double getBalance() {
        return balance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public String toString(){
        return displayName;
    }

}
