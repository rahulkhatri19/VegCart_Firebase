package `in`.example.rahul.vegcartpro.activity

import `in`.example.rahul.vegcartpro.R
import `in`.example.rahul.vegcartpro.utils.Config
import `in`.example.rahul.vegcartpro.utils.NotificationUtils
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    lateinit var tvRegistrationId: TextView
    lateinit var tvMessage: TextView
    private val TAG = MainActivity::class.java.simpleName
    private var registrationBroadcastReceiver: BroadcastReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvRegistrationId = findViewById(R.id.tv_registration_id)
        tvMessage = findViewById(R.id.tv_push_message)
        registrationBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) { // checking for type filter
                if (intent.action == Config.REGISTRATION_COMPLETE) { // gcm successfully registered
// now subscribe to global topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL)
                    displayFirebaseRegId()
                } else if (intent.action == Config.PUSH_NOTIFICATION) { // new push notification is received
                    val message = intent.getStringExtra("message")
                    Toast.makeText(context, "Push notification: $message", Toast.LENGTH_SHORT).show()
                    tvMessage.setText(message)
                }
            }
        }
        displayFirebaseRegId()
    }
    // Fetches reg id from shared preferences
    // and displays on the screen

    // Fetches reg id from shared preferences
// and displays on the screen
    private fun displayFirebaseRegId() {
        val pref = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
        val regId = pref.getString("regId", null)
        Log.e(TAG, "Firebase reg id: $regId", null)
        if (!TextUtils.isEmpty(regId)) {
            tvRegistrationId.text = "Firebase Reg Id: $regId"
        } else {
            tvRegistrationId.text = "Firebase RegId is not received yet!"
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(registrationBroadcastReceiver!!, IntentFilter(Config.REGISTRATION_COMPLETE))
        // register new push receiver
// by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(registrationBroadcastReceiver!!, IntentFilter(Config.PUSH_NOTIFICATION))
        // clear the notification area when the app is opened
        NotificationUtils.clearNotification(applicationContext)
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(registrationBroadcastReceiver!!)
        super.onPause()
    }

    fun getToken(view: View?) {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }
                    // Get new Instance ID token
                    val token = task.result!!.token
                    // Log and toast
                    //  String msg = getString(R.string.msg_token_fmt, token);
                    Log.d(TAG, token)
                    Toast.makeText(this@MainActivity, "Token: $token", Toast.LENGTH_SHORT)
                            .show()
                })
    }

}