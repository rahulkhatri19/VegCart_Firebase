package in.example.rahul.vegcartpro.utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    String refreshedToken="";
    private static final String TAG= MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        refreshedToken= FirebaseInstanceId.getInstance().getToken();

        // saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to our server
        sendRegistrationToServer(refreshedToken);
        Log.e(TAG,"Token: "+refreshedToken);
        // Notify UI that registration has completed, so the process indicator cab be hidden.

        Intent registrationComplete= new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }
    private void sendRegistrationToServer(final String token){
        // sending gcm token to server
        Log.e(TAG, "sendRegTokenToServer: "+token);
    }
    private void storeRegIdInPref(String token){
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }
}
