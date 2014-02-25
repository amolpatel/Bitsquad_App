package Model;

import java.util.ArrayList;
public class Account {

    private String fullName;
    private String displayName;
    private double balance;
    private double interestRate;
    private ArrayList<Transaction> transactionHistory;

    public Account(String fullName, String displayName, double balance, double interestRate){
        this.fullName = fullName;
        this.displayName = displayName;
        this.balance = balance;
        this.interestRate = interestRate;
        transactionHistory = new ArrayList<Transaction>();
    }

    public void processTransaction(Transaction transaction){
        if(transaction instanceof DepositTransaction){
            balance += transaction.getAmount();
        }
        else if(transaction instanceof WithdrawTransaction){
                balance -= transaction.getAmount();
        }else{
            return ;
        }
        transactionHistory.add(transaction);
    }

    public ArrayList<Transaction> getTransactionHistory(){
        return transactionHistory;
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
