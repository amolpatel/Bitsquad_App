package com.example.bitsquadapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Kurt on 3/3/14.
 */
public class MainActivity extends Activity {
    private EditText userNameInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        userNameInput = (EditText) findViewById(R.id.Username);
        passwordInput = (EditText) findViewById(R.id.Password);
    }

    public void login(View view){
        String username = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();

        Context context = this.getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        String text = "";

        if(username.equals("") || password.equals("")){
            text = "Please fill out all fields";
        }
        else if(((MyApplication)getApplication()).userCheck(username, password)){
            text = "Login Successful";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Intent intent = new Intent(this, BaseContentActivity.class);
            startActivity(intent);
        }
        else{
            text = "Incorrect username or password";
        }
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void toRegistrationScreen(View view){
        Intent registrationScreenIntent = new Intent(this, RegistrationScreen.class);
        startActivity(registrationScreenIntent);
    }
}

