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

public class MatchedAdapter extends RecyclerView.Adapter<MatchedAdapter.ViewHolder> {
    private List<Match> UserList;
    private Context context;
    OnItemClickListener listener;
    public MatchedAdapter(List<Match>UserList,Context context,OnItemClickListener listener){
        this.listener=listener;
        this.UserList=UserList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
View view;
        LayoutInflater inflater=LayoutInflater.from(context);
        if (viewType==1){
            view=inflater.inflate(R.layout.miss_item_layout,parent,false);
        }
        else
        {
            view=inflater.inflate(R.layout.list_match_items,parent,false);
        }
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchedAdapter.ViewHolder holder, int position) {
        String name=UserList.get(position).getName();
        String age=UserList.get(position).getAge();
        String location=UserList.get(position).getLocation();
        String gender=UserList.get(position).getGender();
        String status=UserList.get(position).getStatus();
        String image=UserList.get(position).getUrl();
        holder.setData(name,age,gender);
        holder.SetImage(image);
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  tv_name,tv_age,tv_gender;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.match_name);
            tv_age=itemView.findViewById(R.id.match_age);
            tv_gender=itemView.findViewById(R.id.match_gender);
            itemView.setOnClickListener(this);
        }

        public void setData(String name, String age,String gender){
            tv_name.setText(name);
            tv_age.setText(age);
            tv_gender.setText(gender);
        }
        public void SetImage(String image){
            ImageView imageView=itemView.findViewById(R.id.match_image);
            Picasso.get().load(image).into(imageView);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition(),v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (UserList.get(position).type){
            return 1;
        }
        else{ return 2;}

    }

    public interface OnItemClickListener{
        void onItemClick(int position, View v);
    }
}
