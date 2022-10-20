package com.CSE3311.parrot;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.content.Intent;
import android.content.Context;
import androidx.core.app.NotificationCompat;

public class notification_class {
    private final static String channel_id = "Parrot Notification";

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channel_id, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification getNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel_id)
                .setContentTitle("ALERT!!")
                .setContentText("You have a notification!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Longer text will not fit..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
}
