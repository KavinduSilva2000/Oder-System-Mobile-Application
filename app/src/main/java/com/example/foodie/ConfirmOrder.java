package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodie.Finaladapter.MyAdapter;
import com.example.foodie.HolderData.CartViewHolder;
import com.example.foodie.HolderData.Lastdata;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import kotlin.LateinitKt;

public class ConfirmOrder extends AppCompatActivity {

    TextView NAME,MOBILE,ADDRESS,Total;

    Button PlaceOrder,back;

    CheckBox checkbox;
    public String Tot="" , OrderDetails="", AccountDetails="";


    RecyclerView RecyclerView;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        NAME = findViewById(R.id.NAME);
        MOBILE = findViewById(R.id.MOBILE);
        ADDRESS = findViewById(R.id.ADDRESS);
        Total = findViewById(R.id.Total);

        PlaceOrder = findViewById(R.id.PlaceOrder);
        checkbox = findViewById(R.id.checkbox);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Cart.class);
                startActivity(intent);
            }
        });

        //Place order Button
        PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Date and time
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String date = simpleDateFormat.format(calendar.getTime());
                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
                String time = simpleTimeFormat.format(calendar.getTime());


                //Log.e("Date", date);
                //Log.e("Time", time);


                //Add data to Database
                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if(checkbox.isChecked()){
                    Toast.makeText(ConfirmOrder.this, "ok", Toast.LENGTH_SHORT).show();

                    //Send data to database
                    //Log.e("OrderDetails", OrderDetails);
                    //Log.e("AccountDetails", AccountDetails);

                    HashMap<String,Object> order = new HashMap<>();
                    order.put("UserID",userID);
                    order.put("Date",date);
                    order.put("Time",time);
                    order.put("Customer",AccountDetails);
                    order.put("Items" , OrderDetails);
                    order.put("Status" , "Process");


                    RootRef.child("Orders").child(date).child(time).updateChildren(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                DatabaseReference removed = FirebaseDatabase.getInstance().getReference()
                                        .child("CartList").child("User_View").child(userID);
                                removed.removeValue();

                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                finish();
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(ConfirmOrder.this, "Error!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else{
                    Toast.makeText(ConfirmOrder.this, "Click Cash Payment", Toast.LENGTH_SHORT).show();
                }




            }
        });





        String User = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference Data =FirebaseDatabase.getInstance().getReference().child("Users").child(User);

        Data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                NAME.setText(snapshot.child("Name").getValue().toString());
                MOBILE.setText(snapshot.child("PhoneNumber").getValue().toString());
                ADDRESS.setText(snapshot.child("Address").getValue().toString());

                Tot = getIntent().getStringExtra("Total");
                Total.setText(Tot);

                AccountDetails = "Name:  "+ snapshot.child("Name").getValue().toString() + System.getProperty("line.separator")
                        +"Phone number:  "+ snapshot.child("PhoneNumber").getValue().toString() + System.getProperty("line.separator")
                        +"Address:  "+ snapshot.child("Address").getValue().toString() + System.getProperty("line.separator")
                        +"Total Price:  "+ Tot ;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }



        });

        //recycle view

        RecyclerView = findViewById(R.id.rv);
        RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        RecyclerView.setLayoutManager(layoutManager);

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("CartList");

        FirebaseRecyclerOptions<Lastdata> options  = new FirebaseRecyclerOptions.Builder<Lastdata>()
                .setQuery(Ref.child("User_View").child(userID).child("Products"), Lastdata.class).build();

        FirebaseRecyclerAdapter<Lastdata, MyAdapter> adapter = new FirebaseRecyclerAdapter<Lastdata, MyAdapter>(options) {



            @Override
            protected void onBindViewHolder(@NonNull MyAdapter myAdapter, int position, @NonNull Lastdata model) {

                myAdapter.quantity.setText(model.getQuantity());
                myAdapter.PrductName.setText(model.getPname());

                OrderDetails = OrderDetails + model.getPname() + " " + model.getQuantity() + System.getProperty("line.separator") ;

                        //Testing
                        Log.e("qua",OrderDetails);


            }


            @NonNull
            @Override
            public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holdfinal, parent, false);
                MyAdapter holder = new MyAdapter(v);
                return holder;
            }
        };


        //Check if user scroll to down of the recycle view
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
                    PlaceOrder.setVisibility(View.VISIBLE);
                }
                else{
                    //Toast.makeText(ConfirmOrder.this, "Not Last Item", Toast.LENGTH_SHORT).show();
                    PlaceOrder.setVisibility(View.INVISIBLE);

                }
            }
        });


        RecyclerView.setAdapter(adapter);
        adapter.startListening();


    }




}