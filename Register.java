package com.example.relo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.relo.Model.RegisterModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
TextView backlogin;
    AppCompatTextView log;
    TextInputLayout names;
    TextInputLayout email;
    TextInputLayout phone;
    TextInputLayout passc;
    TextInputLayout pass;
    Button btnreg;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    StorageReference firebaseStorage;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        backlogin=findViewById(R.id.textView7);
        firebaseFirestore = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Relocation Details").child("Users Details");
        firebaseStorage = FirebaseStorage.getInstance().getReference();
        names = findViewById(R.id.textInputLayout80);
        email = findViewById(R.id.textInputLayout4);
        auth = FirebaseAuth.getInstance();
        phone = findViewById(R.id.textInputLayout5);
        pass = findViewById(R.id.textInputLayout6);
        passc = findViewById(R.id.textInputLayout7);
        btnreg=findViewById(R.id.button2);
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emails = email.getEditText().getText().toString();
                String password = pass.getEditText().getText().toString();
                String passwordc = passc.getEditText().getText().toString();
                String Phonenum = phone.getEditText().getText().toString();
                String fullname = names.getEditText().getText().toString();
                if(fullname.isEmpty()){names.setError("field required");}
                else{names.setError("");}
                if(emails.isEmpty()){email.setError("field required");}
                else{email.setError("");}
                if(password.isEmpty()){pass.setError("field required");}
                else{pass.setError("");}
                if(Phonenum.isEmpty()){phone.setError("field required");}
                else{phone.setError("");}
                if(passwordc.isEmpty()){passc.setError("field required");}
                else{passc.setError(""); }
                if(!password.equals(passwordc)){passc.setError("password didn't match");}
                else{passc.setError("");}
                if (password.length()<8){pass.setError("Password Should be At least 8 characters");}
                if(!emails.isEmpty() && !password.isEmpty() && !passwordc.isEmpty() && !Phonenum.isEmpty()&& !fullname.isEmpty() && password.equals(passwordc))  {
                    create(emails, password);

                    insertdata(emails, Phonenum,fullname);
                }
            }
        });


        backlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,UsersLogin.class));
            }
        });
    }

    private void insertdata(String emails, String phonenum, String fullname) {
        RegisterModel registerModel=new RegisterModel(emails,phonenum,fullname);
        databaseReference.push().setValue(registerModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Data Insertion Successful",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data Insertion Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void create(String emails, String password) {
        String Phonenum = phone.getEditText().getText().toString();
        String fullname = names.getEditText().getText().toString();
        auth.createUserWithEmailAndPassword(emails,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                final  Progressbar Progressbar= new Progressbar(Register.this);
                Progressbar.showprogressbar();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Progressbar.showprogressbar();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                        builder.setTitle("Registration");
                        builder.setIcon(Register.this.getDrawable(R.drawable.success));
                        builder.setMessage("Registration successful, you can login");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                startActivity(new Intent(Register.this,UsersLogin.class));
                                finish();

                            }

                        });
                        builder.show();
                    }
                },4000);
                FirebaseUser user = auth.getCurrentUser();
                DocumentReference documentReference = firebaseFirestore.collection("User's info")
                        .document(user.getUid());
                Map<String,Object> userdat = new HashMap<>();
                userdat.put("Email",emails);
                userdat.put("Full name",fullname);
                userdat.put("Phone number",Phonenum);
                userdat.put("isUser","1");
                documentReference.set(userdat);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                final  Progressbar Progressbar= new Progressbar(Register.this);
                Progressbar.showprogressbar();
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Progressbar.showprogressbar();
                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                        builder.setTitle("Registration");
                        builder.setIcon(Register.this.getDrawable(R.drawable.success));
                        builder.setMessage("Registration Failed, Retry");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();


                            }

                        });
                        builder.show();
                    }
                },4000);
            }
        });
    }
}