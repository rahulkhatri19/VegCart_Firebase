package `in`.example.rahul.vegcartpro.activity

import `in`.example.rahul.vegcartpro.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class StartActivity: AppCompatActivity() {
    companion object{
        var isAppRunning = false
    }


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_start)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "1"
        val channel2 = "2"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId,
                    "Channel 1", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description = "This is BNT"
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(notificationChannel)
            val notificationChannel2 = NotificationChannel(channel2,
                    "Channel 2", NotificationManager.IMPORTANCE_MIN)
            notificationChannel.description = "This is bTV"
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(notificationChannel2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isAppRunning = false
    }
}