package com.autism.mindpower.feeling;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by David Nguyen on 4/20/2016.
 */
public class PinSettingActivity extends AppCompatActivity{

    EditText passwordView;
    public static final String Default = "N/A";

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_setting);
        passwordView = (EditText) findViewById(R.id.Pin);
    }

    public void enter(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("pinNumber", Context.MODE_PRIVATE);
        String userPin = sharedPreferences.getString("password", Default);
        if (userPin.equals(passwordView.getText().toString())) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "No the right pin number", Toast.LENGTH_SHORT).show();
        }
    }
}
