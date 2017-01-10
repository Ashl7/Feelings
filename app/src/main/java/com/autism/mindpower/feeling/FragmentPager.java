package com.autism.mindpower.feeling;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;



/**
 * A simple {@link Fragment} subclass.
 * Created by Arash Nase on 1/9/2017
 */
public class FragmentPager extends FragmentActivity {

    static final int NUM_ITEMS = 2;

    PagerAdapter adapter;
    ViewPager pager;
    SlidingTabLayout tabs;



    public FragmentPager() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);

        adapter = new PagerAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        tabs.setViewPager(pager);
    }
}


