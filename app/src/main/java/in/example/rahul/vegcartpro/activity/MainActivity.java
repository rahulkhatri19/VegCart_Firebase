package in.example.rahul.vegcartpro.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import in.example.rahul.vegcartpro.R;
import in.example.rahul.vegcartpro.utils.Config;
import in.example.rahul.vegcartpro.utils.NotificationUtils;

public class MainActivity extends AppCompatActivity {

    TextView TvRegistrationId, TvMessage;
    private static final String TAG= MainActivity.class.getSimpleName();
    private BroadcastReceiver registrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TvRegistrationId= findViewById(R.id.tv_registration_id);
        TvMessage= findViewById(R.id.tv_push_message);

        registrationBroadcastReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)){
                    // gcm successfully registered
                    // now subscribe to global topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();
                }
                else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)){
                    // new push notification is received

                    String message= intent.getStringExtra("message");
                    Toast.makeText(context, "Push notification: "+message, Toast.LENGTH_SHORT).show();
                    TvMessage.setText(message);
                }
            }
        };
        displayFirebaseRegId();
    }
    // Fetches reg id from shared preferences
    // and displays on the screen

    private void displayFirebaseRegId(){
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId= pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: "+regId, null);

        if (!TextUtils.isEmpty(regId)){
            TvRegistrationId.setText("Firebase Reg Id: "+regId);
        } else {
            TvRegistrationId.setText("Firebase RegId is not received yet!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(registrationBroadcastReceiver, new IntentFilter((Config.REGISTRATION_COMPLETE)));

        // register new push receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(registrationBroadcastReceiver, new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened

        NotificationUtils.clearNotification(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(registrationBroadcastReceiver);
        super.onPause();
    }
    public void getToken(View view){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                      //  String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, token);
                        Toast.makeText(MainActivity.this, "Token: "+token, Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

}
