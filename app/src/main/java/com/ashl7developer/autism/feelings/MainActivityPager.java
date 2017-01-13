package com.ashl7developer.autism.feelings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivityPager extends AppCompatActivity implements
        SendTextFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivityPager.class.getName();
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pager);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapterMainActivity(getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.password),
                Context.MODE_PRIVATE);
        boolean isPasswordSet = sharedPref.getBoolean("passwordSet", false);
        boolean isFirstTime = sharedPref.getBoolean("firstTime", true);
        SharedPreferences.Editor editor = sharedPref.edit();

        Log.d(TAG, "in MainActivityPager");
        if(isFirstTime) {
            Log.d(TAG, "jumping to WelcomeScreenActivity");
            Intent intent = new Intent(getApplicationContext(), WelcomeScreenActivity.class);
            startActivity(intent);
        }
        /*
        // if first time using the app, launch welcome screen
        if (!isPasswordSet) {
            Log.d(TAG, "jumping to PasswordCreateActivity");
            Intent intent = new Intent(getApplicationContext(), PasswordCreateActivity.class);
            startActivity(intent);
            editor.putBoolean("passwordSet", true);
            editor.commit();
        }
        */
    }


    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_setting) {
            // user cannot change the setting unless he has the password
            Intent intent = new Intent(this, PasswordCheckActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

