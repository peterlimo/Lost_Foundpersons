package com.example.lost_foundpersons;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Notificationn {
        Context context;
        NotificationManagerCompat notificationManagerCompat;
    Notification notification;
    public Notificationn(Context context) {
        this.context = context;
    }
    public void showNotification(String title, String message)
    {
        NotificationManager manager;

  if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
 {
     NotificationChannel channel=new NotificationChannel("default_channel_id","My Channel",NotificationManager.IMPORTANCE_HIGH);
     manager = context.getSystemService(NotificationManager.class);
     manager.createNotificationChannel(channel);
 }
 NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
         .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
         .setContentTitle(title)
         .setContentText(message)
         .setAutoCancel(true);
  builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
       notification=builder.build();
       notificationManagerCompat=NotificationManagerCompat.from(context);
       notificationManagerCompat.notify(1,notification);
    }
}
