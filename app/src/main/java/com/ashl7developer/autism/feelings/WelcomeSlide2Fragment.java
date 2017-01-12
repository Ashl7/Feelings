package com.ashl7developer.autism.feelings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Created by Arash Nase
 * Second slide of welcome screen
 */
public class WelcomeSlide2Fragment extends Fragment {


    public WelcomeSlide2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_slide2, container, false);
    }

}
