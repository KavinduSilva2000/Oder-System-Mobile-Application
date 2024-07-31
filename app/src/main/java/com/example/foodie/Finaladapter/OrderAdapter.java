package com.example.foodie.Finaladapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.Interface.ItemClickListner;
import com.example.foodie.R;

public class OrderAdapter extends RecyclerView.ViewHolder implements android.view.View.OnClickListener {

    public static TextView dis,Accountdetails,useid,dates,times,ststus;
    public static  Button Delivered;

    ItemClickListner itemClickListner;

    public OrderAdapter(@NonNull android.view.View itemView) {
        super(itemView);

        dis = (TextView)itemView.findViewById(R.id.discription);
        Accountdetails = (TextView)itemView.findViewById(R.id.Accountdetails);
        useid = (TextView)itemView.findViewById(R.id.useid);
        dates = (TextView)itemView.findViewById(R.id.dates);
        times = (TextView)itemView.findViewById(R.id.times);
        ststus = (TextView)itemView.findViewById(R.id.ststus);
        Delivered = (Button) itemView.findViewById(R.id.Delivered);
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner){
        this.itemClickListner = itemClickListner;
    }
}
