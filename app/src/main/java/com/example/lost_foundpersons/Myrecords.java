package com.example.lost_foundpersons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.lost_foundpersons.data.Adapter;
import com.example.lost_foundpersons.data.MissData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Myrecords extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager LayoutManager;
    List<MissData>UserList;
    Adapter adapter;
    Toolbar toolbar;
    FirebaseFirestore db;
    SharedPreferences pref;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_myrecords);
        //Initializing toolbar
        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        UserList=new ArrayList<>();
        //setting the back navigation icon
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref=getSharedPreferences("user",MODE_PRIVATE);
        db=FirebaseFirestore.getInstance();
        initRecyclerview();
        getData();
    }


    private void initRecyclerview() {
        recyclerView=findViewById(R.id.my_records_recycler);
        LayoutManager=new LinearLayoutManager(this);
        LayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        adapter=new Adapter(UserList,this);

    }
    private void getData()
    {

        db.collection("missing")
                .get()
              .addOnSuccessListener(queryDocumentSnapshots ->
              {
                  for (QueryDocumentSnapshot doc:queryDocumentSnapshots)
                  {

                      String user=doc.getString("user");
                      if (user.equals(getUser()) || user==getUser())
                      {
                        String name= doc.getId();
                        String location=doc.getString("location");
                        String age=doc.getString("age");
                        String gender=doc.getString("gender");
                        String status=doc.getString("status");
                        String url =doc.getString("url");
                        String isAlive=doc.getString("isAlive");
                      SetData(name,location,age,gender,user,status,isAlive);

                      }
                  }
              });
    }

    private void SetData(String name, String location, String age, String gender,String user,String status,String isAlive) {
                db.collection("missing").document(name).collection("images").limit(1)
                .get().addOnSuccessListener(
                queryDocumentSnapshots -> {
                   for (QueryDocumentSnapshot doc:queryDocumentSnapshots)
                   {
                       String url=doc.get("url").toString();
                       MissData data=new MissData(name,location,age,gender,status,url,user,isAlive);
                       UserList.add(data);
                       recyclerView.setAdapter(adapter);
                       adapter.notifyDataSetChanged();
                   }
                }
        );

    }


    //methods to implement onBackPressed
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public String getUser() {
            pref=getSharedPreferences("user",MODE_PRIVATE);
            username=pref.getString("email","");

            return username;

    }

}


