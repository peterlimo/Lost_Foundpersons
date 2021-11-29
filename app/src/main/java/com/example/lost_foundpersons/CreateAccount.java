package com.example.lost_foundpersons;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccount extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener {
    //declaring variables
    EditText name,email,password,phonenumber;
    User user;
    Toolbar toolbar;
    Button submitbtn;
    FirebaseFirestore db;
    ImageView btn_google_signin;
     GoogleApiClient googleApiClient;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public static final int RC_SIGN_IN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        toolbar=findViewById(R.id.my_toolbar);
        name=findViewById(R.id.miss_age);
        email=findViewById(R.id.useremail);
        password=findViewById(R.id.userpass);
        phonenumber=findViewById(R.id.phone);
        btn_google_signin=findViewById(R.id.btn_google_signin);

        submitbtn=findViewById(R.id.submitbtn);
        db=FirebaseFirestore.getInstance();
        setSupportActionBar(toolbar);

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        btn_google_signin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        signUpWithGoogle();
    }
});
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

    private void signUpWithGoogle() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            user=new User(result.getSignInAccount().getDisplayName(),"null",result.getSignInAccount().getEmail(),"null");
            db.collection("users").document(result.getSignInAccount().getEmail()).set(user)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(CreateAccount.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        pref=getSharedPreferences("user",MODE_PRIVATE);
                        editor=pref.edit();
                        editor.putString("email",result.getSignInAccount().getEmail());
                        editor.putString("type","google");
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);
                  }).addOnFailureListener(e -> Toast.makeText(CreateAccount.this, "Failed to register: "+e.getMessage(), Toast.LENGTH_SHORT).show());

     }else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}