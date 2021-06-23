package com.example.safelife;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FragmentMyBooking extends Fragment implements FragmentMyBookingAdapter.OnListItemClick {

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FragmentMyBookingAdapter adapter;
    private FirebaseAuth mAuth;

    String userId;

    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_booking, parent, false);


        recyclerView=view.findViewById(R.id.recyclerviewmybooking);
        firebaseFirestore=FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        userId=mAuth.getCurrentUser().getUid();

        CollectionReference collectionReference = firebaseFirestore.collection("History");
        Query query = collectionReference.whereEqualTo("userid", userId);

        FirestoreRecyclerOptions<MyBooking> options = new FirestoreRecyclerOptions.Builder<MyBooking>()
                .setQuery(query, MyBooking.class)
                .build();

        adapter=new FragmentMyBookingAdapter(options,this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
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

    @Override
    public void onItemClick(MyBooking snapshot, int adapterPosition) {
        Toast.makeText(getActivity(), "Item Clicked"+" "+adapterPosition, Toast.LENGTH_SHORT).show();
        FragmentMyResult fragmentMyResult = new FragmentMyResult ();
        Bundle args = new Bundle();
        args.putString("labname", snapshot.getLabname());
        args.putString("useremail", snapshot.getUseremail());
        args.putString("bookedtest", snapshot.getBookedtest());
        args.putString("dateandtime", snapshot.getDateandtime());
        args.putString("time", snapshot.getTime());
        args.putString("labid", snapshot.getLabid());
        args.putString("userid", snapshot.getUserid());
        args.putString("pdfurl", snapshot.getPdfurl());
        fragmentMyResult.setArguments(args);
        myContext.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragmentMyResult).commit();
    }
}
