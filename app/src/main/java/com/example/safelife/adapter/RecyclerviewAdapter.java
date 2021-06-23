package com.example.safelife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safelife.HomeFragment;
import com.example.safelife.R;
import com.example.safelife.model.UserData;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.RecyclerviewHolder> {
    Context context;
    List<UserData> userDataList;

    public RecyclerviewAdapter(Context context, List<UserData> userDataList) {
        this.context = context;
        this.userDataList = userDataList;
    }

//    public RecyclerviewAdapter(HomeFragment homeFragment, List<UserData> userDataList) {
//        this.context = homeFragment;
//        this.userDataList = userDataList;
//    }


    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_raw_item,parent,false);
        return new RecyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder holder, int position) {

        holder.labName.setText(userDataList.get(position).getLabName());
        holder.labImage.setImageResource(userDataList.get(position).getLabImage());
        //holder.labImage.setImageResource(R.drawable.lab_test2);
        holder.description.setText(userDataList.get(position).getDescription());
        holder.address.setText(userDataList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public static final class RecyclerviewHolder extends RecyclerView.ViewHolder{
        ImageView labImage;
        TextView labName,description,address;

        public RecyclerviewHolder(@NonNull View itemView) {
            super(itemView);

            labImage=itemView.findViewById(R.id.labimage);
            labName=itemView.findViewById(R.id.labname);
            description=itemView.findViewById(R.id.description);
            address=itemView.findViewById(R.id.address);



        }
    }
}
