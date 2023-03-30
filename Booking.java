package com.example.relo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.relo.Model.RelocationModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Booking extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    Button submit;
    FirebaseAuth auth;
    TextInputLayout beds, bedq,sofas, sofaq, fridges,fridgeq,tables,tableq,date,helper,addinfo;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        drawerLayout=findViewById(R.id.drawerLayout);
        toolbar=findViewById(R.id.app_toolbar);
        navigationView=findViewById(R.id.nav_container);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        addinfo=findViewById(R.id.textInputLayout12);
        beds=findViewById(R.id.textInputLayout4);
        bedq=findViewById(R.id.bedq);
        sofas=findViewById(R.id.textInputLayout5);
        sofaq=findViewById(R.id.sofaq);
        fridges=findViewById(R.id.textInputLayout6);
        fridgeq=findViewById(R.id.textInputLayout14);
        auth=FirebaseAuth.getInstance();
        tables=findViewById(R.id.textInputLayout7);
        tableq=findViewById(R.id.textInputLayout9);
        date=findViewById(R.id.textInputLayout11);
        helper=findViewById(R.id.textInputLayout10);
        databaseReference= FirebaseDatabase.getInstance().getReference("Relocation Details").child("Relocation Info");
        submit = findViewById(R.id.button5);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bedsize=beds.getEditText().getText().toString();
                int bq=Integer.parseInt(bedq.getEditText().getText().toString());
                String sofasize=sofas.getEditText().getText().toString();
                int sq=Integer.parseInt(sofaq.getEditText().getText().toString());
                String fridgesize=fridges.getEditText().getText().toString();
                int fq=Integer.parseInt(fridgeq.getEditText().getText().toString());
                String tableSiz=tables.getEditText().getText().toString();
                int tq=Integer.parseInt(tableq.getEditText().getText().toString());
                String hel=helper.getEditText().getText().toString();
                String dat=date.getEditText().getText().toString();
                String addinfom=addinfo.getEditText().getText().toString();
                if(bq <=7 && sq<=7 && fq<=7 && tq<=7){

                    AlertDialog.Builder builder=new AlertDialog.Builder(Booking.this);
                    builder.setTitle("PICK UP");
                    builder.setMessage(" According to Your Delivery Quotation Appropriate vehicle to ferry Your HouseHold Commodities is a pick up");
                    builder.setIcon(R.drawable.delivery);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Booking.this,Map.class));

                        }
                    });
                    builder.show();

                }
                else if(bq >7 && bq <=15 && sq>7 && sq<=15 &&fq >7 && fq<=15 &&tq>7 && tq<=15){
                    AlertDialog.Builder builder=new AlertDialog.Builder(Booking.this);
                    builder.setTitle("VAN");
                    builder.setMessage("According to Your Delivery Quotation Appropriate vehicle to ferry Your HouseHold Commodities is a Van");
                    builder.setIcon(R.drawable.delivery);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Booking.this,Map.class));
                        }
                    });
                    builder.show();

                }
                else  if(bq >15 && sq>15 && fq>15 && tq>15){

                    AlertDialog.Builder builder=new AlertDialog.Builder(Booking.this);
                    builder.setTitle("TRUCK");
                    builder.setMessage("According to Your Delivery Quotation Appropriate vehicle to ferry Your HouseHold Commodities Is a Truck");
                    builder.setIcon(R.drawable.delivery);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Booking.this,Map.class));
                        }
                    });
                    builder.show();
                }
                else{
                    insertdata(bedsize,bq,sofasize,sq,fridgesize,fq,tableSiz,tq,hel,dat,addinfom);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.contacts:
                        startActivity(new Intent(Booking.this,MainActivity.class));
                        break;
                    case R.id.boo:
                        startActivity(new Intent(Booking.this,Booking.class));
                        break;
                    case R.id.pin:
                        startActivity(new Intent(Booking.this,Map.class));
                        break;
                    case R.id.logout:
                    logout();
                    break;

                }
                return false;
            }
    });

}

    private void insertdata(String bedsize, int bq, String sofasize, int sq, String fridgesize, int fq, String tableSiz, int tq, String hel, String dat, String addinfom) {
            RelocationModel relocationModel=new RelocationModel(bedsize,bq,sofasize,sq,fridgesize,fq,tableSiz,tq,hel,dat,addinfom);
            databaseReference.push().setValue(relocationModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {


                    Toast.makeText(getApplicationContext(), "Delivery Quotation submitted successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Delivery Quotation Failed to submitted,Retry", Toast.LENGTH_SHORT).show();

                }
            });
        }

    private void logout(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Booking.this);
        builder.setTitle("Logout");
        builder.setIcon(R.drawable.warning);
        builder.setMessage("Are you sure you want to exit this app ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Booking.this,UsersLogin.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    }

