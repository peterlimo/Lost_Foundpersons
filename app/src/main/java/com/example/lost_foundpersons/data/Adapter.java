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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<MissData> UserList;
    private Context context;
    private OnItemClickListener listener;
    public Adapter(List<MissData>UserList,Context context,OnItemClickListener listener){

        this.UserList=UserList;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.records_design,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String name=UserList.get(position).getName();
        String age=UserList.get(position).getAge();
        String location=UserList.get(position).getLocation();
        String gender=UserList.get(position).getGender();
        String status=UserList.get(position).getStatus();
//        String status=UserList.get(position).getStatus();
        String image=UserList.get(position).getUrl();
        holder.setData(name,location,age,gender,status);
        holder.SetImage(image);
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView  tv_name,tv_age,tv_location,tv_gender,tv_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.miss_name);
            tv_age=itemView.findViewById(R.id.miss_age);
            tv_location=itemView.findViewById(R.id.miss_lo);
            tv_gender=itemView.findViewById(R.id.miss_gender);
            tv_status=itemView.findViewById(R.id.miss_status);
            itemView.setOnClickListener(this);
        }

        public void setData(String name,String location, String age,String gender, String status){
            tv_name.setText(name);
            tv_age.setText(age);
            tv_location.setText(location);
            tv_gender.setText(gender);
            tv_status.setText(status);
        }
        public void SetImage(String image){
            ImageView imageView=itemView.findViewById(R.id.imageview);
            Picasso.get().load(image).into(imageView);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition(),v);
        }

    }
   public interface OnItemClickListener{
        void onItemClick(int position,View v);
   }
}
