package com.example.safelife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safelife.adapter.RecyclerviewAdapter;
import com.example.safelife.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView userRecycler;
    RecyclerviewAdapter recyclerviewAdapter;

    LayoutInflater inflater;
    ViewGroup parent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        this.inflater=inflater;
        this.parent=parent;

        View view = inflater.inflate(R.layout.fragment_test_home, parent, false);


        List<UserData> userDataList=new ArrayList<>();
        userDataList.add(new UserData("Laboratory1","Modern Lab","Malaviya Nagar Alwar (Rajasthan)", R.drawable.lab1));
        userDataList.add(new UserData("Laboratory2","Modern Lab","Malaviya Nagar Alwar (Rajasthan)", R.drawable.lab1));
        userDataList.add(new UserData("Laboratory3","Modern Lab","Malaviya Nagar Alwar (Rajasthan)", R.drawable.lab1));
        userDataList.add(new UserData("Laboratory4","Modern Lab","Malaviya Nagar Alwar (Rajasthan)", R.drawable.lab1));
        userDataList.add(new UserData("Laboratory5","Modern Lab","Malaviya Nagar Alwar (Rajasthan)", R.drawable.lab_test1));
        //setUserRecycler(userDataList);
        userRecycler=view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        userRecycler.setLayoutManager(layoutManager);
        recyclerviewAdapter=new RecyclerviewAdapter(getActivity(),userDataList);
        userRecycler.setAdapter(recyclerviewAdapter);
        return view;
    }

    private void setUserRecycler(List<UserData> userDataList){

        View view = inflater.inflate(R.layout.fragment_test_home, parent, false);
        userRecycler=view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        userRecycler.setLayoutManager(layoutManager);
        recyclerviewAdapter=new RecyclerviewAdapter(getActivity(),userDataList);
        userRecycler.setAdapter(recyclerviewAdapter);
    }

}
