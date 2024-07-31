package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodie.Finaladapter.EditAdapter;
import com.example.foodie.HolderData.EditHolder;
import com.example.foodie.HolderData.ProductHolder;
import com.example.foodie.HolderData.Products;
import com.example.foodie.databinding.ActivityHomeBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class EditProducts extends AppCompatActivity {

    EditText searchText;
    Button button9;

    DatabaseReference reference;
    Query query;
    RecyclerView recycleView;
    RecyclerView.LayoutManager layoutManager;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);

        reference = FirebaseDatabase.getInstance().getReference().child("Foods");
        query = reference.orderByChild("Category");

        recycleView = findViewById(R.id.rv);
        recycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);

        button9 = findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProducts.class);

                startActivity(intent);
            }
        });


        searchText = findViewById(R.id.searchText);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String value = searchText.getText().toString();
                //String value = val.toUpperCase(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
                Log.e("text", value);
                query = reference.orderByChild("Foodname").startAt(value);
                onStart();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Intent intent = new Intent(getApplicationContext(), AddProducts.class);
                startActivity(intent);
            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<EditHolder> options = new FirebaseRecyclerOptions.Builder<EditHolder>()
                .setQuery(query,EditHolder.class).build();

        FirebaseRecyclerAdapter<EditHolder, EditAdapter> adapter = new FirebaseRecyclerAdapter<EditHolder, EditAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull EditAdapter holder, int position, @NonNull EditHolder model) {
                holder.PName.setText(model.getFoodname());



                Picasso.get()
                        .load(model.getImage())
                        .resize(200, 200)
                        .placeholder(R.drawable.buy_3595628)
                        .error(R.drawable.delivery_3595516)
                        .into(holder.Pimage, new Callback() {
                            @Override
                            public void onSuccess() {
                                // Image loaded successfully
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e("PicassoError", "Error loading image: " + e.getMessage());
                            }
                        });


                //holder.PDiscription.setText(model.getDiscription());
                holder.PPrice.setText("Price: " + model.getPrice() + " Rs");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ProductDataChanger.class);
                        intent.putExtra("Pid",model.getPid());
                        //Testing
                        //Log.e("Product id", "Id is" + model.getPid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public EditAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.editcard,parent,false);
                EditAdapter holder = new EditAdapter(v);
                return holder;

            }
        };
        recycleView.setAdapter(adapter);
        adapter.startListening();
    }
}