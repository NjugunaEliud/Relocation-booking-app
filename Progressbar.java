package com.example.relo;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

public class Progressbar {
    Activity activity;
    AlertDialog alertDialog;

    public Progressbar(Activity activity) {
        this.activity = activity;
    }
    void  showprogressbar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progressbar,null));
        builder.create();
        alertDialog=builder.show();

    }
    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
