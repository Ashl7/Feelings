package com.autism.mindpower.feeling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by David Nguyen on 4/21/2016.
 *
 */


public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void editContacts (View view) {
        // Go to add contacts activity
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    public void changePassword(View view) {
        // Go to pin activity
        Intent intent = new Intent(this, PasswordCreateActivity.class);
        startActivity(intent);
    }

    public void goHome(View view) {
        //Return to home page
        finish();
    }
}
