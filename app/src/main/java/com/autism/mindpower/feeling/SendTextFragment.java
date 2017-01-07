package com.autism.mindpower.feeling;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SendTextFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SendTextFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Created by Arash Nase
 */
public class SendTextFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_EMJOI = "paramEmoji";
    private static final String ARG_PARAM_NAME = "paramName";
    private static final String ARG_PARAM_CAPTION = "paramCaption";

    // TODO: Rename and change types of parameters
    private int mParamEmoji;
    private String mParamName;
    private String mParamCaption;

    private OnFragmentInteractionListener mListener;

    private TextView emojiName;
    private ImageView emojiPicture;
    private EditText toPhoneNumberET;
    private ListView contactsListView;

    private ArrayList<Contact> cl;

    public SendTextFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param paramEmoji Path to resource.
     * @param paramName Name.
     * @param paramCaption Text associated with emoji to send as SMS.
     * @return A new instance of fragment SendTextFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SendTextFragment newInstance(int paramEmoji, String paramName, String paramCaption) {
        SendTextFragment fragment = new SendTextFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_EMJOI, paramEmoji);
        args.putString(ARG_PARAM_NAME, paramName);
        args.putString(ARG_PARAM_CAPTION, paramCaption);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamEmoji = getArguments().getInt(ARG_PARAM_EMJOI);
            mParamName = getArguments().getString(ARG_PARAM_NAME);
            mParamCaption = getArguments().getString(ARG_PARAM_CAPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_text, container, false);

        // Populate the emoji picture and the emotion from the params
        emojiName = (TextView)view.findViewById(R.id.emojy_name_textview);
        emojiName.setText(mParamName);
        emojiPicture = (ImageView)view.findViewById(R.id.emoji_image_view);
        emojiPicture.setImageResource(mParamEmoji);

        // Assign text fields to variables so we can get them later
        //toPhoneNumberET = (EditText)view.findViewById(R.id.to_phone_number_et);/////////////////////////////////////////////
        Button sendButton = (Button)view.findViewById(R.id.send_sms_button);

        cl = getContactsFromDatabase();
        ArrayContactAdapter cAdapter =
                new ArrayContactAdapter(getContext(), android.R.layout.simple_list_item_1, cl);
        contactsListView = (ListView)view.findViewById(R.id.lvContacts);
        contactsListView.setAdapter(cAdapter);
        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact c = (Contact)parent.getItemAtPosition(position);
                sendSms(c.getNumber());
            }
        });

        return view;
    }

    public void onSendClick(View view) {
        String toPhoneNumber = toPhoneNumberET.getText().toString();
        sendSms(toPhoneNumber);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    ArrayList<Contact> getContactsFromDatabase() {
        ContactDatabase db = new ContactDatabase(getContext());
        db.open();
        ArrayList<Contact> cl = db.getContacts();
        db.close();
        return cl;
    }

    // Function that runs when "Send" is pressed
    void sendSms(String number) {
        String smsMessage = mParamCaption;
        // Check if app has permission to send an SMS
        if(SmsHelper.hasSmsPermission(getContext())) {
            SmsHelper.sendSMS(number, smsMessage, getContext(), null);
            Toast.makeText(getContext(), "Sent",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(
                    getContext(),
                    "Unable to send text - turn on the SMS permission in Settings -> Apps",
                    Toast.LENGTH_LONG).show();
        }
        // Dismiss the fragment
        this.dismiss();
    }
}
