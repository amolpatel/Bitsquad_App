package Model;

import java.util.Date;

public class WithdrawTransaction extends Transaction {

    private String withdrawReason;
    private String category;

    public WithdrawTransaction(Date realTime, Date userTime, double amount, String withdrawReason, String category){
        super(realTime,userTime,amount);
        this.withdrawReason = withdrawReason;
        this.category = category;

    }

    public String getWithdrawReason() {
        return withdrawReason;
    }

    public void setWithdrawReason(String withdrawReason){
        this.withdrawReason = withdrawReason;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public double process(){
        return -getAmount();
    }
}
