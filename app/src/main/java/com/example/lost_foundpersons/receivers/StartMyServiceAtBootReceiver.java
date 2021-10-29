package com.example.lost_foundpersons.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.lost_foundpersons.Dashboard;

public class StartMyServiceAtBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, Dashboard.class);
            context.startService(serviceIntent);
        }
    }
}
