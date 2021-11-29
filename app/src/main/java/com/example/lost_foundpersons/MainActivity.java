package com.example.lost_foundpersons;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //Declaring variables
    Button loginbtn;
    LinearLayout openreg;
    private Object forgotpassword;
    AlertDialog.Builder builder;
    EditText email,password;
    FirebaseFirestore db;
//    com.google.firebase.firestore.auth.User user;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        builder = new AlertDialog.Builder(this);
        //initializing variables
        loginbtn = findViewById(R.id.loginbtn);
        forgotpassword = findViewById(R.id.forgotpassword);
email=findViewById(R.id.usermail);
password=findViewById(R.id.miss_location);
        db=FirebaseFirestore.getInstance();
//btn to submit data to firestore

//forgot password
        ((View) forgotpassword).setOnClickListener(v -> {
            MainActivity.this.builder.setMessage("Do you want to reset your password?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            (dialog, which) -> {
                               //Intent intent = new Intent(getApplicationContext(), Reset_password.class);
                               // startActivity(intent);
                            }).setNegativeButton("No",
                    (dialog, which) -> {

                        dialog.cancel();
                        Toast.makeText(MainActivity.this, "cancelled", Toast.LENGTH_SHORT).show();
                    });
            AlertDialog alertDialog = MainActivity.this.builder.create();alertDialog.setTitle("Confirm");
            alertDialog.show();

});


        loginbtn.setOnClickListener(v -> {
            String eemail=email.getText().toString();
            String epassword=password.getText().toString();
            db.collection("users").document(eemail).get().addOnSuccessListener(documentSnapshot -> {
                if(documentSnapshot.exists())
                {
                   if(Objects.requireNonNull(documentSnapshot.get("password")).toString().equals(epassword))
                   {
                       pref=getSharedPreferences("user",MODE_PRIVATE);
                       editor=pref.edit();
                       editor.putString("email",eemail);
                       editor.putString("type","normal");
                       editor.commit();
                       Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                       startActivity(intent);
                   }
                   else{
                       AlertDialog alertDialog = MainActivity.this.builder.create();
                       alertDialog.setTitle("error");
                       alertDialog.setMessage("wrong username or password");
                       alertDialog.show();

                   }

                }
                else{
                    AlertDialog alertDialog = MainActivity.this.builder.create();
                    alertDialog.setTitle("error");
                    alertDialog.setMessage("wrong username or password");
                    alertDialog.show();
                }

               // Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                //startActivity(intent);

            }).addOnFailureListener(e -> {
                AlertDialog alertDialog = MainActivity.this.builder.create();
                alertDialog.setTitle("error");
                alertDialog.setMessage("wrong username or password");
                alertDialog.show();
            });


        });

        openreg = findViewById(R.id.openreg);
        openreg.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), CreateAccount.class);
            startActivity(i);
        });
    }
}









