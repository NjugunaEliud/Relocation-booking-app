package com.example.relo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Admin extends AppCompatActivity {
    TextInputLayout email;
    TextInputLayout password;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    Button btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        email = findViewById(R.id.textInputLayout);
        password = findViewById(R.id.textInputLayout8);
        btnAdmin = findViewById(R.id.button5);
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaill = email.getEditText().getText().toString();
                String passwordA = password.getEditText().getText().toString();
                if (emaill.isEmpty()) {
                    email.setError("This Field is Required");
                } else {
                    email.setError("");
                }
                if (passwordA.isEmpty()) {
                    password.setError("This Field is Required");
                } else {
                    password.setError("");
                }
                if (!emaill.isEmpty() && !passwordA.isEmpty()) {
                    signinadmin(emaill, passwordA);

                }
            }
        });
    }

    private void signinadmin(String emaill, String passwordA) {
        auth.signInWithEmailAndPassword(emaill, passwordA).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                checkIfAdmin(authResult.getUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(Admin.this);
                builder.setTitle("Login");
                builder.setIcon(Admin.this
                        .getDrawable(R.drawable.warning));
                builder.setMessage("Login failed, Retry");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });


    }

    private void checkIfAdmin(String uid) {
        DocumentReference documentReference = firebaseFirestore.
                collection("User's info").document(uid);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.getString("isAdmin") != null) {
                    final  Progressbar Progressbar= new Progressbar(Admin.this);
                    Progressbar.showprogressbar();
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Progressbar.showprogressbar();
                            startActivity(new Intent(Admin.this,AdminMainPage.class));
                            Progressbar.dismissDialog();
                        }
                    },4000);;
                } else {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(Admin.this);
                    builder.setTitle("Login");
                    builder.setIcon(Admin.this
                            .getDrawable(R.drawable.warning));
                    builder.setMessage("Login failed, Retry");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();


                }


            }
        });
    }
}