package com.example.safelife;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterLabActivity extends AppCompatActivity {
    EditText edName, edDescription, edMobile, edAddress;
    ImageView labImageView;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private Uri imageUri;
    private Bitmap bitmap;

    String userId;
    int Image_Request_Code = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_lab);
        changeStatusBarColor();

        edName = findViewById(R.id.registerName);
        edDescription = findViewById(R.id.registerDescription);
        edMobile = findViewById(R.id.registerMobile);
        edAddress = findViewById(R.id.registerAddress);
        labImageView = findViewById(R.id.labimageview);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onRegisterClick(View view) {
        if (TextUtils.isEmpty(edName.getText())) {
            edName.setError("Name is required!");
        } else if (TextUtils.isEmpty(edDescription.getText())) {
            edDescription.setError("Description is required!");
        } else if (TextUtils.isEmpty(edMobile.getText())) {
            edMobile.setError("Mobile Number is required!");
        } else if (TextUtils.isEmpty(edAddress.getText())) {
            edAddress.setError("Address is required!");
        } else {
            ProgressDialog progress = new ProgressDialog(this);

            String name = edName.getText().toString().trim();
            String description = edDescription.getText().toString().trim();
            String mobile = edMobile.getText().toString().trim();
            String address = edAddress.getText().toString();
            //String url;

            if (imageUri != null) {
                progress.setMessage("Registering...");
                progress.show();
                StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(imageUri));

                storageReference2.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url = uri.toString();
                                        userId = mAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = db.collection("lab request").document(userId);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("id",userId);
                                        user.put("name", name);
                                        user.put("description", description);
                                        user.put("mobile", mobile);
                                        user.put("address", address);
                                        user.put("imageurl",url);
                                        user.put("status", "pending");

                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progress.dismiss();
                                                Toast.makeText(RegisterLabActivity.this, "Lab Register Successfully!", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(view.getContext(), MainActivity.class));
                                                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                                            }
                                        });
                                    }
                                });

                            }
                        });
            } else {
                Toast.makeText(RegisterLabActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
            }



        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    public void onImageClick(View view) {
        //Toast.makeText(RegisterLabActivity.this, "Image clicked", Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(RegisterLabActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(RegisterLabActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(RegisterLabActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            } else {

                choseImage();

            }
        } else {

            choseImage();

        }
    }

    private void choseImage() {
        //Toast.makeText(RegisterLabActivity.this, "Chose Image", Toast.LENGTH_LONG).show();
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setAspectRatio(1, 1)
//                .start(RegisterLabActivity.this);
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//
//                imageUri = result.getUri();
//                labImageView.setImageURI(imageUri);
//
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//
//                Exception error = result.getError();
//
//            }
//        }

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                labImageView.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}