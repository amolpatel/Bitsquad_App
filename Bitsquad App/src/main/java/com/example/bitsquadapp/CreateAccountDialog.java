package com.example.bitsquadapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Created by Kurt on 2/24/14.
 */
public class CreateAccountDialog extends DialogFragment {
    public interface CreateAccountListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    CreateAccountListener dialogListener;
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.create_account_dialog, null))
                .setTitle("Create Account")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogListener.onDialogPositiveClick(CreateAccountDialog.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogListener.onDialogNegativeClick(CreateAccountDialog.this);
                    }
                });
        return builder.create();
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            dialogListener = (CreateAccountListener) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "implement NoticeDialogListener");
        }
    }

}
