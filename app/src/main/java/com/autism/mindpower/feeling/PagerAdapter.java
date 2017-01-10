package com.autism.mindpower.feeling;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ASHL7 on 1/9/2017.
 * This adapter will keep track of which tab is currently selected
 * and loads or returns the right fragment.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_FRAGMENTS = 2;      // Number of tabs
    private static final String[] TAB_NAMES = {"Feelings", "Contacts"};  // Tab titles


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            SendTextFragment fragment1 = new SendTextFragment();
            return fragment1;
        }
        else {
            ContactChecklistFragment fragment2 = new ContactChecklistFragment();
            return fragment2;
        }
    }


    @Override
    public int getCount() {
        return NUM_FRAGMENTS;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_NAMES[position];
    }
}
