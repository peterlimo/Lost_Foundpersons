package com.example.lost_foundpersons;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lost_foundpersons.data.Adapter;
import com.example.lost_foundpersons.data.MissData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class All_records extends AppCompatActivity implements Adapter.OnItemClickListener{
    RecyclerView recyclerView;
    LinearLayoutManager LayoutManager;
    List<MissData> UserList;
    Adapter adapter;
    Toolbar toolbar;
    FirebaseFirestore db;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

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
            addNotification();
        }

        private void initRecyclerview() {
            recyclerView=findViewById(R.id.my_records_recycler);
            LayoutManager=new LinearLayoutManager(this);
            LayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(LayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
            adapter=new Adapter(UserList,this,this);
        }
        private void getData()
        {
            db.collection("missing")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots ->
                    {
                        for (QueryDocumentSnapshot doc:queryDocumentSnapshots)
                        {
                            String name= doc.get("name").toString();
                            String poster=doc.getString("postedby");
                           String  location=doc.get("location").toString();
                            String age=doc.get("age").toString();
                            String gender=doc.get("gender").toString();
                            String isAlive=doc.get("isAlive").toString();
                            String url=doc.get("url").toString();
                            String status=doc.get("status").toString();
                            MissData data=new MissData(name,location,age,gender,status,url,poster,isAlive,null);
                            UserList.add(data);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
//                            SetData(name,location,age,gender,poster,status,isAlive);
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
                            MissData data=new MissData(name,location,age,gender,status,url,poster,"",null);
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


    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
//                      .setSmallIcon(R.drawable.ic_baseline_person_24) //set icon for notification
                        .setContentTitle("Notifications Example") //set title of notification
                        .setContentText("This is a notification message")//this is notification message
                        .setAutoCancel(true) // makes auto cancel of notification
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT); //set priority of notification


        Intent notificationIntent = new Intent(this, NotificationView.class);
//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        //notification message will get at NotificationView
//        notificationIntent.putExtra("message", "This is a notification message");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // Add as notification
        //NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       // manager.notify(0, builder.build());
    }

    @Override
    public void onItemClick(int position, View v) {
            // add click events for the all records recycler view here
//        Toast.makeText(this, "All records clicked", Toast.LENGTH_SHORT).show();
    }
}

