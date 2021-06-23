package com.example.safelife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ResetPassword extends AppCompatActivity {

    EditText edEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edEmail=findViewById(R.id.editEmail);
    }
    public void onSubmit(View view){
        if( TextUtils.isEmpty(edEmail.getText())){
            edEmail.setError( "Email is required!" );
        }else {
            ParseUser.requestPasswordResetInBackground(edEmail.getText().toString(), new RequestPasswordResetCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // An email was successfully sent with reset instructions.
                        Toast.makeText(ResetPassword.this, "An email was successfully sent with reset instructions.", Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(this,SignInActivity.class));
//                        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                    } else {
                        // Something went wrong. Look at the ParseException to see what's up.
                        Toast.makeText(ResetPassword.this, "Something went wrong. Look at the ParseException to see what's up.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    public void onLoginClick(View View){
        startActivity(new Intent(this,SignInActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }
}