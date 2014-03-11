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
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String LOG = "DatabaseHelper";

    private static final String DATABASE_NAME = "bitsqaudDB.db";
    private static final int DATABASE_VERSION = 1;

    //Table names
    public static final String TABLE_user = "user";
    public static final String TABLE_account = "account";
    public static final String TABLE_transaction = "_transaction";
    public static final String TABLE_deposittransaction = "deposittransaction";
    public static final String TABLE_withdrawtransaction = "withdrawtransaction";

    // Column names seperated by table
    public static String KEY_ID = "_id";

    public static String KEY_username = "username";
    public static String KEY_pass = "pass";
    public static String KEY_name = "name";

    public static String KEY_fullname = "fullname";
    public static String KEY_displayname = "displayname";
    public static String KEY_balance = "balance";
    public static String KEY_interestrate = "interestrate";
    public static String KEY_user_id = "user_id";

    public static String KEY_amount = "amount";
    public static String KEY_realtime = "realtime";
    public static String KEY_usertime = "usertime";
    public static String KEY_account_id = "account_id";

    public static String KEY_transaction_id = "transaction_id";

    public static String KEY_moneysource = "moneysource";

    public static String KEY_withdrawreason = "withdrawreason";
    public static String KEY_category = "category";

    // Table Create Statements
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_user + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_username + " TEXT, "
            + KEY_pass + " TEXT, "
            + KEY_name + " TEXT"
            + ");";

    private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE "
            + TABLE_account + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_fullname + " TEXT, "
            + KEY_displayname + " TEXT, "
            + KEY_balance + " INTEGER, "
            + KEY_interestrate + " REAL, "
            + KEY_user_id + " INTEGER"
            +  ");";

    private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE "
            + TABLE_transaction + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_amount + " REAL, "
            + KEY_realtime + " INTEGER, "
            + KEY_usertime + " INTEGER, "
            + KEY_account_id +" INTEGER"
            + ");";

    private static final String CREATE_TABLE_DEPOSITTRANSACTION = "CREATE TABLE "
            + TABLE_deposittransaction + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_moneysource + " TEXT, "
            + KEY_transaction_id + " INTEGER"
            + ");";

    private static final String CREATE_TABLE_WITHDRAWTRANSACTION = "CREATE TABLE "
            + TABLE_withdrawtransaction + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_withdrawreason + " TEXT, "
            + KEY_category + " TEXT, "
            + KEY_transaction_id + " INTEGER"
            + ");";

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

    public void close() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}