package com.example.safelife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parse.ParseUser;

public class SignInFirbaseActivity extends AppCompatActivity {
    EditText edEmail , edPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_sign_in_firbase);
        edEmail=findViewById(R.id.editEmail);
        edPassword=findViewById(R.id.editPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    public void onLoginClick(View view){
        if( TextUtils.isEmpty(edEmail.getText())){
            edEmail.setError( "Email is required!" );
        }else if(!Patterns.EMAIL_ADDRESS.matcher(edEmail.getText()).matches()){
            edEmail.setError( "Enter valid email address!" );
        }else if( TextUtils.isEmpty(edPassword.getText())){
            edPassword.setError( "Password is required!" );
        }else{
            ProgressDialog progress=new ProgressDialog(this);
            progress.setMessage("Login...");
            progress.show();

            mAuth.signInWithEmailAndPassword(edEmail.getText().toString(),edPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progress.dismiss();
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(SignInFirbaseActivity.this, "Welcome back!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(view.getContext(),MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(SignInFirbaseActivity.this, "Authentication failed! Enter Correct username or password.",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                private void updateUI(FirebaseUser user) {
                }
            });

        }
    }
    public void onRegisterClick(View View){
        startActivity(new Intent(this,SignUpFirbaseActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
    public void onForgot(View view){
        startActivity(new Intent(this,ResetPassword.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}