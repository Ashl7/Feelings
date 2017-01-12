package com.ashl7developer.autism.feelings;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by Jeff To on 4/18/2016.
 */
public class SmsHelper {

    public static boolean hasSmsPermission(Context c) {
        return ContextCompat.checkSelfPermission(c, Manifest.permission.SEND_SMS) ==
                PackageManager.PERMISSION_GRANTED;
    }

    public static void checkAndRequestSmsPermission(Activity a, int permissionIdCode) {
        Context c = a.getApplicationContext();
        if (!hasSmsPermission(c)) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(a, Manifest.permission.SEND_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(c, "App needs the SMS permission. Turn it on in Settings -> Apps",
                        Toast.LENGTH_LONG).show();
            }
            else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(a, new String[]{Manifest.permission.SEND_SMS}, permissionIdCode);
            }
        }
    }

    public static void sendSMS(String toPhoneNumber, String smsMessage, Context c,
                           PendingIntent smsSendStatus) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, smsSendStatus, null);
        } catch (IllegalArgumentException e) {
            // Phone number or message fields were blank
            Toast.makeText(c, "Unable to send text - check the phone number and message to make sure it's correct",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (Exception e) {
            // Something else happened e.g. app does not have permission to send an SMS
            Toast.makeText(c, "Unable to send text - something unknown happened",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
