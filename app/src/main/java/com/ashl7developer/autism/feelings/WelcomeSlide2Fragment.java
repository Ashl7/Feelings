package com.ashl7developer.autism.feelings;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Created by Arash Nase
 * Second slide of welcome screen
 */
public class WelcomeSlide2Fragment extends Fragment {

    private TextView leftDot;
    private TextView rightDot;
    private Button startButton;


    public WelcomeSlide2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome_slide2, container, false);
        leftDot = (TextView) v.findViewById(R.id.left_bullet);
        rightDot = (TextView) v.findViewById(R.id.right_bullet);
        startButton = (Button) v.findViewById(R.id.start_app_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PasswordCreateActivity.class);
                startActivity(intent);
            }
        });

        leftDot.setText(Html.fromHtml("&#8226;"));  // makes a bullet
        leftDot.setTextSize(50);
        rightDot.setText(Html.fromHtml("&#8226;"));
        rightDot.setTextSize(50);
        return v;
    }

}
