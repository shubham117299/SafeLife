package com.example.safelife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

public class FragmentMyBookingAdapter extends FirestoreRecyclerAdapter<MyBooking, FragmentMyBookingAdapter.MyBookingHolder> {

    private OnListItemClick onListItemClick;

    public FragmentMyBookingAdapter(FirestoreRecyclerOptions<MyBooking> options,OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick=onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull MyBookingHolder holder, int position, @NonNull @NotNull MyBooking model) {
        holder.labname.setText(model.getLabname());
        holder.testname.setText(model.getBookedtest());
        holder.dateandtime.setText(model.getDateandtime());
    }

    @NonNull
    @NotNull
    @Override
    public MyBookingHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_booking,parent,false);
        return new MyBookingHolder(view);
    }

    public class MyBookingHolder extends RecyclerView.ViewHolder{
        private TextView labname,testname,dateandtime;
        public MyBookingHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            labname=itemView.findViewById(R.id.labname);
            testname=itemView.findViewById(R.id.testname);
            dateandtime=itemView.findViewById(R.id.dateandtime);

            itemView.setOnClickListener(this::onClick);
        }
        public void onClick(View v) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());
        }
    }

    public interface OnListItemClick{
        void onItemClick(MyBooking snapshot, int adapterPosition);
    }
}
