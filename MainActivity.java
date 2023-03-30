package com.example.relo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.relo.Model.Drivers;
import com.example.relo.Model.DriversAdapter;
import com.example.relo.Model.DriversREcycler;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    RecyclerView recyclerView;
    DriversAdapter driversAdapter;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        databaseReference= FirebaseDatabase.getInstance().getReference("Available Drivers");
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        FirebaseRecyclerOptions<DriversREcycler> option =
                new FirebaseRecyclerOptions.Builder<DriversREcycler>().setQuery(databaseReference,DriversREcycler.class).build();
        driversAdapter = new DriversAdapter(option);
        recyclerView.setAdapter(driversAdapter);


        drawerLayout=findViewById(R.id.drawerLayout);
        toolbar=findViewById(R.id.app_toolbar);
        navigationView=findViewById(R.id.nav_container);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.contacts:
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                    break;
                    case R.id.boo:
                        startActivity(new Intent(MainActivity.this,Booking.class));
                        break;
                    case R.id.pin:
                        startActivity(new Intent(MainActivity.this,Map.class));
                        break;
                    case R.id.logout:
                        logout();
                        break;
                    case R.id.rate:
                        startActivity(new Intent(MainActivity.this,RateRecomend.class));
                        break;
                }
                return false;
            }
        });

    }
    private void logout(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity .this);
        builder.setTitle("Logout");
        builder.setIcon(R.drawable.warning);
        builder.setMessage("Are you sure you want to exit this app ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,UsersLogin.class));
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
    @Override
    public void onStart() {
        super.onStart();
        driversAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        driversAdapter.stopListening();
    }
}