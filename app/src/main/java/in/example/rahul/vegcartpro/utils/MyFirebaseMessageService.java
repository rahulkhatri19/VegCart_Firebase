package in.example.rahul.vegcartpro.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;
import in.example.rahul.vegcartpro.activity.MainActivity;

public class MyFirebaseMessageService extends FirebaseMessagingService {
    private static final String TAG= MyFirebaseMessageService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       // super.onMessageReceived(remoteMessage);
        Log.e(TAG, "From: "+remoteMessage.getFrom());

        if (remoteMessage == null){
            return;
        }

        // Check if message contain a notification payload.

        if (remoteMessage.getNotification() != null){
            Log.e(TAG, "Notification Body: "+remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contain a data payload.
        if (remoteMessage.getData().size() > 0){
            Log.e(TAG, "Data Payload: "+ remoteMessage.getData().toString());
            try{
                JSONObject json= new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (JSONException e){
                Log.e(TAG, "Exception: "+ e.getMessage());
            }
        }
    }
    private void handleNotification(String message){
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())){
            // app is in foreground, broadcast the push message
            Intent pushNotification= new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils= new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            // If app is in background, firebase itself handle the notification
        }
    }
    private void handleDataMessage(JSONObject json){
        Log.e(TAG, "push json: "+ json.toString());

        try{
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            String payload = data.getString("payload");

            boolean isBackground= data.getBoolean("is_background");

            Log.e(TAG, "title: "+title);
            Log.e(TAG, "message: "+message);
            Log.e(TAG, "imageUrl: "+imageUrl);
            Log.e(TAG, "timestamp"+timestamp);
            Log.e(TAG, "payload: "+payload);
            Log.e(TAG, "isBackground"+isBackground);

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())){
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils= new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray

                Intent resultIntent= new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);
                // check for attachment
                if (TextUtils.isEmpty(imageUrl)){
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image

                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
            Log.e(TAG, "Exception: "+ e.getMessage());
        }
    }
    // Showing notification with text only

    private void showNotificationMessage(Context context, String tittle, String message, String timestamp, Intent intent){
        notificationUtils= new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(tittle, message, timestamp, intent);
    }
    // Showing notification with text and image

    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl){
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
