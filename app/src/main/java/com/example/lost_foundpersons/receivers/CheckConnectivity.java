package com.example.lost_foundpersons.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;
import com.example.lost_foundpersons.Listener;


public class CheckConnectivity extends BroadcastReceiver {
    Listener listener;
    @Override
    public void onReceive(Context context, Intent arg1) {
      listener  =new Listener(context);
        boolean isConnected = arg1.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        if(!isConnected) {
           listener.listen();
        }
         else
        {
            Toast.makeText(context, "Network DisConnected", Toast.LENGTH_SHORT).show();
            //context.stopService(new Intent(context,Myservice.class));
        }
    }



}
