package Model;

import java.util.Date;

public class DepositTransaction extends Transaction{

    private String moneySource;

    public DepositTransaction(Date realTime, Date userTime, double amount, String moneySource){
        super(realTime,userTime,amount);
        this.moneySource = moneySource;
    }

    public String getMoneySource() {
        return moneySource;
    }

    public void setMoneySource(String moneySource){
        this.moneySource = moneySource;
    }
}
