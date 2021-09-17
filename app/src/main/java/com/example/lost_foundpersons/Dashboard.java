package com.example.lost_foundpersons;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;

public class Dashboard extends AppCompatActivity {

    //declaring variables
    Toolbar toolbar;
    LinearLayout openmissing, openfound, myrecords,all_records;
    EditText name, password;
SharedPreferences pref;
TextView welcome_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        pref=getSharedPreferences("user",MODE_PRIVATE);



//initializing variables
        welcome_txt=findViewById(R.id.welcome_txt);
        openmissing = findViewById(R.id.open_missing_activity);
        openfound = findViewById(R.id.open_found_person_activity);
        myrecords = findViewById(R.id.myrecords);
        toolbar = findViewById(R.id.dash_toolbar);
        all_records=findViewById(R.id.all_records);
        setSupportActionBar(toolbar);

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



    public boolean onCreateOptionsMenu(Menu menu) {
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
                Toast.makeText(this, "Logout successfully", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();
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
    private void checkLogin() {
       if (pref!=null)
        {
           pref=getSharedPreferences("user",MODE_PRIVATE);
           String username=pref.getString("email","");
           welcome_txt.setText("Welcome "+username);
        }
    }
}









