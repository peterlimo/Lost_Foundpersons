package com.example.lost_foundpersons.receivers;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lost_foundpersons.ConnectionHelper;
import com.example.lost_foundpersons.Listener;
import com.example.lost_foundpersons.data.AddR;
import com.example.lost_foundpersons.data.Sms;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class Myservice extends Service {
Context context;
public Myservice()
{

}
    public Myservice(Context context) {
        this.context = context;
    }

//    static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
//   FirebaseFirestore db;
//    Integer delay;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    public Boolean isWifiConnected() {
//        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        return mWifi.isConnected();
//    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Listener listener=new Listener(context);
        listener.listen();
        // Let it continue running until it is stopped.

//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        BroadcastReceiver receiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String action = intent.getAction();
//                if (CONNECTIVITY_CHANGE_ACTION.equals(action)) {
//                    //check internet connection
//                    if (!ConnectionHelper.isConnectedOrConnecting(context)) {
//                        if (context != null) {
//                            boolean show = false;
//                            if (ConnectionHelper.lastNoConnectionTs == -1) {//first time
//                                show = true;
//                                ConnectionHelper.lastNoConnectionTs = System.currentTimeMillis();
//                            } else {
//                                if (System.currentTimeMillis() - ConnectionHelper.lastNoConnectionTs > 1000) {
//                                    show = true;
//                                    ConnectionHelper.lastNoConnectionTs = System.currentTimeMillis();
//                                }
//                            }
//
//                            if (show && ConnectionHelper.isOnline) {
//                                ConnectionHelper.isOnline = false;
//
//                                //manager.cancelAll();
//                            }
//                        }
//                    } else {
//
//
//                        ConnectionHelper.isOnline = true;
//                    }
//                }
//            }
//        };
//        registerReceiver(receiver,filter);
       return START_STICKY;
    }
//    public void startSyncThread()
//    {
//        Handler handler = new Handler();
//        delay = 1000;
//
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                //UpoadContent();
//                handler.postDelayed(this, delay);
//            }
//        }, delay);
//    }
//
//    public void performSync()
//    {
//        if (isWifiConnected())
//        {
//
//            delay = 6000;
//        }
//        else
//        {
//            Toast.makeText(this, "Failed to upload content due to network", Toast.LENGTH_SHORT).show();
//            delay = 1000;
//        }
//
//    }
//    public void UpoadContent()
//    {
//
//        db=FirebaseFirestore.getInstance();
//        Toast.makeText(getApplicationContext(), "Listener service started", Toast.LENGTH_SHORT).show();
//
//    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}