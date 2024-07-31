package com.example.foodie.HolderData;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.Interface.ItemClickListner;
import com.example.foodie.R;

public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView PName, PDiscription, PPrice;
    public ImageView Pimage;
    public ItemClickListner listner;
    public ProductHolder(@NonNull View itemView) {
        super(itemView);

        PName = (TextView) itemView.findViewById(R.id.PName);
        Pimage = (ImageView) itemView.findViewById(R.id.Pimage);
        PPrice = (TextView) itemView.findViewById(R.id.PPrice);
        //PDiscription = (TextView) itemView.findViewById(R.id.PDiscription);

    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }



}
