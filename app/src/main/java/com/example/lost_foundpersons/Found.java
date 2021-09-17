package com.example.lost_foundpersons;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lost_foundpersons.data.FoundData;
import com.example.lost_foundpersons.data.Miss;
import com.example.lost_foundpersons.data.MissData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.Bitmap;
@RequiresApi(api = Build.VERSION_CODES.M)
public class Found extends AppCompatActivity {
    EditText name, location, age;
    String gender = "";
    String status = "";
    Button submitbtn;
    Uri imageuri;
    ProgressDialog dialog;
    RadioGroup radioGroup;
    FirebaseFirestore db;
    Toolbar toolbar;
    //image view
    private static final int CAMERA_REQUEST = 1888;
    private CircleImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_found);
        //initializing radioGroup
        radioGroup = findViewById(R.id.group_gender);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.found_Male:
                    gender = "male";
                    break;
                case R.id.found_Female:
                    gender = "female";
                    break;
            }
        });
        //initializing radioGroup
        radioGroup = findViewById(R.id.group_status);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.found_alive:

                    status = "Alive";
                    break;
                case R.id.found_dead:

                    status = "Dead";
                    break;
            }
        });
//        pickmain = findViewById(R.id.linearLayout
        dialog = new ProgressDialog(this);
        name = findViewById(R.id.username);
        location = findViewById(R.id.found_location);
        age = findViewById(R.id.found_age);
        submitbtn = findViewById(R.id.submitbtn);
        //allow profile take a picture via camera
        imageView = findViewById(R.id.linearLayout);
        db = FirebaseFirestore.getInstance();
        //Initializing toolbar
        toolbar = findViewById(R.id.found_toolbar);
        setSupportActionBar(toolbar);
        //setting the back navigation icon
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        submitbtn.setOnClickListener(v -> {
            if (imageuri != null) {
                AddData();
            }
            else{
                Toast.makeText(this, "image no found", Toast.LENGTH_SHORT).show();
            }});
        imageView.setOnClickListener(view -> {
                Intent cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
                cameraIntent.setType("image/*");
                startActivityForResult(cameraIntent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == RESULT_OK && data.getData()!=null) {
                imageuri = data.getData();
//                Bitmap bitmap=(Bitmap) data.getExtras().get("data");
//                imageView.setImageBitmap(bitmap);
                InputStream imageStream= null;
                try {
                    imageStream = getContentResolver().openInputStream(imageuri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap= BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(bitmap);
            }

        }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

//    {
//;
//        int requestCode = 0;
//        int resultCode = 0;
//        @Nullable Intent data = null;
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }

    private void AddData() {
        dialog.show();
        FirebaseStorage reference = FirebaseStorage.getInstance();
        StorageReference storageRef = reference.getReference();
        StorageReference name = storageRef.child("images");
        name.putFile(imageuri).addOnSuccessListener(taskSnapshot ->
                name.getDownloadUrl().addOnSuccessListener(uri -> {
                    dialog.dismiss();
                    String url = uri.toString();
                    StoreLink(url);
                })).addOnProgressListener(
                task -> {
                    double progress = (100.0 * task.getBytesTransferred()) / task.getTotalByteCount();
                    int prog = (int) progress;
                    dialog.setMessage("Uploading" + prog);
                });

    }
    private void StoreLink(String url)
    {
        MissData missData = new MissData(name.getText().toString(), location.getText().toString(), age.getText().toString(), gender, "Found","",getUser(),status);
        Miss miss=new Miss(url);
        db.collection("missing").document(name.getText().toString())
                .set(missData)
                .addOnSuccessListener(aVoid -> {db.collection("missing").document(name.getText().toString()).collection("images")
                        .document().set(miss).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                afterSuccess();
                                Toast.makeText(getApplicationContext(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
               });
    }
    public String getUser()
    {
            pref=getSharedPreferences("user",MODE_PRIVATE);
            String username=pref.getString("email","");
            return username;
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


  public void afterSuccess() {
        imageView.setImageResource(R.drawable.profile1);
        location.getText().clear();
        name.getText().clear();
        age.getText().clear();
        radioGroup.clearCheck();
    }
}




