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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SendTextFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SendTextFragment#newInstance} factory method to
 * create an instance of this fragment.
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
        emojiName = (TextView)view.findViewById(R.id.tvWord);
        emojiName.setText(mParamName);
        emojiPicture = (ImageView)view.findViewById(R.id.ivEmotion);
        emojiPicture.setImageResource(mParamEmoji);

        // Assign text fields to variables so we can get them later
        toPhoneNumberET = (EditText)view.findViewById(R.id.to_phone_number_et);
        Button sendButton = (Button)view.findViewById(R.id.send_sms_btn);

        // Button needs to have an OnClickListener set here since this is a fragment
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
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

    // Function that runs when "Send" is pressed
    public void sendSms() {
        String toPhoneNumber = toPhoneNumberET.getText().toString();
        String smsMessage = mParamCaption;
        // Check if app has permission to send an SMS
        if(SmsHelper.hasSmsPermission(getContext())) {
            SmsHelper.sendSMS(toPhoneNumber, smsMessage, getContext(), null);
        }
        else {
            Toast.makeText(getContext(), "Unable to send text - turn on the SMS permission in Settings -> Apps",
                    Toast.LENGTH_LONG).show();
        }
        // Dismiss the fragment
        this.dismiss();
    }
}
