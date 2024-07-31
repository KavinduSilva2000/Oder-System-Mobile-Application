package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;

import com.example.foodie.Finaladapter.MyAdapter;
import com.example.foodie.Finaladapter.OrderAdapter;
import com.example.foodie.HolderData.Lastdata;
import com.example.foodie.HolderData.OrderData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Orders extends AppCompatActivity {

    Switch toggle;
    DatePicker picker;
    String years,day,month;
    public String key="";
    androidx.recyclerview.widget.RecyclerView RecyclerViews;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference RootRef;
    Query query;
    Button button8;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);


        //final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        //String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        toggle = findViewById(R.id.toggle);
        picker = findViewById(R.id.picker);
        button8 = findViewById(R.id.button8);

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProducts.class);
                startActivity(intent);
            }
        });

        if(toggle.isChecked()){
            picker.setVisibility(View.VISIBLE);

        }else{
            picker.setVisibility(View.INVISIBLE);

        }

        toggle.setOnCheckedChangeListener((new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    picker.setVisibility(View.VISIBLE);
                    RecyclerViews.setVisibility(View.INVISIBLE);

                } else {
                    picker.setVisibility(View.INVISIBLE);
                    RecyclerViews.setVisibility(View.VISIBLE);
                }
            }
        }));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(calendar.getTime());
        key = date ;


        Log.e("key",key);


        picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                monthOfYear = monthOfYear+1;

                if(monthOfYear<10){
                    month = "0" + Integer.toString(monthOfYear);
                }else {
                    month = Integer.toString(monthOfYear);
                }
                if(dayOfMonth<10){
                    day = "0" + Integer.toString(dayOfMonth);
                }else{
                    day = Integer.toString(dayOfMonth);
                }

                key = day + "-" + month + "-" + Integer.toString(year);

                query = RootRef.child("Orders").child(key);
                onStart();

            }

        });




    }

    @Override
    protected void onStart() {
        super.onStart();

        //Display data
        query = RootRef.child("Orders").child(key);


        RecyclerViews = findViewById(R.id.recyclerView);
        RecyclerViews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        RecyclerViews.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<OrderData> options = new FirebaseRecyclerOptions.Builder<OrderData>()
                .setQuery(query, OrderData.class).build();


        FirebaseRecyclerAdapter<OrderData, OrderAdapter> adapter = new FirebaseRecyclerAdapter<OrderData, OrderAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderAdapter holder, int position, @NonNull OrderData model) {

                OrderAdapter.dis.setText(model.getItems());
                Log.e("dis", "ss");
                OrderAdapter.Accountdetails.setText(model.getCustomer());
                OrderAdapter.useid.setText(model.getUserID());
                OrderAdapter.dates.setText(model.getDate());
                OrderAdapter.times.setText(model.getTime());
                OrderAdapter.ststus.setText(model.getStatus());

                OrderAdapter.Delivered.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Orders").child(key).child(model.getTime());
                        root.removeValue();

                        Intent intent = new Intent(getApplicationContext() , Orders.class);
                        finish();
                        startActivity(intent);

                    }
                });

            }

            @NonNull
            @Override
            public OrderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordercard, parent, false);
                OrderAdapter holder = new OrderAdapter(v);
                return holder;
            }
        };

        RecyclerViews.setAdapter(adapter);
        adapter.startListening();

    }
}