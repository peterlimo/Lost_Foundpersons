package com.example.lost_foundpersons.data;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lost_foundpersons.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_allrecords extends RecyclerView.Adapter<Adapter_allrecords.ViewHolder> {
    private List<MissData> UserList;
    private Context context;
    public Adapter_allrecords(List<MissData>UserList,Context context){

        this.UserList=UserList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.records_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name=UserList.get(position).getName();
        String age=UserList.get(position).getAge();
        String location=UserList.get(position).getLocation();
        String gender=UserList.get(position).getGender();
        String status=UserList.get(position).getStatus();
        String image=UserList.get(position).getUrl();
        holder.setData(name,age,location,gender,status);
        holder.SetImage(context,image);

    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name,tv_age,tv_location,tv_gender,tv_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name=itemView.findViewById(R.id.miss_name);
            //  textView2=itemView.findViewById(R.id.textView2);
            tv_age=itemView.findViewById(R.id.miss_age);
            tv_location=itemView.findViewById(R.id.miss_status);
            tv_gender=itemView.findViewById(R.id.miss_status);
            tv_status=itemView.findViewById(R.id.miss_status);

        }

        public void setData(String s, String name, String age, String status, String gender){

            tv_name.setText(name);
            tv_age.setText(age);
            tv_location.setText(status);
            tv_gender.setText(gender);
            tv_status.setText(status);
        }
        public void SetImage(final Context c,final String image){
            ImageView imageView=itemView.findViewById(R.id.imageview);
            Picasso.get().load(image).into(imageView);
        }
    }
}



