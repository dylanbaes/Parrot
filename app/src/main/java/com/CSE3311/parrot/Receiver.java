package com.CSE3311.parrot;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Receiver extends BroadcastReceiver {
    public final static String channel_id = "Parrot Notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        Notification builder = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Parrot")
                .setContentText("You have successfully created an entry!")

                //.setContentIntent(pendingIntent)
                //have not implemented yet

                //when notification sends it shows the big text first
                //instead of contentText

                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Your subscription is due next month"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, builder);
    }
}
