package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodie.HolderData.CartData;
import com.example.foodie.HolderData.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cart extends AppCompatActivity {

    RecyclerView RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth auth;
    Button Purchace;
    TextView Totalprice,textView21;

    private String Pid="";
    int TotalPrice =0;

    ImageButton imageButton5,imageButton4,imageButton3,imageButton6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        Purchace = findViewById(R.id.Purchace);
        Totalprice = findViewById(R.id.Totalprice);

        RecyclerView = findViewById(R.id.cardlist);
        RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        RecyclerView.setLayoutManager(layoutManager);
        Purchace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TotalPrice == 0){
                    Toast.makeText(Cart.this, "Cart empty", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    finish();
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), ConfirmOrder.class);
                    intent.putExtra("Total", Integer.toString(TotalPrice) );
                    finish();
                    startActivity(intent);
                }

            }
        });

        imageButton5 = findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });
        imageButton4 = findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Cart.class);
                finish();
                startActivity(intent);
            }
        });
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });
        imageButton6= findViewById(R.id.imageButton6);
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), History.class);
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = (LinearLayoutManager) RecyclerView.getLayoutManager();
        RecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                // Check if the last visible item is the last item in the list
                if (lastVisibleItem == totalItemCount - 1) {
                    // User has scrolled to the end of the RecyclerView
                    // Add your logic here
                    //Toast.makeText(ConfirmOrder.this, "Last Item", Toast.LENGTH_SHORT).show();
                    Purchace.setVisibility(View.VISIBLE);
                    Totalprice.setVisibility(View.VISIBLE);
                    //textView21.setVisibility(View.INVISIBLE);

                }
                else{
                    //Toast.makeText(ConfirmOrder.this, "Not Last Item", Toast.LENGTH_SHORT).show();
                    Purchace.setVisibility(View.INVISIBLE);
                    Totalprice.setVisibility(View.INVISIBLE);
                   // textView21.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference Cref = FirebaseDatabase.getInstance().getReference().child("CartList");

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.e("userID", "userID" + userID);
        FirebaseRecyclerOptions<CartData> options = new FirebaseRecyclerOptions.Builder<CartData>()
                .setQuery(Cref.child("User_View").child(userID).child("Products"), CartData.class).build();


        FirebaseRecyclerAdapter<CartData , CartViewHolder> adapter = new FirebaseRecyclerAdapter<CartData, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull CartData model) {

                holder.quantity.setText(model.getQuantity());
                holder.PrductName.setText(model.getPname());
                holder.Prices.setText(model.getPprice());

                int price = (Integer.valueOf(model.getPprice()))*(Integer.valueOf(model.getQuantity()));

                TotalPrice = price+TotalPrice;
                Totalprice.setText(Integer.toString(TotalPrice));
                //Testing
                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cref.child("Admin_View").child(userID).child("Products").child(model.getPid()).child("Status").setValue("Removed from cart");

                        Cref.child("User_View").child(userID).child("Products").child(model.getPid()).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            Toast.makeText(Cart.this, "Successfully Removed", Toast.LENGTH_SHORT).show();

                                            //Intent intent = new Intent(getApplicationContext(), Cart.class );
                                            Intent intent = new Intent(getApplicationContext(), Cart.class);
                                            finish();
                                            startActivity(intent);


                                        }else{
                                            Toast.makeText(Cart.this, "Error!!!", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                    }
                });

                //Testing

                Pid = getIntent().getStringExtra("Pid");
                holder.update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), DisplayProduct.class);
                        intent.putExtra("Pid",model.getPid());
                        finish();
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent , false);
                CartViewHolder holder = new CartViewHolder(v);
                return holder;


            }
        };



        RecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}