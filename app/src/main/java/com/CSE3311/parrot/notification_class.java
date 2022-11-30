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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class notification_class extends AppCompatActivity {
    public final static String channel_id = "Parrot Notification";
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_create_entry);
        Button submitEntry = (Button)findViewById(R.id.create_entry_submit_submission);

        /*submitEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        submitEntry.setOnClickListener(v -> {
            Toast.makeText(this, "reminder set", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(notification_class.this, Receiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(notification_class.this, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
        });

    }
    //submitEntry.setOnClickListener(new View.OnClickListener() {

    public void Submit(View v) {

       /*
        Intent intent = new Intent(this, App.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        */

        Notification builder = new NotificationCompat.Builder(this, channel_id)
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

        notificationManager.notify(1, builder);

    }
}