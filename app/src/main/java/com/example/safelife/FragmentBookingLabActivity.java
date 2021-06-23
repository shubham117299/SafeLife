package com.example.safelife;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FragmentBookingLabActivity extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private String userId;
    private String id;
    private TextView name,description,address,mobile;
    private ImageView imageView;
    private Button btn;



    TextView timeslot;
    boolean[] selectedslot;
    ArrayList<Integer> timeslotlist=new ArrayList<>();
    String[] slotArray;
    ArrayList<String> test=new ArrayList<>();
    //Integer slotArraySize;
    StringBuilder stringBuilder2=new StringBuilder();

    TextView labtests;
    boolean[] selectedlabtest;
    ArrayList<Integer> labtestlist=new ArrayList<>();
    String[] labtestArray;
    ArrayList<String> test2=new ArrayList<>();

    StringBuilder stringBuilder3=new StringBuilder();

    String labName;

    private FragmentActivity myContext;
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_lab, parent, false);
        String value = getArguments().getString("Id");
        Toast.makeText(getActivity(),value , Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        //Toast.makeText(getActivity(),firebaseUser.getEmail().toString(), Toast.LENGTH_LONG).show();

        firebaseFirestore=FirebaseFirestore.getInstance();

        name=view.findViewById(R.id.name);
        description=view.findViewById(R.id.description);
        address=view.findViewById(R.id.address);
        mobile=view.findViewById(R.id.mobile);
        imageView=view.findViewById(R.id.labimageview);
        btn=view.findViewById(R.id.cirBookButton);

        timeslot=view.findViewById(R.id.timeslot);
        labtests=view.findViewById(R.id.labtests);

        userId=value;
        id=mAuth.getCurrentUser().getUid();
        DocumentReference documentReference=firebaseFirestore.collection("lab request").document(value);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Glide.with(getActivity()).load(document.getString("imageurl")).into(imageView);
                        name.setText(document.getString("name"));
                        description.setText(document.getString("description"));
                        address.setText(document.getString("address"));
                        mobile.setText(document.getString("mobile"));
                        labName=document.getString("name");
                        //userId=document.getId();
                        //Toast.makeText(getActivity(), String.valueOf(document.getData().size()), Toast.LENGTH_LONG).show();
//                        Map<String, Object> m=document.getData();
//                        for (String variableName : m.keySet())
//                        {
//                            String variableKey = variableName;
//                            //String variableValue = (String) m.get(variableName);
//                            Toast.makeText(getActivity(), String.valueOf(variableKey), Toast.LENGTH_LONG).show();
//
//                        }

                    } else {
                        //Log.d(TAG, "No such document");
                        Toast.makeText(getActivity(), "No such document", Toast.LENGTH_LONG).show();

                    }
                } else {
                    // Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
//       test.add("8-9");
        readData(new FirestoreCallBack() {
            @Override
            public void onCallBack() {
                //Toast.makeText(getActivity(), "First", Toast.LENGTH_LONG).show();
                slotArray=new String[test.size()];
                for(int i=0;i<test.size();i++){
                    slotArray[i]=test.get(i);
                    //Toast.makeText(getActivity(), slotArray[i].toString(), Toast.LENGTH_LONG).show();
                }


                selectedslot=new boolean[slotArray.length];
                timeslot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getActivity(),"Reaching", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        builder.setTitle("Chose Time Slot");
                        builder.setCancelable(false);
                        builder.setMultiChoiceItems(slotArray, selectedslot, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                                if(isChecked){
                                    timeslotlist.add(i);
                                    Collections.sort(timeslotlist);
                                }else{
                                    for(int j=0;j<timeslotlist.size();j++){
                                        if(timeslotlist.get(j)==i) {
                                            timeslotlist.remove(j);
                                        }
                                    }


                                }
                            }
                        });
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                StringBuilder stringBuilder=new StringBuilder();
                                for(int j=0;j<timeslotlist.size();j++){
                                    stringBuilder.append(slotArray[timeslotlist.get(j)]);
                                    //Toast.makeText(getActivity(),slotArray[timeslotlist.get(j)].toString(), Toast.LENGTH_LONG).show();

                                    if(j!=timeslotlist.size()-1){
                                        stringBuilder.append(", ");
                                    }
                                }
                                stringBuilder2=stringBuilder;
                                timeslot.setText(stringBuilder.toString());
                            }
                        });

                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int j=0;j<selectedslot.length;j++){
                                    selectedslot[j]=false;
                                    timeslotlist.clear();
                                    timeslot.setText("");
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }
        });

        readData2(new FirestoreCallBack2() {
            @Override
            public void onCallBack2() {

                labtestArray=new String[test2.size()];
                for(int i=0;i<test2.size();i++){
                    labtestArray[i]=test2.get(i);
                    //Toast.makeText(getActivity(), slotArray[i].toString(), Toast.LENGTH_LONG).show();
                }

                selectedlabtest=new boolean[labtestArray.length];
                labtests.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getActivity(),"Reaching", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        builder.setTitle("Chose Lab Tests");
                        builder.setCancelable(false);
                        builder.setMultiChoiceItems(labtestArray, selectedlabtest, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                                if(isChecked){
                                    labtestlist.add(i);
                                    Collections.sort(labtestlist);
                                }else{
                                    for(int j=0;j<labtestlist.size();j++){
                                        if(labtestlist.get(j)==i) {
                                            labtestlist.remove(j);
                                        }
                                    }


                                }
                            }
                        });
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                StringBuilder stringBuilder=new StringBuilder();
                                for(int j=0;j<labtestlist.size();j++){
                                    stringBuilder.append(labtestArray[labtestlist.get(j)]);

                                    if(j!=labtestlist.size()-1){
                                        stringBuilder.append(", ");
                                    }
                                }
                                stringBuilder3=stringBuilder;
                                labtests.setText(stringBuilder.toString());
                            }
                        });

                        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                for(int j=0;j<selectedlabtest.length;j++){
                                    selectedlabtest[j]=false;
                                    labtestlist.clear();
                                    labtests.setText("");
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }
        });
        //Toast.makeText(getActivity(), "Second", Toast.LENGTH_LONG).show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();

                //Date currentTime = Calendar.getInstance().getTime();
                String currentDateTime = java.text.DateFormat.getDateTimeInstance().format(new Date());
                //Toast.makeText(getActivity(), currentDateTime.toString(), Toast.LENGTH_LONG).show();
                ProgressDialog progress = new ProgressDialog(getActivity());
                progress.setMessage("Booking...");
                progress.show();
                DocumentReference documentReference = firebaseFirestore.collection("Lab Booking").document();
                Map<String,Object> data=new HashMap<>();
                data.put("labId",value);
                data.put("timing",stringBuilder2.toString());
                data.put("labTest",stringBuilder3.toString());
                data.put("dateAndTime",currentDateTime);
                data.put("userId",id);
                data.put("labName",labName);
                data.put("status","pending");
                data.put("userEmail",firebaseUser.getEmail().toString());
                documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Booking Successful", Toast.LENGTH_LONG).show();
                        FragmentLabHome fragmentLabHome = new FragmentLabHome();
                        myContext.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                fragmentLabHome).commit();
                    }
                });

            }
        });

        return view;
    }

    public void readData(FirestoreCallBack firestoreCallBack){

        DocumentReference documentReference2=firebaseFirestore.collection("TimeSlot").document(userId);
        documentReference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> m=document.getData();

                        //Toast.makeText(getActivity(), String.valueOf(slotArraySize), Toast.LENGTH_LONG).show();

                        for (String variableName : m.keySet())
                        {
                            String variableKey = variableName;
                            //Toast.makeText(getActivity(), test.get(0), Toast.LENGTH_LONG).show();
                            //Toast.makeText(getActivity(), String.valueOf(slotArray[j]), Toast.LENGTH_LONG).show();
                            test.add(variableName);
                            //String variableValue = (String) m.get(variableName);


                        }
                        firestoreCallBack.onCallBack();
                        //Toast.makeText(getActivity(), String.valueOf(document.getData().size()), Toast.LENGTH_LONG).show();

                    } else {
                        //Log.d(TAG, "No such document");
                        Toast.makeText(getActivity(), "No such document", Toast.LENGTH_LONG).show();

                    }
                } else {
                    // Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void readData2(FirestoreCallBack2 firestoreCallBack2){
        DocumentReference documentReference3=firebaseFirestore.collection("LabTests").document(userId);
        documentReference3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> m=document.getData();

                        //Toast.makeText(getActivity(), String.valueOf(slotArraySize), Toast.LENGTH_LONG).show();

                        for (String variableName : m.keySet())
                        {
                            String variableKey = variableName;
                            //Toast.makeText(getActivity(), test.get(0), Toast.LENGTH_LONG).show();
                            //Toast.makeText(getActivity(), String.valueOf(slotArray[j]), Toast.LENGTH_LONG).show();
                            test2.add(variableName);
                            //String variableValue = (String) m.get(variableName);


                        }
                        firestoreCallBack2.onCallBack2();
                        //Toast.makeText(getActivity(), String.valueOf(document.getData().size()), Toast.LENGTH_LONG).show();

                    } else {
                        //Log.d(TAG, "No such document");
                        Toast.makeText(getActivity(), "No such document", Toast.LENGTH_LONG).show();

                    }
                } else {
                    // Log.d(TAG, "get failed with ", task.getException());
                    Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private interface FirestoreCallBack{
        void onCallBack();
    }

    private interface FirestoreCallBack2{
        void onCallBack2();
    }
}