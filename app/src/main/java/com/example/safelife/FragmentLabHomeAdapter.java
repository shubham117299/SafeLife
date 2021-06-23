package com.example.safelife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class FragmentLabHomeAdapter extends FirestoreRecyclerAdapter<UserLabData,FragmentLabHomeAdapter.LabViewHolder> {

    private OnListItemClick onListItemClick;


    public FragmentLabHomeAdapter(@NonNull @NotNull FirestoreRecyclerOptions<UserLabData> options,OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick=onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull LabViewHolder holder, int position, @NonNull @NotNull UserLabData model) {
        //Glide.with(mContext).load(model.getImageurl()).into(holder.labImage);
        Glide.with(holder.itemView.getContext()).load(model.getImageurl()).into(holder.labImage);
        holder.labName.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.address.setText(model.getAddress());

    }

    @NonNull
    @NotNull
    @Override
    public LabViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_raw_item,parent,false);
        return new LabViewHolder(view);
    }

    public class LabViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView labImage;
        private TextView labName,description,address;
        public LabViewHolder(@NonNull View itemView) {
            super(itemView);

            labImage=itemView.findViewById(R.id.labimage);
            labName=itemView.findViewById(R.id.labname);
            description=itemView.findViewById(R.id.description);
            address=itemView.findViewById(R.id.address);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());
        }
    }
    public interface OnListItemClick{
        void onItemClick(UserLabData snapshot, int adapterPosition);
    }
}
