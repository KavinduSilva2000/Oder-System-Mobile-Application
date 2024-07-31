package com.example.foodie.HolderData;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.Interface.ItemClickListner;
import com.example.foodie.R;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView PrductName,quantity,Prices;
    ItemClickListner itemClickListner;

    public Button remove, update;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        PrductName = (TextView) itemView.findViewById(R.id.PrductName);
        quantity = (TextView)itemView.findViewById(R.id.quantity);
        Prices = (TextView) itemView.findViewById(R.id.Prices);

        //Testing

        remove = (Button) itemView.findViewById(R.id.remove);
        update = (Button) itemView.findViewById(R.id.update);


    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
