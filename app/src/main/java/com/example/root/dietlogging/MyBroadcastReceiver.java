package com.example.root.dietlogging;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.root.dietlogging.Activities.HomeActivity;
import com.example.root.dietlogging.Activities.SearchActivity;
import com.example.root.dietlogging.Entities.User;
import com.example.root.dietlogging.ViewModels.UserViewModel;

import java.util.Calendar;

public class MyBroadcastReceiver extends BroadcastReceiver {
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    private static final String CHANNEL_ID = "ch";
    private static final String TAG = "MyBroadcastReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "BroadcastReceiver::OnReceive() >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        // Create an explicit intent for an Activity in your app
        Intent searchIntent = new Intent(context, SearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, searchIntent, 0);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_add_white_24dp)
                .setContentTitle("Diet Logging app")
                .setContentText("Have you added your last meal?")
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, mBuilder.build());


        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 14);

        int notificationValue = intent.getExtras().getInt("notificationValue");
        Log.d("broad notif value >>> ", String.valueOf(notificationValue));
        switch (notificationValue) {

            case 0:
                //alarm manager push notification daily
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY,
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                Log.d("notif broadcast > ", "> > 0");

                break;
            case 1:
                //alarm manager push notification every three days
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY * 3,
                        AlarmManager.INTERVAL_DAY * 3, pendingIntent);
                Log.d("notif broadcast > ", "> > 1");

                break;
            case 2:
                //alarm manager push notification every week
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY * 7,
                        AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                Log.d("notif broadcast > ", "> > 2");

                break;
            case 3:
                //alarm manager push notification every month
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY * 30,
                        AlarmManager.INTERVAL_DAY * 30, pendingIntent);
                Log.d("notif broadcast > ", "> > 3");

                break;


        }


    }
}

