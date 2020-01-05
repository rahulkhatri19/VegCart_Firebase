package `in`.example.rahul.vegcartpro.notifications

import `in`.example.rahul.vegcartpro.R
import `in`.example.rahul.vegcartpro.activity.MainActivity
import `in`.example.rahul.vegcartpro.activity.StartActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*

/**
 * Created by Rahul on 18-06-2018.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var notificationManager: NotificationManager? = null
    override fun onMessageReceived(remoteMessage: RemoteMessage) { //super.onMessageReceived(remoteMessage);
        var notificationIntent = Intent(this, StartActivity::class.java)
        if (StartActivity.isAppRunning) {
        }
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT)
        val notificationId = Random().nextInt(6000)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //Setting up Notification channels for android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels()
        }
        notificationIntent = if (StartActivity.isAppRunning) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, StartActivity::class.java)
        }
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_action_home) //a resource for your custom small icon
                .setContentTitle(remoteMessage.data["title"]) //the "title" value you sent in your notification
                .setContentText(remoteMessage.data["message"]) //ditto
                .setAutoCancel(true) //dismisses the notification on click
                .setSound(defaultSoundUri).setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupChannels() {
        val adminChannelName: CharSequence = getString(R.string.notifications_admin_channel_name)
        val adminChannelDescription = getString(R.string.notifications_admin_channel_description)
        val adminChannel: NotificationChannel
        adminChannel = NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW)
        adminChannel.description = adminChannelDescription
        adminChannel.enableLights(true)
        adminChannel.lightColor = Color.RED
        adminChannel.enableVibration(true)
        if (notificationManager != null) {
            notificationManager!!.createNotificationChannel(adminChannel)
        }
    }

    companion object {
        private const val NOTIFICATION_ID_EXTRA = "AIzaSyB2ANrAGzB08rzZk48G1PbSnqpi27qNOcE"
        private const val IMAGE_URL_EXTRA = "imageUrl"
        private const val ADMIN_CHANNEL_ID = "28915234825"
    }
}