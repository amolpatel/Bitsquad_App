package com.example.bitsquadapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Kurt on 3/3/14.
 */
public class RegistrationScreen extends Activity {
    private EditText nameInput, userNameInput, passwordInput, confirmPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);

        nameInput = (EditText) findViewById(R.id.NewName);
        userNameInput = (EditText) findViewById(R.id.NewUsername);
        passwordInput = (EditText) findViewById(R.id.NewPassword);
        confirmPasswordInput = (EditText) findViewById(R.id.ConfirmPassword);
    }

    public void registerUser(View view){
        String newUsername   = userNameInput.getText().toString();
        String newName = nameInput.getText().toString();
        String newPassword = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();


        Context context = this.getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text;
        if(newUsername.equals("") || newName.equals("") || newPassword.equals("") ||
                confirmPassword.equals("")){
            text = "Please fill out all fields";
            Toast toast = Toast.makeText(context,text,duration);
            toast.show();
        }
        else if(!confirmPassword.equals(newPassword)){
            text = "Passwords don't match";
            Toast toast = Toast.makeText(context,text,duration);
            toast.show();
        }
        else if(!((MyApplication) getApplication()).addUser(newName,newPassword,newUsername)){
            text = "Username already taken";
            Toast toast = Toast.makeText(context,text,duration);
            toast.show();
        }
        else{
            text = "Registration Successful";
            Toast toast = Toast.makeText(context,text,duration);
            toast.show();
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntent);
            this.finish();
        }
    }
}
