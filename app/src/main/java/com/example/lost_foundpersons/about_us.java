package com.example.lost_foundpersons;

import android.os.Bundle;
import android.view.WindowManager;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class about_us extends AppCompatActivity {
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about_us);
//Initializing toolbar
        toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        //setting the back navigation icon
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    //methods to implement onBackPressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;



    }
}






