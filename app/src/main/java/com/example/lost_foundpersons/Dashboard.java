package com.example.lost_foundpersons;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lost_foundpersons.data.Match;
import com.example.lost_foundpersons.receivers.Myservice;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;

public class Dashboard extends AppCompatActivity {
    Notificationn notificationn;
    //declaring variables
    Toolbar toolbar;
    MaterialCardView openmissing, openfound, myrecords,all_records;
//  EditText name, password;
    SharedPreferences pref;
    TextView welcome_txt;
    FirebaseFirestore db;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        notificationn=new Notificationn(this);
        db=FirebaseFirestore.getInstance();

        pref=getSharedPreferences("user",MODE_PRIVATE);


//initializing variables
        welcome_txt=findViewById(R.id.welcome_txt);
        openmissing = findViewById(R.id.open_missing_activity);
        openfound = findViewById(R.id.open_found_person_activity);
        myrecords = findViewById(R.id.myrecords);
        toolbar = findViewById(R.id.dash_toolbar);
        all_records=findViewById(R.id.all_records);
        setSupportActionBar(toolbar);
//        startService(new Intent(getBaseContext(), Myservice.class));
        checkLogin();
        openmissing.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Missing.class);
            startActivity(intent);
        });
        openfound.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Found.class);
            startActivity(intent);
        });
        myrecords.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Myrecords.class);
            startActivity(intent);
        });
        all_records.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), All_records.class);
            startActivity(intent);
        });
    }

    private void checkFoundUsers()
    {

    db.collection("missing")
            .limit(1)
            .orderBy("time", Query.Direction.ASCENDING)
            .addSnapshotListener((queryDocumentSnapshots, e) ->
            {
                for (DocumentSnapshot doc :queryDocumentSnapshots)
                {
                if (doc.exists())
                {
               name=doc.getId();
                String gender=doc.get("gender").toString();
                    db.collection("missing").get().addOnSuccessListener(
                            queryDocumentSnapshots12 -> {

                                for (DocumentSnapshot doc12 : queryDocumentSnapshots12) {
                                    String status = doc12.getString("status");
                                    String namee = doc12.getString("name");
                                    String genderr = doc12.getString("gender");
                                    String urll = doc12.getString("url");
                                    String userr = doc12.getString("user");
//                                    String location=doc12.get("location").toString();

                                    if (status.equals("Found") && namee.contains(name)) {
                                         notificationn.showNotification(name,"A person which matches the loved one you posted has been found\nCheck if he is your relative\n" +
                                                        "" +
                                                        "person name="+name

                                                );
                                            }

                                    }

                            });


                }
                }
            });
    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.dashboard_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.about_us:
//Start activity about us
                Intent intent = new Intent(getApplicationContext(), about_us.class);
                startActivity(intent);
                return true;
            case R.id.contact_us:
                Intent i = new Intent(getApplicationContext(), Contact_us.class);
                startActivity(i);
                return true;
            case R.id.logout_user:
                SharedPreferences preferences=getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(this, "Logout Successful!!", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                this.finish();
                return true;
            case R.id.share:
                ApplicationInfo api = getApplicationContext().getApplicationInfo();
                String apkpath = api.sourceDir;
                Intent in = new Intent(Intent.ACTION_SEND);
                in.setType("application/vnd.android.package-archive");
                in.putExtra(Intent.EXTRA_STREAM, String.valueOf(Boolean.parseBoolean(String.valueOf(Uri.fromFile(new File(apkpath))))));
                startActivity(Intent.createChooser(in, "ShareVia"));
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
    public String getUser() {
        pref=getSharedPreferences("user",MODE_PRIVATE);
        String username=pref.getString("email","");
        return username;

    }
    private void checkLogin() {
       if (pref!=null)
        {
           pref=getSharedPreferences("user",MODE_PRIVATE);
           String username=pref.getString("email","");
           welcome_txt.setText("Welcome "+username);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel("default_channel_id","limo", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("This is my notification channel");
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

}









