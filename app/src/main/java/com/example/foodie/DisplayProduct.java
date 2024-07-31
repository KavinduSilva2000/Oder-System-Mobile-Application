package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodie.HolderData.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DisplayProduct extends AppCompatActivity {

    ImageView Product_image;
    TextView Product_name,Product_description,Product_price,textView6,TotalPrice;

    Button button4,Add,Remove,back;

    int Count=1, tot;
    String productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product);

        Product_image=findViewById(R.id.Product_image);
        Product_name=findViewById(R.id.Product_name);
        Product_description=findViewById(R.id.Product_description);
        Product_price=findViewById(R.id.Product_price);
        textView6=findViewById(R.id.textView6);
        button4=findViewById(R.id.button4);
        Remove=findViewById(R.id.button5);
        Add=findViewById(R.id.button6);
        back=findViewById(R.id.back);
        TotalPrice= findViewById(R.id.TotalPrice);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        productID=getIntent().getStringExtra("Pid");
        //Testing
        //Log.e("Product id", "Id is" + getIntent().getStringExtra("Pid"));

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Count <10){
                    Count = Count+1;

                    tot=Integer.parseInt(Product_price.getText().toString())*Count;
                    //tot=10*Count;
                    TotalPrice.setText(Integer.toString(tot));
                    textView6.setText(Integer.toString(Count));



                }else{
                    Toast.makeText(DisplayProduct.this, "Maximum Count is 10", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Count >1){
                    Count = Count-1;
                    tot=Integer.parseInt(Product_price.getText().toString())*Count;
                    //tot=10*Count;
                    TotalPrice.setText(Integer.toString(tot));
                    textView6.setText(Integer.toString(Count));
                }else{
                    Toast.makeText(DisplayProduct.this, "Minimum Count is 1", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ProductDetails(productID);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat date = new SimpleDateFormat("MMM dd, yyy");
                String Cdate = date.format(calendar.getTime());
                SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss a");
                String Ctime = time.format(calendar.getTime());

                String key = Cdate + Ctime;


                DatabaseReference cartdata = FirebaseDatabase.getInstance().getReference().child("CartList");

                HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("pid",productID);
                cartMap.put("pname",Product_name.getText().toString());
                cartMap.put("pprice",Product_price.getText().toString());
                cartMap.put("date",Cdate);
                cartMap.put("time",Ctime);
                cartMap.put("quantity",Integer.toString(Count));

                //Testing
                cartMap.put("Status","Add to cart");

                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                cartdata.child("User_View").child(userID).child("Products").child(productID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            cartdata.child("Admin_View").child(userID).child("Products").child(productID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(DisplayProduct.this, "Added to cart Successfull", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), Home.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }

                    }
                });


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

                    Picasso.get().load(products.getImage()).into(Product_image);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}