package com.ashl7developer.autism.feelings;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Created by Arash Nase
 * First slide of welcome screen
 */
public class WelcomeSlide1Fragment extends Fragment {

    private TextView leftDot;
    private TextView rightDot;

    public WelcomeSlide1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome_slide1, container, false);

        leftDot = (TextView) v.findViewById(R.id.left_bullet);
        rightDot = (TextView) v.findViewById(R.id.right_bullet);

        leftDot.setText(Html.fromHtml("&#8226;"));  // makes a bullet
        leftDot.setTextSize(50);
        rightDot.setText(Html.fromHtml("&#8226;"));
        rightDot.setTextSize(50);
        return v;
    }

}
