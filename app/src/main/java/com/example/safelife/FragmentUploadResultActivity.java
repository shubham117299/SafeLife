package com.example.safelife;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class FragmentUploadResultActivity extends Fragment {

    private TextView labName,userEmail,bookedTest,dateAndTime,timimg;
    String labname,useremail,bookedtest,dateandtime,time,labid,userid;
    private Button btn;

    private StorageReference storageReference;
    private FirebaseFirestore db;

    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upload_result, parent, false);

        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        labname = getArguments().getString("labname");
        useremail = getArguments().getString("useremail");
        bookedtest = getArguments().getString("bookedtest");
        dateandtime = getArguments().getString("dateandtime");
        time = getArguments().getString("time");
        labid=getArguments().getString("labid");
        userid=getArguments().getString("userid");


        //Toast.makeText(getActivity(), labname, Toast.LENGTH_SHORT).show();
        labName=view.findViewById(R.id.labname);
        userEmail=view.findViewById(R.id.useremail);
        bookedTest=view.findViewById(R.id.bookedtest);
        dateAndTime=view.findViewById(R.id.registertime);
        timimg=view.findViewById(R.id.timing);
        btn=view.findViewById(R.id.cirUploadButton);

        labName.setText("Lab Name : "+labname);
        userEmail.setText("User Email : "+useremail);
        bookedTest.setText("Booked Test : "+bookedtest);
        dateAndTime.setText("Date And Time : "+dateandtime);
        timimg.setText("Time Slot : "+time);


//        db.collection("Lab Booking").document("acHZ02mppH7mVTNHS3xr")
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(getActivity(), "Deleted Successful", Toast.LENGTH_SHORT).show();
//                    }
//                });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDFFile();
            }
        });

        return view;
    }
    public void deletedata(String id){
                db.collection("Lab Booking").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(getActivity(), "Deleted Successful", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void selectPDFFile(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            uploadPDFFile(data.getData());
        }
    }

    private void uploadPDFFile(Uri data) {

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("Uploading...");
        progress.show();
        StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + ".pdf");
        storageReference2.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                DocumentReference documentReference = db.collection("History").document();
                                Map<String, Object> result = new HashMap<>();
                                result.put("pdfurl",url);
                                result.put("labname",labname);
                                result.put("useremail",useremail);
                                result.put("dateandtime",dateandtime);
                                result.put("time",time);
                                result.put("labid",labid);
                                result.put("userid",userid);
                                result.put("bookedtest",bookedtest);
                                documentReference.set(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
//                                        CollectionReference collectionReference = db.collection("Lab Booking");
//                                        Query query = collectionReference.whereEqualTo("userId",userid );
//                                        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                            @Override
//                                            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
//                                                if (task.isSuccessful()) {
//                                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                                        if(document.getData())
//                                                        Toast.makeText(getActivity(), document.getId().toString(), Toast.LENGTH_SHORT).show();
//                                                        Toast.makeText(getActivity(), "hi", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            }
//                                        });
                                        db.collection("Lab Booking")
                                                .whereEqualTo("userId",userid)
                                                .whereEqualTo("dateAndTime",dateandtime)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                                                //Toast.makeText(getActivity(), document.getId().toString(), Toast.LENGTH_SHORT).show();

                                                                String documentid=document.getId();
                                                                Toast.makeText(getActivity(), documentid, Toast.LENGTH_SHORT).show();
//                                                                db.collection("lab Booking").document("oklsqZVkfA4qYl65uZ4z")
//                                                                        .delete()
//                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                            @Override
//                                                                            public void onSuccess(Void unused) {
//                                                                                progress.dismiss();
//                                                                                Toast.makeText(getActivity(), "Uploaded Successful", Toast.LENGTH_SHORT).show();
//                                                                            }
//                                                                        });
                                                                deletedata(documentid);
                                                                progress.dismiss();
                                                               Toast.makeText(getActivity(), "Uploaded Successful", Toast.LENGTH_SHORT).show();
                                                                FragmentLabBooking fragmentLabBooking = new FragmentLabBooking ();
                                                                myContext.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                                                        fragmentLabBooking).commit();
                                                            }
                                                        }

                                                    }
                                                });




                                    }
                                });

                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {

            }
        });

    }
}