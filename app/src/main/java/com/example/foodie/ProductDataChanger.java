package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodie.HolderData.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProductDataChanger extends AppCompatActivity {

    ImageView Product_image;
    TextView Product_name,Product_description,Product_price;

    Button button4,back;

    String productID,Imagelink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_data_changer);

        Product_image=findViewById(R.id.Product_image);
        Product_name=findViewById(R.id.Product_name);
        Product_description=findViewById(R.id.Product_description);
        Product_price=findViewById(R.id.Product_price);
        button4=findViewById(R.id.button4);
        back=findViewById(R.id.back);
        productID=getIntent().getStringExtra("Pid");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProducts.class);
                startActivity(intent);
            }
        });

        ProductDetails(productID);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference remove = FirebaseDatabase.getInstance().getReference().child("Foods").child(productID);
                Log.e("asa",productID);
                StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(Imagelink);
                photoRef.delete();
                remove.removeValue();

                Intent intent = new Intent(getApplicationContext(), EditProducts.class);
                finish();
                startActivity(intent);


            }
        });

    }

    private void ProductDetails(String productID) {

        DatabaseReference DataRef = FirebaseDatabase.getInstance().getReference().child("Foods");

        DataRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    Products products = snapshot.getValue(Products.class);

                    Product_name.setText(products.getFoodname());
                    Product_description.setText(products.getDiscription());
                    Product_price.setText(products.getPrice());
                    Imagelink =  products.getImage();
                    Picasso.get().load(products.getImage()).into(Product_image);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}