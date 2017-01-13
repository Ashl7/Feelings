package com.ashl7developer.autism.feelings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by David Nguyen on 4/20/2016.
 * Background work to storage the pin number when first register
 *
 * Modified by Arash
 * Changed it in manifast so that no activity could go back to it by pressing back button
 */

public class PasswordCreateActivity extends AppCompatActivity {

    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        password = (EditText) findViewById(R.id.etPassword);
    }

    public void onSavePasswordClick(View view) {
        SharedPreferences sharedpreference = getSharedPreferences(
                getString(R.string.password), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreference.edit();
        editor.putString("password", password.getText().toString());
        editor.putBoolean("passwordSet", true);
        editor.commit();

        Toast.makeText(this, "Created New Password", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), ContactSelectActivity.class);
        startActivity(intent);
    }
}
