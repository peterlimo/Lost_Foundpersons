package com.example.lost_foundpersons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class Contact_us extends AppCompatActivity {
Toolbar toolbar;
TextView textView3,textView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_us);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        //Initializing toolbar
        toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        //setting the back navigation icon
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = textView3.getText().toString();

                // Use format with "tel:" and phoneNumber created is
                // stored in u.
                Uri u = Uri.parse("tel:" + input);

                // Create the intent and set the data for the
                // intent as the phone number.
                Intent i = new Intent(Intent.ACTION_DIAL, u);

                try
                {
                    // Launch the Phone app's dialer with a phone
                    // number to dial a call.
                    startActivity(i);
                }
                catch (SecurityException s)
                {
                    // show() method display the toast with
                    // exception message.
                }

            }});}

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