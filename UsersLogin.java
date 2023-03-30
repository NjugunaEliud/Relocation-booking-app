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
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class UsersLogin extends AppCompatActivity {
    TextView signup;
    TextInputLayout pass,email;
    Button btnLogin;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_login);
        signup=findViewById(R.id.textView10);
        email=findViewById(R.id.textInputLayout);
        pass=findViewById(R.id.textInputLayout3);
        btnLogin=findViewById(R.id.button2);
        firebaseFirestore=FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UsersLogin.this,Register.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String passwordA=pass.getEditText().getText().toString();
                String emaill=email.getEditText().getText().toString();

                if(passwordA.isEmpty()){
                    pass.setError("EmailAddress Required");

                }
                else{
                    pass.setError("");
                }
                if(emaill.isEmpty()){
                    email.setError("");
                }
                if(!passwordA.isEmpty() && !emaill.isEmpty()){

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
                        new AlertDialog.Builder(UsersLogin.this);
                builder.setTitle("Login");
                builder.setIcon(UsersLogin.this
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

                if (documentSnapshot.getString("isUser") != null) {
                    final  Progressbar Progressbar= new Progressbar(UsersLogin.this);
                    Progressbar.showprogressbar();
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Progressbar.showprogressbar();
                            startActivity(new Intent(UsersLogin.this,MainActivity.class));
                            Progressbar.dismissDialog();
                        }
                    },4000);;
                } else {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(UsersLogin.this);
                    builder.setTitle("Login");
                    builder.setIcon(R.drawable.warning);
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