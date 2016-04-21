package com.autism.mindpower.feeling;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by David Nguyen on 4/20/2016.
 */
public class PinActivity extends AppCompatActivity {
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        password = (EditText) findViewById(R.id.etPassword);
    }

    public void register(View view) {
        SharedPreferences sharedpreference = getSharedPreferences("pin#", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreference.edit();
        editor.putString("password", password.getText().toString());

        editor.commit();
    }
}
