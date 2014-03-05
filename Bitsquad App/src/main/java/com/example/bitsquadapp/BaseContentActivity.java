package com.example.bitsquadapp;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

import Model.Account;

/**
 * Created by Kurt on 3/4/14.
 */
public class BaseContentActivity extends Activity {
    protected DrawerLayout drawerLayout;
    protected ListView listView;
    protected ActionBarDrawerToggle actionBarDrawerToggle;
    protected String closedDrawerTitle, openDrawerTitle;
    private final static String TAG_CREATE_ACCOUNT = "Create Account";
    private final static String TAG_CREATE_TRANSACTION = "Create Transaction";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openDrawerTitle = "Accounts";
        closedDrawerTitle = "Create Account";
        setContentView(R.layout.nav_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.left_drawer);

        View footer = getLayoutInflater().inflate(R.layout.create_account_footer, null);
        listView.addFooterView(footer);
        // Set the adapter for the list view
        listView.setAdapter(new AccountListAdapter(this, ((MyApplication) getApplication()).getAccounts()));
        // Set the list's click listener
        listView.setOnItemClickListener(new DrawerItemClickListener());

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(closedDrawerTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(openDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    public void notifySetChange(){
        ((AccountListAdapter) ((HeaderViewListAdapter)listView.getAdapter()).getWrappedAdapter()).notifyDataSetChanged();
    }


    /**
     * Custom ArrayAdapter for the drawer ListView to properly display
     */
    private class AccountListAdapter extends ArrayAdapter<Account>{
        private Context context;
        private List<Account> accounts;

        public AccountListAdapter(Context context, List<Account> objects){
            super(context, R.layout.account_item, objects);
            this.accounts = objects;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.account_item, parent, false);
            TextView displayName = (TextView) rowView.findViewById(R.id.ListDisplayName);
            TextView balance = (TextView) rowView.findViewById(R.id.ListDisplayBalance);

            displayName.setText(accounts.get(position).getDisplayName());
            double bal = accounts.get(position).getBalance();
            DecimalFormat format = new DecimalFormat("#0.00");
            balance.setText("$ " + format.format(bal));

            return rowView;
        }
    }

    /**
     * ItemClickListener for the ListView
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            FragmentManager fragmentManager =  getFragmentManager();
            CreateTransactionFragment tFrag =
                    (CreateTransactionFragment) fragmentManager.findFragmentByTag(TAG_CREATE_TRANSACTION);
            CreateAccountFragment aFrag =
                    (CreateAccountFragment) fragmentManager.findFragmentByTag(TAG_CREATE_ACCOUNT);
            //If footer (create account)
            if(position == (parent.getCount() - 1)){
                if(aFrag == null){
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, new CreateAccountFragment(), TAG_CREATE_ACCOUNT)
                            .commit();
                }
                else if(!aFrag.isVisible()){
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, aFrag, TAG_CREATE_ACCOUNT)
                            .commit();
                }
                closedDrawerTitle = "Create Account";
                Toast toast = Toast.makeText(getApplicationContext(), "Create Account", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                ((MyApplication) getApplication()).setCurrentAccount((Account)parent.getItemAtPosition(position));
                if(tFrag == null){
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, new CreateTransactionFragment(), TAG_CREATE_TRANSACTION)
                            .commit();
                }
                else if(!tFrag.isVisible()){
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, tFrag, TAG_CREATE_TRANSACTION)
                            .commit();
                }
                closedDrawerTitle = ((MyApplication) getApplication()).getCurrentAccount().getDisplayName();
            }
            drawerLayout.closeDrawer(listView);
        }
    }
}
