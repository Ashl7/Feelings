package com.ashl7developer.autism.feelings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivityPager extends AppCompatActivity implements
        SendTextFragment.OnFragmentInteractionListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pager);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.password),
                Context.MODE_PRIVATE);
        boolean passwordSet = sharedPref.getBoolean("passwordSet", false);
        // if pin is not set, go to the PasswordCreateActivity to set it up for the first up
        if (!passwordSet) {
            Intent intent = new Intent(getApplicationContext(), PasswordCreateActivity.class);
            startActivity(intent);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapterMainActivity(getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
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

