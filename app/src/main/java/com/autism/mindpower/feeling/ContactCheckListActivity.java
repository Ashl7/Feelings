package com.autism.mindpower.feeling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContactCheckListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_check_list);

        ContactChecklistFragment fragment = (ContactChecklistFragment) getSupportFragmentManager().
                findFragmentById(R.id.fragment_checklist);

    }
}
