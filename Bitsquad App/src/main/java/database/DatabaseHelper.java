package database;

import Model.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String LOG = "DatabaseHelper";

    private static final String DATABASE_NAME = "bitsqaudDB.db";
    private static final int DATABASE_VERSION = 1;

    //Table names
    private static final String TABLE_user = "user";
    private static final String TABLE_account = "account";
    private static final String TABLE_transaction = "transaction";
    private static final String TABLE_deposittransaction = "deposittransaction";
    private static final String TABLE_withdrawtransaction = "withdrawtransaction";

    // Column names seperated by table
    private static String KEY_ID="id";

    private static String KEY_username = "username";
    private static String KEY_pass = "pass";
    private static String KEY_name = "name";

    private static String KEY_fullname = "fullname";
    private static String KEY_displayname = "displayname";
    private static String KEY_balance = "balance";
    private static String KEY_interestrate = "interestrate";
    private static String KEY_user_id = "user_id";

    private static String KEY_amount = "amount";
    private static String KEY_realtime = "realtime";
    private static String KEY_usertime = "usertime";
    private static String KEY_account_id = "account_id";

    private static String KEY_transaction_id = "transaction_id";

    private static String KEY_moneysource = "moneysource";

    private static String KEY_withdrawreason = "withdrawreason";
    private static String KEY_category = "category";

    // Table Create Statements
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_user + "(" + KEY_ID + " INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT," + KEY_username
            + " VARCHAR(255)," + KEY_pass + " VARCHAR(255)," + KEY_name
            + " VARCHAR(255)" + ")";

    private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_account
            + "(" + KEY_ID + " INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT," + KEY_fullname + " TEXT," + KEY_displayname + "VARCHAR(255),"
            + KEY_balance + " INTEGER," + KEY_interestrate  + "FLOAT,"  + KEY_user_id + "INTEGER" +  ")";

    private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE "
            + TABLE_transaction + "(" + KEY_ID + " INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,"
            + KEY_amount + " FLOAT," + KEY_realtime + " DATETIME,"
            + KEY_usertime + " DATETIME," + KEY_account_id +"INTEGER" + ")";

    private static final String CREATE_TABLE_DEPOSITTRANSACTION = "CREATE TABLE "
            + TABLE_deposittransaction + "(" + KEY_ID
            + " INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, " + KEY_moneysource
            + " VARCHAR(255)," + KEY_transaction_id + "INTEGER" + ")";

    private static final String CREATE_TABLE_WITHDRAWTRANSACTION = "CREATE TABLE "
            + TABLE_withdrawtransaction + "(" + KEY_ID
            + " INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, " + KEY_withdrawreason
            + " VARCHAR(255)," + KEY_category + "VARCHAR(255)" + KEY_transaction_id + "INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_USER);
        database.execSQL(CREATE_TABLE_ACCOUNT);
        database.execSQL(CREATE_TABLE_TRANSACTION);
        database.execSQL(CREATE_TABLE_DEPOSITTRANSACTION);
        database.execSQL(CREATE_TABLE_WITHDRAWTRANSACTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_deposittransaction);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_withdrawtransaction);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_transaction);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_account);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_user);


        // create new tables
        onCreate(db);
    }
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    //User CRUD  methods

    public long createUser(User user){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        //ids to be created at insert or by account class? db.insert creates an id?
        //values.put(KEY_ID,user.getId());
        values.put(KEY_username, user.getUserName());
        values.put(KEY_pass,user.getPass());
        values.put(KEY_name,user.getName());


        return db.insert(TABLE_account,null,values);
    }

    public User getUser(int user_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM" + TABLE_user
                +"WHERE" + KEY_ID +" = " + user_id;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        User user = new User();

        user.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        user.setUserName(c.getString(c.getColumnIndex(KEY_username)));
        user.setName(c.getString(c.getColumnIndex(KEY_name)));
        user.setPass(c.getString(c.getColumnIndex(KEY_pass)));

        return  user;
    }

    public void deleteUser(int id){

    }

    //Account CRUD methods
    public long createAccount(int user_id, Account account){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        //values.put(KEY_ID,account.getId());
        values.put(KEY_fullname,account.getFullName());
        values.put(KEY_displayname,account.getDisplayName());
        values.put(KEY_balance,account.getBalance());
        values.put(KEY_interestrate,account.getInterestRate());
        values.put(KEY_user_id,user_id);

        return db.insert(TABLE_account,null,values);
    }

    public Account getAccount(int account_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM" + TABLE_account
                +"WHERE" + KEY_ID +" = " + account_id;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Account account = new Account();

        account.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        account.setFullName(c.getString(c.getColumnIndex(KEY_fullname)));
        account.setDisplayName(c.getString(c.getColumnIndex(KEY_displayname)));
        account.setBalance(c.getDouble(c.getColumnIndex(KEY_balance)));
        account.setInterestRate(c.getDouble(c.getColumnIndex(KEY_interestrate)));

        return  account;
    }

    public ArrayList<Account> getAccountList(int user_id){
        ArrayList<Account> accounts = new ArrayList<Account>();
        String selectQuery = "SELECT * FROM" + TABLE_account +
                "WHERE" + KEY_user_id +"=" + user_id + ";";
        Log.e(LOG,selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst()){
            do {
                Account account = new Account();

                account.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                account.setFullName(c.getString(c.getColumnIndex(KEY_fullname)));
                account.setDisplayName(c.getString(c.getColumnIndex(KEY_displayname)));
                account.setBalance(c.getDouble(c.getColumnIndex(KEY_balance)));
                account.setInterestRate(c.getDouble(c.getColumnIndex(KEY_interestrate)));

                accounts.add(account);
            }while(c.moveToNext());
        }
        return accounts;
    }

    public void deleteAccount(int account_id){

    }

    //Transaction CRUD methods

    public long createTransaction(int account_id,Transaction transaction){
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues values = new ContentValues();

        values.put(KEY_amount,transaction.getAmount());
        values.put(KEY_realtime,dateFormat.format(transaction.getRealTime()));
        values.put(KEY_usertime,dateFormat.format(transaction.getUserTime()));
        values.put(KEY_account_id,account_id);

        long transaction_id = db.insert(TABLE_transaction,null,values);

        if(transaction instanceof DepositTransaction){
            values = new ContentValues();

            values.put(KEY_moneysource,((DepositTransaction) transaction).getMoneySource());
            values.put(KEY_transaction_id,transaction_id);

            return db.insert(TABLE_deposittransaction,null,values);

        }else if(transaction instanceof WithdrawTransaction){

            values = new ContentValues();

            values.put(KEY_withdrawreason,((WithdrawTransaction) transaction).getWithdrawReason());
            values.put(KEY_category,((WithdrawTransaction) transaction).getCategory());
            values.put(KEY_transaction_id,transaction_id);

            return db.insert(TABLE_withdrawtransaction,null,values);

        }
        return -1;
    }

    public Transaction getTransaction(Transaction transaction) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery;
        Cursor c;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        if(transaction instanceof DepositTransaction){

            selectQuery = "SELECT * FROM" + TABLE_deposittransaction
                    +"WHERE" + KEY_transaction_id +" = " + transaction.getId();  //maybe need key_transaction id
            Log.e(LOG, selectQuery);

            c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();

            int id = c.getInt(c.getColumnIndex(KEY_ID));
            Date realTime = dateFormat.parse(c.getString(c.getColumnIndex(KEY_realtime)));
            Date userTime = dateFormat.parse(c.getString(c.getColumnIndex(KEY_usertime)));
            double amount = c.getDouble(c.getColumnIndex(KEY_amount));
            String moneySource = c.getString(c.getColumnIndex(KEY_moneysource));

            Transaction returnTransaction = new DepositTransaction(realTime,userTime,amount,moneySource);
            returnTransaction.setId(id);

            return  returnTransaction;

        }else if(transaction instanceof  WithdrawTransaction){
            selectQuery = "SELECT * FROM" + TABLE_withdrawtransaction
                    +"WHERE" + KEY_transaction_id +" = " + transaction.getId();  //maybe need key_transaction id
            Log.e(LOG, selectQuery);

            c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();

            int id = c.getInt(c.getColumnIndex(KEY_ID));
            Date realTime = dateFormat.parse(c.getString(c.getColumnIndex(KEY_realtime)));
            Date userTime = dateFormat.parse(c.getString(c.getColumnIndex(KEY_usertime)));
            double amount = c.getDouble(c.getColumnIndex(KEY_amount));
            String withdrawReason = c.getString(c.getColumnIndex(KEY_withdrawreason));
            String category = c.getString(c.getColumnIndex(KEY_category));

            Transaction returnTransaction = new WithdrawTransaction(realTime,userTime,amount,withdrawReason,category);
            returnTransaction.setId(id);

            return  returnTransaction;

        }
        return null;
    }

    //not needed for current feature, more thought needed into transaction structure
    public ArrayList<Transaction> getTransactionList(int account_id){
        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        String selectQuery = "SELECT * FROM" + TABLE_transaction +
                "WHERE" + KEY_account_id +"=" + account_id + ";";
        Log.e(LOG,selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        Cursor c1,c2;
        String depositQuery,withdrawQuery;

        if(c.moveToFirst()){
            do {

            }while(c.moveToNext());
        }
        return null;
    }
}