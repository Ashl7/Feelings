package com.ashl7developer.autism.feelings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Jeff To 4/17/2016
 * Modified by Arash Nase 1/8/2017
 * Child fragment of EmojiGridFragment, in which the user chooses the emoji to send
 * TODO: can you move sending sms to the background? Is it better?
 * TODO: when there is no contact, send button does not work at all!
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
    private Button sendButton;
    private ListView contactsListView;

    private ArrayList<Contact> contactList;

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

        sendButton = (Button)view.findViewById(R.id.send_button);
        emojiName = (TextView)view.findViewById(R.id.emojy_name_textview);
        emojiPicture = (ImageView)view.findViewById(R.id.emoji_image_view);
        contactsListView = (ListView)view.findViewById(R.id.contacts_listview);

        emojiName.setText(mParamName);
        emojiPicture.setImageResource(mParamEmoji);
        contactList = getContactsFromDatabase();
        ArrayContactAdapter cAdapter =
                new ArrayContactAdapter(getContext(), android.R.layout.simple_list_item_1, contactList);
        contactsListView.setAdapter(cAdapter);

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                for(Contact contact: contactList)
                    sendSms(contact.getNumber());
                Toast.makeText(getContext(), "Sent", Toast.LENGTH_LONG).show(); //sent shown once
            }
        });

        return view;
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
        ArrayList<Contact> cl = db.getCanSendContacts();
        db.close();
        return cl;
    }

    // Function that runs when "Send" is pressed
    void sendSms(String number) {
        String smsMessage = mParamCaption;
        // Check if app has permission to send an SMS
        if(SmsHelper.hasSmsPermission(getContext())) {
            SmsHelper.sendSMS(number, smsMessage, getContext(), null);
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
