package com.CSE3311.parrot;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;
import android.content.Intent;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class notification_class extends AppCompatActivity {
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

    private void scheduleNotification()
    {
        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

    }


    public Notification getNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel_id)
                .setContentTitle("ALERT!!")
                .setContentText("You have a notification!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Longer text will not fit..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        return builder.build();
    }
}
