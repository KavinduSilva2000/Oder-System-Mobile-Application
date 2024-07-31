package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.foodie.Finaladapter.HistoryAdapter;
import com.example.foodie.Finaladapter.OrderAdapter;
import com.example.foodie.HolderData.HistoryData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class History extends AppCompatActivity {


    androidx.recyclerview.widget.RecyclerView RecyclerViews;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference RootRef;
    Query q;

    ImageButton imageButton5,imageButton4, imageButton3,imageButton6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        RecyclerViews = findViewById(R.id.rv);
        RecyclerViews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        RecyclerViews.setLayoutManager(layoutManager);

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
                startActivity(intent);
            }
        });
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                finish();
                startActivity(intent);
                finish();
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


    }

    @Override
    protected void onStart() {
        super.onStart();
        RootRef = FirebaseDatabase.getInstance().getReference();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(calendar.getTime());
        String key = date ;

        String Usr  = FirebaseAuth.getInstance().getUid();

        q = RootRef.child("Orders").child(key).orderByChild("UserID").equalTo(
                Usr);
        FirebaseRecyclerOptions<HistoryData> options = new FirebaseRecyclerOptions.Builder<HistoryData>()
                .setQuery(q, HistoryData.class).build();

        FirebaseRecyclerAdapter<HistoryData,HistoryAdapter> adapter = new FirebaseRecyclerAdapter<HistoryData, HistoryAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HistoryAdapter holder, int position, @NonNull HistoryData model) {

                HistoryAdapter.Accountdetails.setText(model.getCustomer());
                HistoryAdapter.dates.setText(model.getDate());
                HistoryAdapter.times.setText(model.getTime());
                HistoryAdapter.discription.setText(model.getItems());
            }

            @NonNull
            @Override
            public HistoryAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.historycard,parent,false);
                HistoryAdapter holder = new HistoryAdapter(v);
                return holder;
            }
        };




        RecyclerViews.setAdapter(adapter);
        adapter.startListening();

    }
}