package com.example.bitsquadapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


import Model.Account;
import Model.DepositTransaction;
import Model.Transaction;
import Model.WithdrawTransaction;

/**
 * Created by Kurt on 3/4/14.
 */
public class CreateTransactionFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    private Spinner transactionType, transactionSource;
    private ArrayAdapter sources, types;
    private EditText transactionAmount, withdrawalReason;
    private DatePicker datePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_transaction_screen, container, false);

        datePicker = (DatePicker) view.findViewById(R.id.DatePicker);
        datePicker.setMaxDate(System.currentTimeMillis());
        transactionSource = (Spinner) view.findViewById(R.id.SourceSpinner);
        transactionType = (Spinner) view.findViewById(R.id.TypeSpinner);
        transactionAmount = (EditText) view.findViewById(R.id.NewTransactionAmount);
        withdrawalReason = (EditText) view.findViewById(R.id.WithdrawalReason);

        ((Button) view.findViewById(R.id.CreateTransaction)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.CancelNewTransaction)).setOnClickListener(this);

        ArrayList<String> sourceStrings = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.deposit_types)));
        sources = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_style, sourceStrings);
        sources.setDropDownViewResource(R.layout.selectable_list_item);

        transactionSource.setAdapter(sources);

        types = ArrayAdapter.createFromResource(getActivity(), R.array.transaction_types, R.layout.spinner_style);
        types.setDropDownViewResource(R.layout.selectable_list_item);
        transactionType.setAdapter(types);

        transactionType.setOnItemSelectedListener(this);
        return view;
    }

    public void cancelTransaction(View view){
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void createTransaction(View view){
        String type = transactionType.getSelectedItem().toString();
        String source = transactionSource.getSelectedItem().toString();
        String amount = transactionAmount.getText().toString();
        String reason = withdrawalReason.getText().toString();


        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text;
        double amt;
        if(amount.equals("") || (type.equals("Withdrawal") && reason.equals(""))){
            text = "Please fill out all fields";
        }
        else {
            try {
                amt = Double.parseDouble(amount);
                Date currentDate = new Date(System.currentTimeMillis());
                Date userDate = new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                if(type.equals("Deposit")){
                    ((MyApplication) getActivity().getApplication()).createTransaction(currentDate,userDate,amt,source);
                }
                else {
                    ((MyApplication) getActivity().getApplication()).createTransaction(currentDate, userDate, amt, source, reason);
                }
                ((BaseContentActivity) getActivity()).notifySetChange();
                text = "Transaction complete";
            }catch (Exception e){
                text = "Please enter correct number format";
            }
        }
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if(adapterView.getItemAtPosition(position).toString().equals("Deposit")){
            if(!(withdrawalReason.getVisibility() == View.GONE)){
                withdrawalReason.setVisibility(View.GONE);
                sources.clear();
                sources.addAll(getResources().getStringArray(R.array.deposit_types));
            }
        }
        else{
            if(withdrawalReason.getVisibility() == View.GONE){
                withdrawalReason.setVisibility(View.VISIBLE);
                sources.clear();
                sources.addAll(getResources().getStringArray(R.array.withdrawal_types));
            }
        }
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
        sources.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.CreateTransaction:
                this.createTransaction(view);
                break;
            case R.id.CancelNewAccount:
                this.cancelTransaction(view);
                break;
        }
    }
}
