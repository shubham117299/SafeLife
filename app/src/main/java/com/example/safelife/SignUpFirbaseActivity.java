package com.example.safelife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.HashMap;
import java.util.Map;

public class SignUpFirbaseActivity extends AppCompatActivity {
    EditText edName ,edEmail,edMobile ,edPassword;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_firbase);
        changeStatusBarColor();

        edName=findViewById(R.id.editName);
        edEmail=findViewById(R.id.editEmail);
        edMobile=findViewById(R.id.editMobile);
        edPassword=findViewById(R.id.editPassword);

        mAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }
    public void onRegisterClick(View view){
        if( TextUtils.isEmpty(edName.getText())){
            edName.setError( "Name is required!" );
        }else if(!Patterns.EMAIL_ADDRESS.matcher(edEmail.getText()).matches()){
            edEmail.setError( "Enter valid email address!" );
        }else if( TextUtils.isEmpty(edEmail.getText())){
            edEmail.setError( "Email is required!" );
        }else if( TextUtils.isEmpty(edMobile.getText())){
            edMobile.setError( "Mobile Number is required!" );
        } else if( TextUtils.isEmpty(edPassword.getText())){
            edPassword.setError( "Password is required!" );
        }else if(edPassword.getText().length()<6){
            edPassword.setError( "Minimum length of Password should be 6 !" );
        }else{
            ProgressDialog progress=new ProgressDialog(this);
            progress.setMessage("Signing Up...");
            progress.show();
            String name=edName.getText().toString().trim();
            String email=edEmail.getText().toString().trim();
            String mobile=edMobile.getText().toString().trim();
            String password=edPassword.getText().toString();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progress.dismiss();
                    if (task.isSuccessful()) {
                        //Toast.makeText(SignUpFirbaseActivity.this, "User Register Successful!", Toast.LENGTH_LONG).show();
                        userId=mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference=db.collection("users").document(userId);
                        Map<String,Object> user=new HashMap<>();
                        user.put("Name",name);
                        user.put("Email",email);
                        user.put("Mobile",mobile);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SignUpFirbaseActivity.this, "User Register Successful!", Toast.LENGTH_LONG).show();
                            }
                        });

                        startActivity(new Intent(view.getContext(),SignInFirbaseActivity.class));
                        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

                    } else {
                        Toast.makeText(SignUpFirbaseActivity.this, task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
    public void onLoginClick(View view){
        startActivity(new Intent(this,SignInFirbaseActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }
}