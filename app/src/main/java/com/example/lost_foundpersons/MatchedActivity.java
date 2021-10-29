package com.example.lost_foundpersons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lost_foundpersons.data.Adapter;
import com.example.lost_foundpersons.data.Match;
import com.example.lost_foundpersons.data.MatchedAdapter;
import com.example.lost_foundpersons.data.MissData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MatchedActivity extends AppCompatActivity implements MatchedAdapter.OnItemClickListener{
    RecyclerView recyclerView;
    LinearLayoutManager LayoutManager;
    List<Match> UserList;
   MatchedAdapter adapter;
    Toolbar toolbar;
    FirebaseFirestore db;
    SharedPreferences pref;
    String username;
  public  String name;
   public String gender;
   public String age;
  public   String url;
  String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matched);
        name=getIntent().getStringExtra("name");
        gender=getIntent().getStringExtra("gender");
        age=getIntent().getStringExtra("age");
        url=getIntent().getStringExtra("url");
        id=getIntent().getStringExtra("id");
        UserList=new ArrayList<>();
        recyclerView=findViewById(R.id.matched_recycler);
        LayoutManager=new LinearLayoutManager(this);
        LayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(LayoutManager);
        adapter=new MatchedAdapter(UserList,this,this);
        recyclerView.setAdapter(adapter);
        toolbar=findViewById(R.id.matched_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fetchData();
    }

    public void fetchData() {
        Match match=new Match(name,"","",gender,"",url,"","","",true);
        UserList.add(match);
        adapter.notifyDataSetChanged();
        db= FirebaseFirestore.getInstance();
        db.collection("missing").get().addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                       for (DocumentSnapshot doc:queryDocumentSnapshots){
                          String status= doc.getString("status");
                          String namee=doc.getString("name");
                          String genderr=doc.getString("gender");
                          String urll=doc.getString("url");
                          String userr=doc.getString("user");

                          if (status.equals("Found") && namee.contains(name) && genderr.equals(gender)){
                            db.collection("missing").document(id).collection("images").get().addOnSuccessListener(queryDocumentSnapshots1 -> {
                                for (DocumentSnapshot doc1 : queryDocumentSnapshots1) {
                                    Match match1 = new Match(namee, "", "", genderr, "", doc1.getString("url"), userr, "", "", false);
                                    UserList.add(match1);
                                    adapter.notifyDataSetChanged();
                                }
                            });

                          }
                       }
                    }
                }
        ).addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Failed to get data",Toast.LENGTH_LONG).show());
    }

    public String getUser() {
        pref=getSharedPreferences("user",MODE_PRIVATE);
        username=pref.getString("email","");
        return username;

    }
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
    public void onItemClick(int position, View v) {

    }
}