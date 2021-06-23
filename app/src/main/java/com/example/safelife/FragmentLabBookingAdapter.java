package com.example.safelife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class FragmentLabBookingAdapter  extends FirestoreRecyclerAdapter<UserLabBooking, FragmentLabBookingAdapter.LabBookingHolder>{

    private OnListItemClick onListItemClick;

    public FragmentLabBookingAdapter(FirestoreRecyclerOptions<UserLabBooking> options, OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick=onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull LabBookingHolder holder, int position, @NonNull @NotNull UserLabBooking model) {
        holder.labTest.setText(model.getLabTest());
        holder.userEmail.setText(model.getUserEmail());
        holder.dateAndTime.setText(model.getDateAndTime());
    }

    @NonNull
    @NotNull
    @Override
    public LabBookingHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_lab_booking,parent,false);
        return new LabBookingHolder(view);
    }

    public class LabBookingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView labTest,dateAndTime,userEmail;
        public LabBookingHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            labTest=itemView.findViewById(R.id.labtest);
            dateAndTime=itemView.findViewById(R.id.useremail);
            userEmail=itemView.findViewById(R.id.dateandtime);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());
        }
    }
    public interface OnListItemClick{
        void onItemClick(UserLabBooking snapshot, int adapterPosition);
    }
}
