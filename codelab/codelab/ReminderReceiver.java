package com.example.codelab;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ReminderReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "reminder_channel";
    private static final int NOTIFICATION_ID = 1001;  // Unique ID for notifications

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);

    
        Intent activityIntent = new Intent(context, Dashboard.class);
        activityIntent.putExtra("openHomeFragment", true);  // Pass an extra flag

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, activityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Notification sound
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)  
                .setContentTitle("Code Learning Reminder")
                .setContentText("Time for your scheduled coding session!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(soundUri)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        // Display the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Code Learning Reminders";
            String description = "Channel for scheduled coding reminders";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
