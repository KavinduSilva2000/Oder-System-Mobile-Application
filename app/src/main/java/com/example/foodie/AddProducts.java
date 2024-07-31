package com.example.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AddProducts extends AppCompatActivity {

    ImageButton Pizza,Cakes,Pasta,Snacks,Sweets,Drinks;
    ImageView imageView2;
    Button Orders,Edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        Pizza = findViewById(R.id.Pizza);
        Cakes = findViewById(R.id.Cakes);
        Pasta = findViewById(R.id.Pasta);
        Snacks = findViewById(R.id.Snacks);
        Sweets = findViewById(R.id.Sweets);
        Drinks = findViewById(R.id.Drinks);
        imageView2 = findViewById(R.id.imageView2);
        Orders = findViewById(R.id.Orders);
        Edit = findViewById(R.id.Edit);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        Pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intend = new Intent(AddProducts.this, NewProduct.class);
                intend.putExtra("Category","Pizza");
                startActivity(intend);
            }
        });

        Cakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intend = new Intent(AddProducts.this, NewProduct.class);
                intend.putExtra("Category","Cakes");
                startActivity(intend);
            }
        });

        Pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intend = new Intent(AddProducts.this, NewProduct.class);
                intend.putExtra("Category","Pasta");
                startActivity(intend);
            }
        });

        Snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intend = new Intent(AddProducts.this, NewProduct.class);
                intend.putExtra("Category","Snacks");
                startActivity(intend);
            }
        });

        Sweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intend = new Intent(AddProducts.this, NewProduct.class);
                intend.putExtra("Category","Sweets");
                startActivity(intend);
            }
        });

        Drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intend = new Intent(AddProducts.this, NewProduct.class);
                intend.putExtra("Category","Drinks");
                startActivity(intend);
            }
        });

        Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intend = new Intent(AddProducts.this, Orders.class);
                startActivity(intend);
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intend = new Intent(AddProducts.this, EditProducts.class);
                startActivity(intend);
            }
        });
    }
}