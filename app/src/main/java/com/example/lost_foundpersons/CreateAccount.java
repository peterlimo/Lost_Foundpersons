package com.example.lost_foundpersons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccount extends AppCompatActivity {
    //declaring variables
    EditText name,email,password,phonenumber;
    User user;
    Toolbar toolbar;
    Button submitbtn;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        toolbar=findViewById(R.id.my_toolbar);
        name=findViewById(R.id.miss_age);
        email=findViewById(R.id.useremail);
        password=findViewById(R.id.userpass);
        phonenumber=findViewById(R.id.phone);


        submitbtn=findViewById(R.id.submitbtn);
        db=FirebaseFirestore.getInstance();
        setSupportActionBar(toolbar);

//btn to submit data to firestore
        submitbtn.setOnClickListener(v -> {
         String ename=name.getText().toString();
            String eemail=email.getText().toString();
            String ephone=phonenumber.getText().toString();
            String epassword=password.getText().toString();
            user=new User(ename,epassword,eemail,ephone);
            db.collection("users").document(eemail).set(user)
                    .addOnSuccessListener(aVoid -> {
                        name.getText().clear();
                        phonenumber.getText().clear();
                        email.getText().clear();
                        password.getText().clear();
                        Toast.makeText(CreateAccount.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> Toast.makeText(CreateAccount.this, "Failed to register"+e.getMessage(), Toast.LENGTH_SHORT).show());

        });
        //setting the back navigation icon
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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