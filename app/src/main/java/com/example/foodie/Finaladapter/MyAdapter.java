package com.example.foodie.Finaladapter;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.Interface.ItemClickListner;
import com.example.foodie.R;

public class MyAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView PrductName,quantity;

    ItemClickListner itemClickListner;

    public MyAdapter(@NonNull View itemView) {
        super(itemView);

        PrductName = (TextView)itemView.findViewById(R.id.PrductName);
        quantity = (TextView)itemView.findViewById(R.id.quantity);
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner){
        this.itemClickListner = itemClickListner;
    }
}
