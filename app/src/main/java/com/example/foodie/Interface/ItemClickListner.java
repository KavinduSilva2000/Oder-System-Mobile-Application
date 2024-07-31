package com.example.foodie.Interface;

import com.google.firebase.database.core.view.View;

public interface ItemClickListner {

    void onClick(View view, int position, boolean isLongClick);


    void onClick(android.view.View view, int adapterPosition, boolean isLongClick);
}
