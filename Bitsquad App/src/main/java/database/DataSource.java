package database;

import Model.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Trey on 3/3/14.
 */
public class DataSource {

    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;

    public DataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    //User CRUD  methods

    public long createUser(User user){

        ContentValues values = new ContentValues();

        values.put(dbHelper.KEY_username, user.getUserName());
        values.put(dbHelper.KEY_pass,user.getPass());
        values.put(dbHelper.KEY_name,user.getName());


        return db.insert(dbHelper.TABLE_user,null,values);
    }

    public User getUser(int user_id){

        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_user
                +" WHERE " + dbHelper.KEY_ID +" = " + user_id +";";
        Log.e(dbHelper.LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null){
            c.moveToFirst();
        }

        User user = new User();

        user.setId(c.getInt(c.getColumnIndex(dbHelper.KEY_ID)));
        user.setUserName(c.getString(c.getColumnIndex(dbHelper.KEY_username)));
        user.setName(c.getString(c.getColumnIndex(dbHelper.KEY_name)));
        user.setPass(c.getString(c.getColumnIndex(dbHelper.KEY_pass)));

        c.close();
        return  user;
    }

    public HashMap<String,User> getUsers(){
        HashMap<String,User> users = new HashMap<String, User>();
        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_user +";";
        Log.e(dbHelper.LOG,selectQuery);

        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst()){
            do {
                User user = new User();

                user.setId(c.getInt(c.getColumnIndex(dbHelper.KEY_ID)));
                user.setUserName(c.getString(c.getColumnIndex(dbHelper.KEY_username)));
                user.setName(c.getString(c.getColumnIndex(dbHelper.KEY_name)));
                user.setPass(c.getString(c.getColumnIndex(dbHelper.KEY_pass)));


                users.put(user.getUserName(), user);
            }while(c.moveToNext());
        }
        c.close();
        return users;
    }

    public void deleteUser(int user_id){

        Log.e(dbHelper.LOG, "User with id "+ user_id + " is being deleted");

        //delete accounts associated with user
        db.delete(dbHelper.TABLE_account,dbHelper.KEY_user_id + "=" + user_id,null);

        //delete user
        db.delete(dbHelper.TABLE_user,dbHelper.KEY_ID + "=" + user_id,null);
    }

    //Account CRUD methods

    public long createAccount(int user_id, Account account){

        ContentValues values = new ContentValues();

        values.put(dbHelper.KEY_fullname,account.getFullName());
        values.put(dbHelper.KEY_displayname,account.getDisplayName());
        values.put(dbHelper.KEY_balance,account.getBalance());
        values.put(dbHelper.KEY_interestrate,account.getInterestRate());
        values.put(dbHelper.KEY_user_id,user_id);

        return db.insert(dbHelper.TABLE_account,null,values);
    }

    public Account getAccount(int account_id){

        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_account
                +" WHERE " + dbHelper.KEY_ID +" = " + account_id +";";
        Log.e(dbHelper.LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Account account = new Account();

        account.setId(c.getInt(c.getColumnIndex(dbHelper.KEY_ID)));
        account.setFullName(c.getString(c.getColumnIndex(dbHelper.KEY_fullname)));
        account.setDisplayName(c.getString(c.getColumnIndex(dbHelper.KEY_displayname)));
        account.setBalance(c.getDouble(c.getColumnIndex(dbHelper.KEY_balance)));
        account.setInterestRate(c.getDouble(c.getColumnIndex(dbHelper.KEY_interestrate)));
        c.close();
        return  account;
    }

    public ArrayList<Account> getAccountList(int user_id){
        ArrayList<Account> accounts = new ArrayList<Account>();
        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_account +
                " WHERE " + dbHelper.KEY_user_id +" = " + user_id + ";";
        Log.e(dbHelper.LOG,selectQuery);

        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst()){
            do {
                Account account = new Account();

                account.setId(c.getInt(c.getColumnIndex(dbHelper.KEY_ID)));
                account.setFullName(c.getString(c.getColumnIndex(dbHelper.KEY_fullname)));
                account.setDisplayName(c.getString(c.getColumnIndex(dbHelper.KEY_displayname)));
                account.setBalance(c.getDouble(c.getColumnIndex(dbHelper.KEY_balance)));
                account.setInterestRate(c.getDouble(c.getColumnIndex(dbHelper.KEY_interestrate)));

                accounts.add(account);
            }while(c.moveToNext());
        }
        c.close();
        return accounts;
    }

    public void updateAccount(Account account){


        ContentValues values = new ContentValues();

        values.put(dbHelper.KEY_fullname,account.getFullName());
        values.put(dbHelper.KEY_displayname,account.getDisplayName());
        values.put(dbHelper.KEY_balance,account.getBalance());
        values.put(dbHelper.KEY_interestrate,account.getInterestRate());

        db.update(dbHelper.TABLE_account,values,dbHelper.KEY_ID + "=" + account.getId(),null);
    }

    public void deleteAccount(int account_id){

        db.delete(dbHelper.TABLE_account,dbHelper.KEY_ID + "=" + account_id,null);
    }

    //Transaction CRUD methods

    public long createTransaction(int account_id, Transaction transaction){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues values = new ContentValues();

        values.put(dbHelper.KEY_amount,transaction.getAmount());
        values.put(dbHelper.KEY_realtime,dateFormat.format(transaction.getRealTime()));
        values.put(dbHelper.KEY_usertime,dateFormat.format(transaction.getUserTime()));
        values.put(dbHelper.KEY_account_id,account_id);

        long transaction_id = db.insert(dbHelper.TABLE_transaction,null,values);

        if(transaction instanceof DepositTransaction){
            values = new ContentValues();

            values.put(dbHelper.KEY_moneysource,((DepositTransaction) transaction).getMoneySource());
            values.put(dbHelper.KEY_transaction_id,transaction_id);

            return db.insert(dbHelper.TABLE_deposittransaction,null,values);

        }else if(transaction instanceof WithdrawTransaction){

            values = new ContentValues();

            values.put(dbHelper.KEY_withdrawreason,((WithdrawTransaction) transaction).getWithdrawReason());
            values.put(dbHelper.KEY_category,((WithdrawTransaction) transaction).getCategory());
            values.put(dbHelper.KEY_transaction_id,transaction_id);

            return db.insert(dbHelper.TABLE_withdrawtransaction,null,values);

        }
        return -1;
    }

    public Transaction getTransaction(Transaction transaction) throws Exception {
        String selectQuery;
        Cursor c;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(transaction instanceof DepositTransaction){

            selectQuery = "SELECT * FROM " + dbHelper.TABLE_deposittransaction
                    +" WHERE " + dbHelper.KEY_transaction_id +" = " + transaction.getId() +";";
            Log.e(dbHelper.LOG, selectQuery);

            c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();

            int id = c.getInt(c.getColumnIndex(dbHelper.KEY_ID));
            Date realTime = dateFormat.parse(c.getString(c.getColumnIndex(dbHelper.KEY_realtime)));
            Date userTime = dateFormat.parse(c.getString(c.getColumnIndex(dbHelper.KEY_usertime)));
            double amount = c.getDouble(c.getColumnIndex(dbHelper.KEY_amount));
            String moneySource = c.getString(c.getColumnIndex(dbHelper.KEY_moneysource));

            Transaction returnTransaction = new DepositTransaction(realTime,userTime,amount,moneySource);
            returnTransaction.setId(id);

            c.close();
            return  returnTransaction;

        }else if(transaction instanceof  WithdrawTransaction){
            selectQuery = "SELECT * FROM " + dbHelper.TABLE_withdrawtransaction
                    +" WHERE " + dbHelper.KEY_transaction_id +" = " + transaction.getId() +";";  //maybe need key_transaction id
            Log.e(dbHelper.LOG, selectQuery);

            c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();

            int id = c.getInt(c.getColumnIndex(dbHelper.KEY_ID));
            Date realTime = dateFormat.parse(c.getString(c.getColumnIndex(dbHelper.KEY_realtime)));
            Date userTime = dateFormat.parse(c.getString(c.getColumnIndex(dbHelper.KEY_usertime)));
            double amount = c.getDouble(c.getColumnIndex(dbHelper.KEY_amount));
            String withdrawReason = c.getString(c.getColumnIndex(dbHelper.KEY_withdrawreason));
            String category = c.getString(c.getColumnIndex(dbHelper.KEY_category));

            Transaction returnTransaction = new WithdrawTransaction(realTime,userTime,amount,withdrawReason,category);
            returnTransaction.setId(id);

            c.close();
            return  returnTransaction;

        }
        return null;
    }

    public ArrayList<Transaction> getTransactionList(int account_id){

        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_transaction
                +" LEFT OUTER JOIN " + dbHelper.TABLE_deposittransaction
                    +" ON " + dbHelper.TABLE_transaction + "." + dbHelper.KEY_ID + "=" + dbHelper.TABLE_deposittransaction
                    + "." + dbHelper.KEY_transaction_id
                + " LEFT OUTER JOIN " + dbHelper.TABLE_withdrawtransaction
                    + " ON " + dbHelper.TABLE_transaction + "." + dbHelper.KEY_ID + "=" + dbHelper.TABLE_withdrawtransaction
                    + "." + dbHelper.KEY_transaction_id
                + " WHERE " + dbHelper.TABLE_transaction + "." + dbHelper.KEY_account_id + "=" + account_id
                + " ORDER BY " + dbHelper.TABLE_transaction + "." + dbHelper.KEY_ID
                + ";"
         ;

        Log.e(dbHelper.LOG,selectQuery);

        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst()){
            do {
                //Check type of transaction
                if(c.getString(c.getColumnIndex(dbHelper.KEY_category))!= null) {
                    Date realTime = new Date(c.getInt(c.getColumnIndex(dbHelper.KEY_realtime)));
                    Date userTime = new Date(c.getInt(c.getColumnIndex(dbHelper.KEY_usertime)));
                    double amount = c.getDouble(c.getColumnIndex(dbHelper.KEY_amount));
                    String withdrawReason = c.getString(c.getColumnIndex(dbHelper.KEY_withdrawreason));
                    String category = c.getString(c.getColumnIndex(dbHelper.KEY_category));

                    WithdrawTransaction withdrawTransaction = new WithdrawTransaction(realTime, userTime,amount,withdrawReason, category);
                    withdrawTransaction.setId(c.getInt(c.getColumnIndex(dbHelper.KEY_ID)));

                    transactionList.add(withdrawTransaction);
                }
                else{
                    Date realTime = new Date(c.getInt(c.getColumnIndex(dbHelper.KEY_realtime)));
                    Date userTime = new Date(c.getInt(c.getColumnIndex(dbHelper.KEY_usertime)));
                    double amount = c.getDouble(c.getColumnIndex(dbHelper.KEY_amount));
                    String moneySource = c.getString(c.getColumnIndex(dbHelper.KEY_moneysource));

                    DepositTransaction depositTransaction = new DepositTransaction(realTime,userTime,amount,moneySource);
                    depositTransaction.setId(c.getInt(c.getColumnIndex(dbHelper.KEY_ID)));

                    transactionList.add(depositTransaction);
                }

            }while(c.moveToNext());
        }
        c.close();
        return transactionList;
    }
}
