package com.example.bitsquadapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Model.Account;

/**
 * Created by Kurt on 3/5/14.
 */
public class CreateAccountFragment extends Fragment implements View.OnClickListener{
    private EditText name, display, interest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_account_screen, container, false);

        ((Button) view.findViewById(R.id.CreateAccountButton)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.CancelNewAccount)).setOnClickListener(this);

        name = (EditText) view.findViewById(R.id.NewAccountName);
        display = (EditText) view.findViewById(R.id.NewAccountDisplayName);
        interest = (EditText) view.findViewById(R.id.NewAccountInterest);

        return view;
    }

    public void createAccount(View view){
        //Get texts
        String newAccountName = name.getText().toString();
        String newDisplayName = display.getText().toString();
        String inter = interest.getText().toString();
        double newInterest;


        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text;
        if(newAccountName.equals("") || newDisplayName.equals("")){
            text = "Fields empty!";
        }
        else {
            if(inter.equals(""))
                inter = inter + "0";
            //Try to parse the strings
            try{
                Log.d("Interest",inter);
                newInterest = Double.parseDouble(inter);
                ((MyApplication) getActivity().getApplication()).createAccount(newAccountName, newDisplayName, 0, newInterest);
                ((BaseContentActivity) getActivity()).notifySetChange();
                text = "Account Created!";
            }catch (Exception e){
                //Catch NumberFormatErrors
                text = "Interest Failed!";
                e.printStackTrace();
            }
        }
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void cancelCreation(View view){
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.CreateAccountButton:
                this.createAccount(view);
                break;
            case R.id.CancelNewAccount:
                this.cancelCreation(view);
                break;
        }
    }
}
