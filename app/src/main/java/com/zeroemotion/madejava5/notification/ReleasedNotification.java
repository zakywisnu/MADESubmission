package com.zeroemotion.madejava5.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.Toast;

import com.zeroemotion.madejava5.BuildConfig;
import com.zeroemotion.madejava5.R;
import com.zeroemotion.madejava5.model.Movie;
import com.zeroemotion.madejava5.model.MovieResponse;
import com.zeroemotion.madejava5.model.Notifications;
import com.zeroemotion.madejava5.service.MovieInstance;
import com.zeroemotion.madejava5.service.MovieService;
import com.zeroemotion.madejava5.view.activity.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.core.app.NotificationCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleasedNotification extends BroadcastReceiver {
    private static final String GROUP_KEY_REMINDER = "group_release_keys";
    private static final int MAX_NOTIFICATIONS = 3;
    private static final int RELEASE_REQUEST_CODE = 103;
    public static CharSequence CHANNEL_RELEASED_NAME = "channel_released";
    private List<Notifications> notifications = new ArrayList<>();
    private ArrayList<Movie> movies;
    private int movieId;

    public ReleasedNotification(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        getMovie(context);
    }

    private void getMovie(Context context) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = simpleDateFormat.format(date);
        MovieService movieService = MovieInstance.getService();
        Call<MovieResponse> call = movieService.getReleased(BuildConfig.API_KEY, currentDate,currentDate);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    movies = response.body().getResults();
                    for (int i = 0;i < movies.size(); i++){
                        notifications.add(new Notifications(movies.get(i).getId()
                        ,movies.get(i).getTitle(),movies.get(i).getOverview()));
                        movieId++;
                    }
                }
                showReleaseNotification(context);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });
    }

    private void showReleaseNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_black_24dp);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,RELEASE_REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;
        for (movieId = 1;movieId < movies.size(); movieId++){
            String CHANNEL_ID = "release_channel";
            if (movieId < MAX_NOTIFICATIONS){
                builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                        .setContentTitle(notifications.get(movieId).getSender())
                        .setContentText(notifications.get(movieId).getMessage())
                        .setSmallIcon(R.drawable.ic_movie_black_24dp)
                        .setLargeIcon(largeIcon)
                        .setGroup(GROUP_KEY_REMINDER)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
            }
            else{
                int tot = movies.size() - 2;
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                        .addLine(notifications.get(movieId).getSender() + ": " + notifications.get(movieId).getMessage())
                        .addLine(notifications.get(movieId).getSender() + ": " + notifications.get(movieId).getMessage())
                        .setBigContentTitle(movieId + 1 + context.getString(R.string.new_movie_release) + "" )
                        .setSummaryText("+" + tot + "");
                builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                        .setContentTitle(notifications.get(movieId).getSender() + "- Released " + setFormatDate())
                        .setContentText(notifications.get(movieId).getMessage())
                        .setSmallIcon(R.drawable.ic_movie_black_24dp)
                        .setGroup(GROUP_KEY_REMINDER)
                        .setGroupSummary(true)
                        .setContentIntent(pendingIntent)
                        .setStyle(inboxStyle)
                        .setAutoCancel(true);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_RELEASED_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                builder.setChannelId(CHANNEL_ID);
                if (notificationManager != null){
                    notificationManager.createNotificationChannel(channel);
                }
            }
            Notification notification = builder.build();
            if (notificationManager != null){
                notificationManager.notify(movieId, notification);
            }
        }
    }

    public void setReleasedReminder(Context context){
        cancelNotification(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleasedNotification.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,102,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);

        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, "Released Reminder Activated", Toast.LENGTH_SHORT).show();
    }

    public void cancelNotification(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleasedNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,102,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
    }

    private String setFormatDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
