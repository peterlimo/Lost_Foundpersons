package com.example.lost_foundpersons;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Listener {
    Context context;
FirebaseFirestore db;
Preferences preferences;
Notificationn not;
List<String> list=new ArrayList<>();
    int prefvalue;
    public Listener(Context context) {
        this.context = context;
    }
    public Listener()
    {

    }
    public  void listen(){

        preferences=new Preferences(context);
        if (preferences.getLimit()==0)
        {
            prefvalue=0;
        }
        else
        {
             prefvalue=preferences.getLimit();
        }

        db=FirebaseFirestore.getInstance();
        checkSize();
    }

    public void checkSize() {
        db.collection("missing")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int s = queryDocumentSnapshots.size();
                    Toast.makeText(context, s+"", Toast.LENGTH_SHORT).show();
                    if (s > prefvalue)
                    {
                        int limit = s - prefvalue;
                        Toast.makeText(context, "Checking for"+limit+"collections", Toast.LENGTH_SHORT).show();
                        fetch(limit,s);
                    }
                    else if(s==prefvalue)
                    {
                        not=new Notificationn(context);
                        not.showNotification("message","Found 0 posts!");
                    }
                });
    }

    private void fetch(int limit,int size) {
        db.collection("missing")
                .limit(limit)
                .orderBy("time", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc:queryDocumentSnapshots)
                    {
                        if (doc.exists()) {
                            String name = doc.get("name").toString();
                            db.collection("missing").get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            preferences.setLimit(size);
                                            for (DocumentSnapshot doc:queryDocumentSnapshots)
                                            {
                                            if (doc.exists())
                                            {
                                                String n=doc.get("name").toString();
                                                String status=doc.get("status").toString();
                                                if (n.equals(name) && status.equals("Found"))
                                                {
                                                    list.add(n);
                                                    Toast.makeText(context, "Found Records", Toast.LENGTH_SHORT).show();
                                                    not=new Notificationn(context);
                                                    not.showNotification("message","Found "+list.size() + "Which matches your search i.e" +n);
                                                }
                                            }
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

}
