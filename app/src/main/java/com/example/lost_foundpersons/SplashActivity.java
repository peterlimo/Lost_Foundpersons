package com.example.lost_foundpersons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class  SplashActivity extends AppCompatActivity {
SharedPreferences pref;
SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> checkLogin(),2000);
    }

    private void checkLogin() {
        if (pref==null)
        {
            pref=getSharedPreferences("user",MODE_PRIVATE);

            String username=pref.getString("email","");
            if (username!=null && !username.equals(""))
            {
                Intent intent=new Intent(getApplicationContext(),Dashboard.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    }
}