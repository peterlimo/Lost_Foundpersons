package com.example.lost_foundpersons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePasswordActivity extends AppCompatActivity {
EditText text_email,text_code,text_n_password,text_c_password;
Button update_btn;
FirebaseFirestore db;
Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        text_email = findViewById(R.id.text_email);
        text_code = findViewById(R.id.text_resetcode);
        text_n_password = findViewById(R.id.text_n_password);
        text_c_password = findViewById(R.id.text_c_password);
        update_btn = findViewById(R.id.update_btn);

        db=FirebaseFirestore.getInstance();

        preferences = new Preferences(this);

        text_email.setText(preferences.getUser());

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCode(text_code.getText().toString());
            }
        });
    }

    private void checkCode(String code) {
        db.collection("reset").document(preferences.getUser()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists())
                {
                    String codeMa=documentSnapshot.getString("code");
                    if (codeMa.equals(code)){
                        updateUserData(text_email.getText().toString(),text_n_password.getText().toString(),text_c_password.getText().toString());
                    }
                    else
                    {
                        Toast.makeText(ChangePasswordActivity.this, "Please enter the correct reset code!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void updateUserData(String email, String pass1, String pass2) {
        if (pass1.equals(pass2))
        {
           db.collection("users").document(email).update("password",pass1).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void aVoid) {
                   Toast.makeText(ChangePasswordActivity.this, "Passwords updated successfully", Toast.LENGTH_SHORT).show();
                   Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                   startActivity(intent);
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(ChangePasswordActivity.this, "Failed to update passwords", Toast.LENGTH_SHORT).show();
               }
           });
        }
        else
        {
            Toast.makeText(this, "Passwords do not match!!", Toast.LENGTH_SHORT).show();
        }
    }
}