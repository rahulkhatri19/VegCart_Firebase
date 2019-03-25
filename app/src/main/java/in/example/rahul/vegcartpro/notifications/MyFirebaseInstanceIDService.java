package in.example.rahul.vegcartpro.notifications;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Rahul on 18-06-2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
   @Override
    public void onTokenRefresh(){
       String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       Log.d(TAG, "Refreshed token: " + refreshedToken);
       // If you want to send messages to this application instance or
       // manage this apps subscriptions on the server side, send the
       // Instance ID token to your app server.
       SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       preferences.edit().putString(Constants.FIREBASE_TOKEN, refreshedToken).apply();
   }
}
