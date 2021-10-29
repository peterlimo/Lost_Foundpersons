package com.example.lost_foundpersons;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Preferences {
    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public Preferences(Context context) {
        this.context = context;
    }
    public void setLimit(int limit)
    {
    pref=context.getSharedPreferences("user",MODE_PRIVATE);
    editor=pref.edit();
    editor.putInt("limit",limit);
   editor.commit();
    }
    public int getLimit()
    {
     pref=context.getSharedPreferences("user",context.MODE_PRIVATE);
    int n=pref.getInt("limit",0);
    return n;
    }
    public String getUser() {
        pref=context.getSharedPreferences("user",context.MODE_PRIVATE);
        String username=pref.getString("email","");
        return username;
    }
}
