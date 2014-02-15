package com.example.bitsquadapp;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registration_screen, menu);
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


    public void registerUser(View view){
        String newUsername   = ((EditText)findViewById(R.id.NewUsername)).getText().toString();
        String newName = ((EditText)findViewById(R.id.NewName)).getText().toString();
        String newPassword = ((EditText)findViewById(R.id.NewPassword)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.ConfirmPassword)).getText().toString();


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
            Intent loginScreenIntent = new Intent(this, LoginScreen.class);
            startActivity(loginScreenIntent);
        }


        /*
        if(newUsername.equals("") || newName.equals("") || newPassword.equals("")){
            text="Please fill out all fields";
            Toast toast = Toast.makeText(context,text,duration);
            toast.show();
        }
        else if(!newPassword.toString().equals(confirmPassword.toString())) {
            text = "Re-type Password";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            ((MyApplication) getApplication()).addUser(
                    newName.toString()
                    , newPassword.toString()
                    , newUsername.toString()
            );

            Intent loginScreenIntent = new Intent(this, LoginScreen.class);
            startActivity(loginScreenIntent);
        }*/
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_registration_screen, container, false);
            return rootView;
        }
    }

}
