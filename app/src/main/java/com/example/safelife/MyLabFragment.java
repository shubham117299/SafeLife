package com.example.safelife;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyLabFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private String userId;
    private TextView name,description,address,mobile;
    private ImageView imageView;
    private Button btn;
    //private Spinner spinner;
    TextView timeslot;
    boolean[] selectedslot;
    ArrayList<Integer> timeslotlist=new ArrayList<>();
    String[] slotArray={"8:00-9:00AM","9:00-10:00AM","10:00-11:00AM","11:00-12:00AM","12:00-1:00PM","1:00-2:00PM","2:00-3:00PM","3:00-4:00PM","4:00-5:00PM","5:00-6:00PM","6:00-7:00PM","7:00-8:00PM","8:00-9:00PM","9:00-10:00PM"};

    TextView labtests;
    boolean[] selectedlabtest;
    ArrayList<Integer> labtestlist=new ArrayList<>();
    String[] labtestArray={"CBC / Hemogram Test ","CT Scan ","DNA Test ","ECG ","HIV Test ","Liver Function Test (LFT) ","MRI Scan ","Thyroid Test","X-Ray"};

    Map<String,Object> tslot=new HashMap<>();
    Map<String,Object> ltest=new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_lab, parent, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        name=view.findViewById(R.id.name);
        description=view.findViewById(R.id.description);
        address=view.findViewById(R.id.address);
        mobile=view.findViewById(R.id.mobile);
        imageView=view.findViewById(R.id.labimageview);
        btn=view.findViewById(R.id.cirSaveButton);
        //spinner=view.findViewById(R.id.spinnerview);
        timeslot=view.findViewById(R.id.timeslot);
        labtests=view.findViewById(R.id.labtests);

        userId=mAuth.getCurrentUser().getUid();
        DocumentReference documentReference=firebaseFirestore.collection("lab request").document(userId);
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

//        String[] timeslot={"8:00-9:00","9:00-10:00","10:00-11:00","11:00-12:00","12:00-1:00","1:00-2:00","2:00-3:00","3:00-4:00","4:00-5:00","5:00-6:00","6:00-7:00","7:00-8:00","8:00-9:00","9:00-10:00"};
//        ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,timeslot);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(arrayAdapter);

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
                                tslot.put(slotArray[timeslotlist.get(j)],"Available");
                                //Toast.makeText(getActivity(),slotArray[timeslotlist.get(j)].toString(), Toast.LENGTH_LONG).show();

                                if(j!=timeslotlist.size()-1){
                                    stringBuilder.append(", ");
                                }
                        }
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
                            ltest.put(labtestArray[labtestlist.get(j)],"Available");

                            if(j!=labtestlist.size()-1){
                                stringBuilder.append(", ");
                            }
                        }
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Clicked", Toast.LENGTH_LONG).show();
                DocumentReference documentReference2 = firebaseFirestore.collection("TimeSlot").document(userId);
                documentReference2.set(tslot).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(),"Successfully set Time", Toast.LENGTH_LONG).show();
                        tslot.clear();
                    }
                });
                DocumentReference documentReference3 = firebaseFirestore.collection("LabTests").document(userId);
                documentReference3.set(ltest).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(),"Successfully set Lab", Toast.LENGTH_LONG).show();
                        ltest.clear();
                    }
                });
            }
        });

        return view;
    }

}
