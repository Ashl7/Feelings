package com.ashl7developer.autism.feelings;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Arash Nase on 1/11/2017.
 * Pager Adapter for Welcome Screen
 */
public class PagerAdapterWelcomeScreen extends FragmentPagerAdapter {

    private static final int NUM_FRAGMENTS = 2;      // Number of pages


    public PagerAdapterWelcomeScreen(FragmentManager fm) {
        super(fm);
    }


    // Indicates which fragment will be called when viewpager is in a certain position
    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new WelcomeSlide1Fragment();
        else
            return new WelcomeSlide2Fragment();
    }


    @Override
    public int getCount() {
        return NUM_FRAGMENTS;
    }

}

