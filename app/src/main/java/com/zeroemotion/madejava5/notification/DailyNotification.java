package com.zeroemotion.madejava5.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.zeroemotion.madejava5.R;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import static com.zeroemotion.madejava5.view.fragment.SettingFragment.CHANNEL_NAME;

public class DailyNotification extends BroadcastReceiver {
    private static final String ALARM_REMINDER = "alarm_reminder";
    private static final int ID_REMINDER = 101;
    private String CHANNEL_ID = "channel_01";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "Hello mind to check the movie?";
        String title = "Movie Catalogue";
        showAlarmNotification(context,title,message);
    }

    private void showAlarmNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ALARM_REMINDER)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context,android.R.color.holo_blue_dark))
                .setVibrate(new long[]{1000,1000,1000,1000})
                .setSound(alarmSound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Notification notification = builder.build();
        if (notificationManager != null){
            notificationManager.notify(DailyNotification.ID_REMINDER, notification);
        }
    }
}
