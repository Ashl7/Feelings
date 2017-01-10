package com.autism.mindpower.feeling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements SendTextFragment.OnFragmentInteractionListener {

    public static final int REQUEST_CONTACTS = 0;
    public static final int REQUEST_SMS = 1;

    private ArrayList<Emoji> emojiList;
    private GridView emojiGridView;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getSharedPreferences(getString(R.string.password), Context.MODE_PRIVATE);
        boolean passwordSet = sharedPref.getBoolean("passwordSet", false);
        // if pin is not set, go to the PasswordCreateActivity to set it up for the first up
        if (!passwordSet) {
            Intent intent = new Intent(getApplicationContext(), PasswordCreateActivity.class);
            startActivity(intent);
        }

        emojiList = Emoji.getEmojiList();
        ArrayEmojiAdapter eAdapter =
                new ArrayEmojiAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, emojiList);
        emojiGridView = (GridView) findViewById(R.id.gvEmoji);
        emojiGridView.setAdapter(eAdapter);
        emojiGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Emoji e = (Emoji)parent.getItemAtPosition(position);
                showSendTextDialog(e);
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
        if (id == R.id.action_setting) {
            checkPassowrd();
        }
        if (id == R.id.checklist) {
            Intent intent = new Intent(this, FragmentPager.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    void checkPassowrd() {
        Intent intent = new Intent(this, PasswordCheckActivity.class);
        startActivity(intent);
    }


    void showSendTextDialog(Emoji e) {
        // Create the fragment and show it as a dialog
        FragmentManager fm = getSupportFragmentManager();
        // TODO specify what will get sent to the Fragment
        DialogFragment newFragment = SendTextFragment.newInstance(e.getDrawableRes(), e.getName(), e.getCaption());
        newFragment.show(fm, null);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        // Don't delete, it's a method needed by OnFragmentInteractionListener
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
