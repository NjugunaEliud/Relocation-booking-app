package com.example.relo.Model;

import android.icu.util.Calendar;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.relo.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Rate extends FirebaseRecyclerAdapter<RecomendRecycler,Rate.myviewholder> {


    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());


    public Rate(@NonNull FirebaseRecyclerOptions<RecomendRecycler> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull RecomendRecycler model) {
        holder.Comment.setText(model.getReview());
        holder.Date.setText(currentDate);

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rate,parent,false);
        return  new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
      TextView Comment,Date;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            Comment=itemView.findViewById(R.id.textView25);
            Date=itemView.findViewById(R.id.textView22);

        }
    }



}
