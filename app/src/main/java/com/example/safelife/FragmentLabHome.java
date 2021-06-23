package com.example.safelife;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FragmentLabHome extends Fragment implements FragmentLabHomeAdapter.OnListItemClick {

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FragmentLabHomeAdapter adapter;
    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lab_home, parent, false);

        recyclerView=view.findViewById(R.id.recyclerviewlab);
        firebaseFirestore=FirebaseFirestore.getInstance();

        Query query=firebaseFirestore.collection("lab request");

        FirestoreRecyclerOptions<UserLabData> options = new FirestoreRecyclerOptions.Builder<UserLabData>()
                .setQuery(query, UserLabData.class)
                .build();

        adapter=new FragmentLabHomeAdapter(options,this);

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
    public void onItemClick(UserLabData snapshot, int position) {
        //Toast.makeText(getActivity(), "Item Clicked "+position, Toast.LENGTH_SHORT).show();
        //FragmentManager fragManager = myContext.getFragmentManager();
        FragmentBookingLabActivity fragmentBookingLabActivity = new FragmentBookingLabActivity ();
        Bundle args = new Bundle();
        args.putString("Id", snapshot.getId());
        fragmentBookingLabActivity.setArguments(args);
        myContext.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragmentBookingLabActivity).commit();
    }
}