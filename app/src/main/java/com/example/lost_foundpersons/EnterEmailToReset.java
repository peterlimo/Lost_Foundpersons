package com.example.lost_foundpersons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lost_foundpersons.data.Reset;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EnterEmailToReset extends AppCompatActivity {
EditText reset_email;
Button reset_button;
FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_email_to_reset);
        db=FirebaseFirestore.getInstance();
        reset_email=findViewById(R.id.reset_email);
        reset_button=findViewById(R.id.reset_button);


        reset_button.setOnClickListener(view -> generateCode());

    }

    private void generateCode() {
        Random random= new Random();
        int no=random.nextInt((10000 - 1000) + 1) + 1000;
        Preferences preferences=new Preferences(this);
        Reset reset=new Reset(reset_email.getText().toString(),no+"");
        if (preferences.getUser()!=null) {
            db.collection("reset").document(reset_email.getText().toString()).
                    set(reset).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    sendRequest(reset_email.getText().toString(), no + "");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EnterEmailToReset.this, "Failed to generate OTP", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void sendRequest(String email, String code) {
        Reset reset=new Reset(email,code);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://smartlinkjobs.com/pass/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<Reset> call = retrofitAPI.resetPassword(reset);
        call.enqueue(new Callback<Reset>() {
            @Override
            public void onResponse(Call<Reset> call, Response<Reset> response) {
                Intent intent = new Intent(getApplicationContext(),ChangePasswordActivity.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<Reset> call, Throwable t) {
                Intent intent = new Intent(getApplicationContext(),ChangePasswordActivity.class);
                startActivity(intent); }
        });
    }
}