package com.CSE3311.parrot;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class notification_class extends AppCompatActivity {
    public final static String channel_id = "Parrot Notification";

    public void scheduleNotification(boolean OnGoing, String OnGoingOption)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(channel_id, 1);
        intent.putExtra(channel_id, getNotification());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long time = SystemClock.elapsedRealtime();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;

    }

    @NonNull
    private Notification getNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel_id)
                .setContentTitle("ALERT!!")
                .setContentText("You have a notification!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Longer text will not fit..."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        return builder.build();
    }

    public void showNotification()
    {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(1, getNotification());
    }

}
