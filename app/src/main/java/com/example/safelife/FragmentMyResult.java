package com.example.safelife;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentMyResult extends Fragment {

    private TextView labName,userEmail,bookedTest,dateAndTime,timimg;
    private String labname,useremail,bookedtest,dateandtime,time,labid,userid,pdfurl;
    private Button btn;
    private Button download;

    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_result, parent, false);
        //Toast.makeText(getActivity(), getArguments().getString("labname"), Toast.LENGTH_SHORT).show();

        labname = getArguments().getString("labname");
        useremail = getArguments().getString("useremail");
        bookedtest = getArguments().getString("bookedtest");
        dateandtime = getArguments().getString("dateandtime");
        time = getArguments().getString("time");
        labid=getArguments().getString("labid");
        userid=getArguments().getString("userid");
        pdfurl=getArguments().getString("pdfurl");

        labName=view.findViewById(R.id.labname);
        userEmail=view.findViewById(R.id.useremail);
        bookedTest=view.findViewById(R.id.bookedtest);
        dateAndTime=view.findViewById(R.id.registertime);
        timimg=view.findViewById(R.id.timing);
        btn=view.findViewById(R.id.cirResultButton);
        download=view.findViewById(R.id.cirDownloadButton);

        labName.setText("Lab Name : "+labname);
        userEmail.setText("User Email : "+useremail);
        bookedTest.setText("Booked Test : "+bookedtest);
        dateAndTime.setText("Date And Time : "+dateandtime);
        timimg.setText("Time Slot : "+time);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                FragmentPdfView fragmentPdfView = new FragmentPdfView ();
                Bundle args = new Bundle();
                args.putString("pdfurl", pdfurl);

                fragmentPdfView.setArguments(args);
                myContext.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragmentPdfView).commit();
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pdfurl));
                startActivity(intent);
            }
        });
        return view;
    }
}