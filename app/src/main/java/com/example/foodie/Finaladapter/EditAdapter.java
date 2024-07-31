package com.example.foodie.Finaladapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.Interface.ItemClickListner;
import com.example.foodie.R;

public class EditAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView PName, PDiscription, PPrice;
    public ImageView Pimage;
    public ItemClickListner listner;
    public EditAdapter(@NonNull View itemView) {
        super(itemView);

        PName = (TextView) itemView.findViewById(R.id.PName);
        Pimage = (ImageView) itemView.findViewById(R.id.Pimage);
        PPrice = (TextView) itemView.findViewById(R.id.PPrice);
    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
