package com.example.safelife;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FragmentLabRequest extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lab_request, parent, false);

        recyclerView=view.findViewById(R.id.recyclerviewlabrequest);
        firebaseFirestore=FirebaseFirestore.getInstance();


        Query query=firebaseFirestore.collection("lab request");

        FirestoreRecyclerOptions<UserLabRequest> options = new FirestoreRecyclerOptions.Builder<UserLabRequest>()
                .setQuery(query, UserLabRequest.class)
                .build();

        adapter=new FirestoreRecyclerAdapter<UserLabRequest, FragmentLabRequest.LabRequestHolder>(options) {


            @NonNull
            @Override
            public LabRequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_lab_request,parent,false);
                return new LabRequestHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull LabRequestHolder holder, int position, @NonNull UserLabRequest model) {
                holder.labName.setText(model.getName());
                holder.description.setText(model.getDescription());
                holder.address.setText(model.getAddress());
                //holder.button.setText(model.getStatus());

            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        return view;
    }
    private class LabRequestHolder extends RecyclerView.ViewHolder{
        private TextView labName,description,address;
        //private Button button;
        public LabRequestHolder(@NonNull View itemView) {
            super(itemView);
            labName=itemView.findViewById(R.id.labname);
            description=itemView.findViewById(R.id.description);
            address=itemView.findViewById(R.id.address);
            //button=itemView.findViewById(R.id.statusbutton);
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