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

public class FragmentLabBooking extends Fragment implements FragmentLabBookingAdapter.OnListItemClick {

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FragmentLabBookingAdapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_lab_booking, parent, false);

        recyclerView=view.findViewById(R.id.recyclerviewlabbooking);
        firebaseFirestore=FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        userId=mAuth.getCurrentUser().getUid();

        CollectionReference collectionReference = firebaseFirestore.collection("Lab Booking");
        Query query = collectionReference.whereEqualTo("labId", userId);



        FirestoreRecyclerOptions<UserLabBooking> options = new FirestoreRecyclerOptions.Builder<UserLabBooking>()
                .setQuery(query, UserLabBooking.class)
                .build();

        adapter=new FragmentLabBookingAdapter(options,this);

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
    public void onItemClick(UserLabBooking snapshot, int position) {
        Toast.makeText(getActivity(), "Item Clicked"+" "+position, Toast.LENGTH_SHORT).show();
        FragmentUploadResultActivity fragmentUploadResultActivity = new FragmentUploadResultActivity ();
        Bundle args = new Bundle();
        args.putString("labname", snapshot.getLabName());
        args.putString("useremail", snapshot.getUserEmail());
        args.putString("bookedtest", snapshot.getLabTest());
        args.putString("dateandtime", snapshot.getDateAndTime());
        args.putString("time", snapshot.getTiming());
        args.putString("labid", snapshot.getLabId());
        args.putString("userid", snapshot.getUserId());
        fragmentUploadResultActivity.setArguments(args);
        myContext.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragmentUploadResultActivity).commit();
    }
}
