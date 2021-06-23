package com.example.safelife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class FragmentMyStatus extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseAuth mAuth;

    String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_status, parent, false);

        recyclerView=view.findViewById(R.id.recyclerviewmystatus);
        firebaseFirestore=FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        userId=mAuth.getCurrentUser().getUid();

        CollectionReference collectionReference = firebaseFirestore.collection("Lab Booking");
        Query query = collectionReference.whereEqualTo("userId", userId);

        FirestoreRecyclerOptions<UserMyStatus> options=new FirestoreRecyclerOptions.Builder<UserMyStatus>()
                .setQuery(query,UserMyStatus.class)
                .build();

        adapter= new FirestoreRecyclerAdapter<UserMyStatus, MyStatusViewHolder>(options) {
            @NonNull
            @NotNull
            @Override
            public MyStatusViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_status,parent,false);
                return new MyStatusViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull @NotNull MyStatusViewHolder holder, int position, @NonNull @NotNull UserMyStatus model) {

                holder.labName.setText(model.getLabName());
                holder.bookedtest.setText(model.getLabTest());
                holder.dateandtime.setText(model.getDateAndTime());
            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }
    private class MyStatusViewHolder extends RecyclerView.ViewHolder{

        private TextView labName,bookedtest,dateandtime;

        public MyStatusViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            labName=itemView.findViewById(R.id.labname);
            bookedtest=itemView.findViewById(R.id.bookedtest);
            dateandtime=itemView.findViewById(R.id.dateandtime);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}