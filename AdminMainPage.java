package com.example.relo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.relo.Model.Drivers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class    AdminMainPage extends AppCompatActivity {
    TextInputLayout name, reg, contact;
    Button upload;
    Uri filePath;
    FirebaseStorage firebaseStorage;
    DatabaseReference databaseReference,databaseReferences;
    TextInputLayout truck ,licence, passport,conduct,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);

        name = findViewById(R.id.textInputLayout15);
        reg = findViewById(R.id.textInputLayout16);
        type=findViewById(R.id.textInputLayout2);
        upload = findViewById(R.id.button7);
        truck = findViewById(R.id.textInputLayout18);
        contact = findViewById(R.id.textInputLayout17);
        firebaseStorage = FirebaseStorage.getInstance();
        databaseReferences = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Available Drivers");
//        upload.setEnabled(false);
        licence=findViewById(R.id.textInputLayout21);
        passport=findViewById(R.id.textInputLayout22);
        conduct=findViewById(R.id.textInputLayout23);
        conduct.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SelectConduct();
            }
        });
        passport.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SelectPassport();
            }
        });
        licence.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SelectDL();
            }
        });



        truck.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });





    }
    private void SelectDL()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select file from here..."),
                12);
    }
    private void SelectPassport()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select file from here..."),
                13);
    }
    private void SelectConduct()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select file from here..."),
                14);
    }

    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select file from here..."),
                15);
    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == 15
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            truck.getEditText().setText(filePath.toString());
            conduct.getEditText().setText(filePath.toString());
            passport.getEditText().setText(filePath.toString());
            licence.getEditText().setText(filePath.toString());
            upload.setEnabled(true);
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String drivers_name = name.getEditText().getText().toString();
                    String registration = reg.getEditText().getText().toString();
                    String telephone = contact.getEditText().getText().toString();
                    String vehicletype=type.getEditText().getText().toString();
                    String telephoneprefix = "+254" + telephone;

                    if (drivers_name.isEmpty()) {
                        name.setError("*This Field is required");
                    } else if (registration.isEmpty()) {
                        reg.setError("*This Field is required");
                    } else if (telephone.isEmpty()) {
                        contact.setError("*This Field is required");
                    }
                    else{
                        uploadImage(drivers_name,registration,telephone,vehicletype);
                    }
                }
            });

        }
    }
    // UploadImage method
    private void uploadImage(String name,String reg,String tel,String type) {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = firebaseStorage.getReference()
                    .child(
                            "documents/"
                                    + System.currentTimeMillis());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {


                                    String uld = taskSnapshot.getStorage().getDownloadUrl().toString();
                                    Drivers drivers = new Drivers(name,reg,tel,uld,type);
                                    databaseReference.push().setValue(drivers);
                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    AlertDialog.Builder builder = new
                                            AlertDialog.Builder(AdminMainPage.this);
                                    builder.setTitle("Adding  a Driver ");
                                    builder.setMessage("Driver uploaded Successfully");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new
                                    AlertDialog.Builder(AdminMainPage.this);
                            builder.setTitle("Uploading a Driver");
                            builder.setMessage("Something wrong happened , Retry");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                     dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }


    }







}


