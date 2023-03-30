package com.example.relo.Model;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.relo.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class DriversAdapter extends FirebaseRecyclerAdapter<DriversREcycler,DriversAdapter.myviewholder> {

    public DriversAdapter(@NonNull FirebaseRecyclerOptions<DriversREcycler> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull DriversREcycler model) {
        holder.textView1.setText(model.getName());
        holder.textView2.setText(model.getRegistration());
        holder.textView3.setText(model.getTel_number());
        holder.textView5.setText(model.getVehicleType());
        Glide.with(holder.imageView).load(model.getFilepath()).into(holder.imageView);

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drivers,parent,false);
        return new myviewholder(v);
    }

    public  class myviewholder extends RecyclerView.ViewHolder{
      TextView textView1,textView2,textView3,textView5;
      ImageView imageView;
      public myviewholder(@NonNull View itemView) {
          super(itemView);
          textView1 = itemView.findViewById(R.id.textView5);
          textView2 = itemView.findViewById(R.id.textView16);
          textView3 = itemView.findViewById(R.id.textView18);
          textView5 = itemView.findViewById(R.id.textView20);
          imageView=itemView.findViewById(R.id.imageView3);
      }
  }

}
