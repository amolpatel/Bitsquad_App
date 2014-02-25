package Model;

import java.util.Date;

public abstract class Transaction {

    private int id;
    private double amount;
    private Date realTime;
    private Date userTime;

    public Transaction(Date realTime, Date userTime, double amount){
        this.realTime = realTime;
        this. userTime = userTime;
        this.amount = amount;
    }

    public int getId(){
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Date getRealTime() {
        return realTime;
    }

    public Date getUserTime() {
        return userTime;
    }
}
