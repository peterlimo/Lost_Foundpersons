package com.example.lost_foundpersons;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lost_foundpersons.data.Miss;
import com.example.lost_foundpersons.data.MissData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Missing extends AppCompatActivity {
    CircleImageView pickmain;
    Toolbar toolbar;
    EditText name,location,age;
     Button submitbtn;
     String gender="";
     ProgressDialog dialog;
    RadioGroup radioGroup;
    Uri imageuri;
    FirebaseFirestore db;
SharedPreferences pref;
String username;
    //image view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//hide status
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_missing);
        //initializing radioGroup
        pref=getSharedPreferences("user",MODE_PRIVATE);
       radioGroup = findViewById(R.id.group_gender);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.missingmale:

                            gender="male";
                        break;
                    case R.id.missingfemale:

                            gender="female";
                        break;
                }
            }
        });
        pickmain=findViewById(R.id.image2);
        dialog=new ProgressDialog(this);
        //Initializing toolbar
        toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        db=FirebaseFirestore.getInstance();
        submitbtn=findViewById(R.id.submitbtn);
        name=findViewById(R.id.miss_name);
        location=findViewById(R.id.miss_location);
        age=findViewById(R.id.miss_age);

        //setting the back navigation icon
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pickmain.setOnClickListener(v -> openimagefrom());

        submitbtn.setOnClickListener(v -> AddData());
            }

    private void AddData() {
        dialog.show();
        FirebaseStorage reference= FirebaseStorage.getInstance();
       StorageReference storageRef = reference.getReferenceFromUrl("gs://my-finder-app-b8590.appspot.com");

            StorageReference  name=storageRef.child(imageuri.getLastPathSegment());

            name.putFile(imageuri).addOnSuccessListener(taskSnapshot -> name.getDownloadUrl().addOnSuccessListener(uri -> {
                dialog.dismiss();
             String url=uri.toString();
             StoreLink(url);
            })).addOnProgressListener(
                    task -> {
                      double progress=(100.0 * task.getBytesTransferred())/task.getTotalByteCount();
                      int prog=(int)progress;
                      dialog.setMessage("Uploading" +prog);
                    }
            );}


    private void StoreLink(String url) {
        MissData missData=new MissData(name.getText().toString(),location.getText().toString(),age.getText().toString(),gender,"missing","",getUser(),"Alive");
        Miss miss=new Miss(url);
        db.collection("missing").document(name.getText().toString())
                .set(missData)
                .addOnSuccessListener(aVoid -> db.collection("missing").document(name.getText().toString()).collection("images")
                        .document().set(miss).addOnSuccessListener(
                        this::onSuccess).addOnFailureListener(e ->
                        Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_SHORT).show()));

    }

    //fetch image from gallery
    public void openimagefrom()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent,1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==1&& resultCode==RESULT_OK&&data!=null &&data.getData()!=null){
               imageuri=data.getData();
//               pickmain.setImageURI(imageuri);
            InputStream imageStream= null;
            try {
                imageStream = getContentResolver().openInputStream(imageuri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap= BitmapFactory.decodeStream(imageStream);
            pickmain.setImageBitmap(bitmap);
            super.onActivityResult(requestCode, resultCode, data);
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

    private void onSuccess(Void aVoid1) {
        pickmain.setImageResource(R.drawable.profile1);
        location.getText().clear();
        name.getText().clear();
        age.getText().clear();
        radioGroup.clearCheck();
        Toast.makeText(getApplicationContext(), "Uploaded successfully", Toast.LENGTH_SHORT).show();
    }

    public String getUser() {
        if (pref!=null)
        {
            pref=getSharedPreferences("user",MODE_PRIVATE);
             username=pref.getString("email","");

            return username;
        }
        return null;
    }
}
