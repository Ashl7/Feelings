package com.ashl7developer.autism.feelings;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;

public class EmojiGridFragment extends Fragment
    implements SendTextFragment.OnFragmentInteractionListener {

    public static final int REQUEST_SMS = 1;

    private ArrayList<Emoji> emojiList;
    private GridView emojiGridView;


    public EmojiGridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grid_emoji, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        emojiList = Emoji.getEmojiList();
        ArrayEmojiAdapter eAdapter =
                new ArrayEmojiAdapter(getActivity(), android.R.layout.simple_list_item_1, emojiList);
        View view = getView();

        if(view != null) {
            emojiGridView = (GridView) view.findViewById(R.id.gvEmoji);
            emojiGridView.setAdapter(eAdapter);
            emojiGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Emoji e = (Emoji)parent.getItemAtPosition(position);
                    showSendTextDialog(e);
                }
            });
        }

        //SmsHelper.checkAndRequestSmsPermission(getActivity(), REQUEST_SMS);
    }

    // Opens up DialogFragment to send sms
    void showSendTextDialog(Emoji e) {
        // Create the fragment and show it as a dialog
        FragmentManager fm = getFragmentManager();
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
                Toast.makeText(getActivity().getApplicationContext(),
                        "SMS permission granted",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getActivity().getApplicationContext(),
                        "SMS permission NOT granted. Turn it on in Settings -> Apps",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
