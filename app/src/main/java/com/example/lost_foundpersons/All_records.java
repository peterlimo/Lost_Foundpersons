package com.example.lost_foundpersons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.lost_foundpersons.data.Adapter;
import com.example.lost_foundpersons.data.MissData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class All_records extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager LayoutManager;
    List<MissData> UserList;
    Adapter adapter;
    Toolbar toolbar;
    FirebaseFirestore db;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_all_records);
            //Initializing toolbar
            toolbar = findViewById(R.id.Toolbar);
            setSupportActionBar(toolbar);
            UserList=new ArrayList<>();
            //setting the back navigation icon
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(true);
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                            String name= doc.getId();
                            String poster=doc.getString("postedby");
                           String  location=doc.get("location").toString();
                            String age=doc.get("age").toString();
                            String gender=doc.get("gender").toString();
                            String isAlive=doc.get("isAlive").toString();
                           String status=doc.get("status").toString();
                            SetData(name,location,age,gender,poster,status,isAlive);
                        }
                    });
        }

        private void SetData(String name, String location, String age, String gender, String poster,String status,String isAlive) {
            db.collection("missing").document(name).collection("images")
                    .get().addOnSuccessListener(
                    queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot doc:queryDocumentSnapshots)
                        {
                            String url=doc.get("url").toString();
                            MissData data=new MissData(name,location,age,gender,status,url,poster,"");
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
    }

