package com.example.bitsquadapp;

import Model.Account;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Model.Account;


public class AccountsList extends ListActivity implements CreateAccountDialog.CreateAccountListener{

    private ArrayAdapter<Account> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Account> accounts = ((MyApplication) getApplication()).getAccounts();
        View footer = getLayoutInflater().inflate(R.layout.footer_layout, null);
        ListView  listView = getListView();
        listView.addFooterView(footer);
        adapter = new ArrayAdapter<Account>(this, R.layout.accountlist_item, R.id.label, accounts);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.accounts_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toCreateAccount(View view){
        DialogFragment dialogFragment = new CreateAccountDialog();
        dialogFragment.show(this.getFragmentManager(), "Create Account");
    }

    public void onDialogPositiveClick(DialogFragment dialogFragment){
        //Get EditTexts from the dialog
        EditText name = (EditText)dialogFragment.getDialog().findViewById(R.id.NewAccountName);
        EditText display = (EditText)dialogFragment.getDialog().findViewById(R.id.NewAccountDisplayName);
        EditText interest = (EditText)dialogFragment.getDialog().findViewById(R.id.NewAcountInterest);
        EditText balance = (EditText)dialogFragment.getDialog().findViewById(R.id.NewAccountBalance);

        //Get texts from dialog
        String newAccountName = name.getText().toString();
        String newDisplayName = display.getText().toString();
        String inter = interest.getText().toString();
        String bal = balance.getText().toString();
        double newInterest, newBalance;


        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text;
        if(newAccountName.equals("") || newDisplayName.equals("")){
            text = "Account Creation Failed!";
        }
        else {
            //Set strings to "0" if empty
            if(bal.equals(""))
               bal = bal + "0";
            if(inter.equals(""))
                inter = inter + "0";
            //Try to parse the strings
            try{
                newInterest = Double.parseDouble(inter);
                newBalance = Double.parseDouble(bal);
                text = "Account Created!";
                ((MyApplication) getApplication()).createAccount(newAccountName, newDisplayName, newBalance, newInterest);
                adapter.notifyDataSetChanged();
            }catch (Exception e){
                //Catch NumberFormatErrors
                text = "Account Creation Failed!";
            }
        }
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text = "Account Creation Cancelled";
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
