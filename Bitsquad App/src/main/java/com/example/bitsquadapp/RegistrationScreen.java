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
        EditText newUsername   = (EditText)findViewById(R.id.NewUsername);
        EditText newName = (EditText)findViewById(R.id.NewName);
        EditText newPassword = (EditText)findViewById(R.id.NewPassword);
        EditText confirmPassword = (EditText)findViewById(R.id.ConfirmPassword);


        Context context = this.getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text;

        //
        if(!newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            text = "Re-type Password";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        if(newUsername == null || newName == null || newPassword == null){
            text="Please fill out all fields";
            Toast toast = Toast.makeText(context,text,duration);
            toast.show();
        }
        else{
            ((MyApplication) getApplication()).addUser(
                    newName.getText().toString()
                    , newPassword.getText().toString()
                    , newUsername.getText().toString()
            );

            Intent loginScreenIntent = new Intent(this, LoginScreen.class);
            startActivity(loginScreenIntent);
        }
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
