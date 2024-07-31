package com.example.foodie.Finaladapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.Interface.ItemClickListner;
import com.example.foodie.R;

public class HistoryAdapter extends RecyclerView.ViewHolder implements android.view.View.OnClickListener{

    public static TextView discription,Accountdetails,dates,times;

    ItemClickListner itemClickListner;
    public HistoryAdapter(@NonNull android.view.View itemView) {
        super(itemView);

        discription = (TextView)itemView.findViewById(R.id.discription);
        Accountdetails = (TextView)itemView.findViewById(R.id.Accountdetails);
        dates = (TextView)itemView.findViewById(R.id.dates);
        times = (TextView)itemView.findViewById(R.id.times);

    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }
    public void setItemClickListner(ItemClickListner itemClickListner){
        this.itemClickListner = itemClickListner;
    }
}
