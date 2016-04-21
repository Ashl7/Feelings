package com.autism.mindpower.feeling;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
    implements SendTextFragment.OnFragmentInteractionListener {

    public static final int REQUEST_CONTACTS = 0;
    public static final int REQUEST_SMS = 1;

    private Button contactActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactActivityButton = (Button) findViewById(R.id.contact_activity_caller);
        contactActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
                startActivity(intent);
            }
        });


        SmsHelper.checkAndRequestSmsPermission(this, REQUEST_SMS);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit_contacts) {
            showContactsActivity(null);
        }
        else if (id == R.id.show_text_dialog) {
            showSendTextDialog(null);
        }

        return super.onOptionsItemSelected(item);
    }

    void showSendTextDialog(View v) {
        // Create the fragment and show it as a dialog
        FragmentManager fm = getSupportFragmentManager();
        // TODO specify what will get sent to the Fragment
        DialogFragment newFragment = SendTextFragment.newInstance(R.drawable.emoji_laugh, "Emotion", "I'm feeling an emotion. What is it?");
        newFragment.show(fm, null);
    }

    void showContactsActivity(View v) {
        // Create the fragment and show it as a dialog
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_SMS) {
            // Received permission result for SMS
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // SMS permission has been granted
                Toast.makeText(getApplicationContext(), "SMS permission granted",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "SMS permission NOT granted. Turn it on in Settings -> Apps",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
