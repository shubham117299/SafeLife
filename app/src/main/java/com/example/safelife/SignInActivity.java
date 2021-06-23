package com.example.safelife;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;

public class SignInActivity extends AppCompatActivity {

    EditText edEmail , edPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_sign_in);

        edEmail=findViewById(R.id.editEmail);
        edPassword=findViewById(R.id.editPassword);
    }
    public void onLoginClick(View view){
        if( TextUtils.isEmpty(edEmail.getText())){
             edEmail.setError( "Email is required!" );
        }else if( TextUtils.isEmpty(edPassword.getText())){
            edPassword.setError( "Password is required!" );
        }else{
                ProgressDialog progress=new ProgressDialog(this);
                progress.setMessage("Login...");
                progress.show();
                ParseUser.logInInBackground(edEmail.getText().toString(), edPassword.getText().toString(), (parseUser, e) -> {
                    progress.dismiss();
                    if (parseUser != null) {
                        Toast.makeText(SignInActivity.this, "Welcome back!", Toast.LENGTH_LONG).show();
                    } else {
                        ParseUser.logOut();
                        Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        }
    }
    public void onRegisterClick(View View){
        startActivity(new Intent(this,SignUpActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
    public void onForgot(View view){
        startActivity(new Intent(this,ResetPassword.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}