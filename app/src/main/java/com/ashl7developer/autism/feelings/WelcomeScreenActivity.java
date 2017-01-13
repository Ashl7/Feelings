package com.ashl7developer.autism.feelings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

/*
* Created by Arash Nase 1/11/2017
* Welcome screen that comes up the first time app is launched
* TODO: fix orientations
*/
public class WelcomeScreenActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.password),
                Context.MODE_PRIVATE);
        boolean firstTimeUse = sharedPreferences.getBoolean("firstTime", true);
        if(!firstTimeUse) {
            Intent intent = new Intent(getApplicationContext(), MainActivityPager.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // make notification bar transparent
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        // hide the action bar
        getSupportActionBar().hide();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapterWelcomeScreen(getSupportFragmentManager()));
    }
}
