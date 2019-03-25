package in.example.rahul.vegcartpro.utils;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import in.example.rahul.vegcartpro.R;

public class NotificationUtils {

    private static String TAG= NotificationUtils.class.getSimpleName();

    private Context context;

    public NotificationUtils(Context context){
        this.context= context;
    }
    public void showNotificationMessage(String title, String message, String timeStamp, Intent intent){
        showNotificationMessage(title, message, timeStamp, intent);
    }
    public void showNotificationMessage(final String title, final String message, final String timeStamp, Intent intent, String imageUrl){
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;

        // notification icon

        final int icon = R.drawable.fruit;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent= PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        final NotificationCompat.Builder builder= new NotificationCompat.Builder(context);

        final Uri alarmSound= Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName()+ "/raw/bird_voice");

        if (!TextUtils.isEmpty(imageUrl)){
            if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()){

                BitmapDrawable bitmapDrawable; // android.graphics.drawable.BitmapDrawable
                Bitmap bitmap= getBitmapFromURL(imageUrl);

                if (bitmap != null){
                    showBigNotification(bitmap, builder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                }
                 else {
                    showSmallNotification(builder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                }

            }
        } else {
            showSmallNotification(builder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
            playNotificationSound();
        }
    }

    private void showSmallNotification(NotificationCompat.Builder builder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.InboxStyle inboxStyle= new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);

        Notification notification;
        notification= builder.setSmallIcon(icon).setTicker(title).setWhen(0).setAutoCancel(true).setContentTitle(title).setContentIntent(resultPendingIntent).setSound(alarmSound).setStyle(inboxStyle).setWhen(getTimeMilliSec(timeStamp)).setSmallIcon(R.drawable.fruit).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon)).setContentText(message).build();

        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID, notification);
    }



    private void showBigNotification(Bitmap bitmap,NotificationCompat. Builder builder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.BigPictureStyle bigPictureStyle= new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);

        Notification notification;
        notification= builder.setSmallIcon(icon).setAutoCancel(true).setContentTitle(title).setContentIntent(resultPendingIntent).setSound(alarmSound).setStyle(bigPictureStyle).setWhen(getTimeMilliSec(timeStamp)).setSmallIcon(R.drawable.fruit).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon)).setContentText(message).build();

        NotificationManager notificationManager= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID_BIG_IMAGE, notification);
    }

    public Bitmap getBitmapFromURL(String strURL){

        try {
            URL url= new URL(strURL);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input= connection.getInputStream();
            Bitmap mBitmap= BitmapFactory.decodeStream(input);
            return mBitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Play notification sound
    public void playNotificationSound(){
        try {


        Uri alarmSound= Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://"+ context.getPackageName()+"/raw/bird_voice");
        Ringtone r= RingtoneManager.getRingtone(context, alarmSound);
        r.play();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    // To Check App is in Background or not

    public static boolean isAppIsInBackground(Context context){
        boolean isInBackground= true;
        ActivityManager am= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH){
            List<ActivityManager.RunningAppProcessInfo> runningProcess= am.getRunningAppProcesses();

            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcess){
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                    for (String activityProcess : processInfo.pkgList){
                        if (activityProcess.equals(context.getPackageName())){
                            isInBackground= false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo= am.getRunningTasks(1);
            ComponentName componentName= taskInfo.get(0).topActivity;

            if (componentName.getPackageName().equals(context.getPackageName())){
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    // Clear notification try message

    public static void clearNotification(Context context){
        NotificationManager notificationManager= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
    public static long getTimeMilliSec(String timeStamp){
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            Date date= format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e){
            e.printStackTrace();
        }
    return 0;
    }

}
