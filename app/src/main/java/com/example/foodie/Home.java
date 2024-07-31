package com.example.foodie;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.foodie.HolderData.ProductHolder;
import com.example.foodie.HolderData.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.databinding.ActivityHomeBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

import java.util.Locale;

public class Home extends AppCompatActivity {

    DatabaseReference ProductRef;
    RecyclerView recycleView;
    RecyclerView.LayoutManager layoutManager;
    Query querysss;

    Button btn1,btn2,btn3,btn4,btn5,btn6;
    EditText searchText;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;


    ImageButton imageButton5,imageButton4, imageButton3,imageButton6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //ProductRef = FirebaseDatabase.getInstance().getReference().child("Foods");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Foods");
        querysss = ProductRef.orderByChild("Category");

        //Filter butterns


        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
/*
        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        recycleView = findViewById(R.id.recycler_menu);
        recycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);

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

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querysss = ProductRef.orderByChild("Category").equalTo("Pizza");
                onStart();

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querysss = ProductRef.orderByChild("Category").equalTo("Pasta");
                onStart();

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querysss = ProductRef.orderByChild("Category").equalTo("Snacks");
                onStart();

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querysss = ProductRef.orderByChild("Category").equalTo("Cakes");
                onStart();

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querysss = ProductRef.orderByChild("Category").equalTo("Sweets");
                onStart();

            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querysss = ProductRef.orderByChild("Category").equalTo("Drinks");
                onStart();

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
                querysss = ProductRef.orderByChild("Foodname").startAt(value);
                onStart();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/


    @Override
    protected void onStart() {
        super.onStart();



        FirebaseRecyclerOptions<Products> options= new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(querysss, Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull Products model) {



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
                            //Testing
                        //Log.e("Product id", "Id is" + model.getPid());

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), DisplayProduct.class);
                                    intent.putExtra("Pid",model.getPid());
                                    //Testing
                                    //Log.e("Product id", "Id is" + model.getPid());
                                    startActivity(intent);
                                }
                            });
                        }


                        //Picasso.get().load(model.getImage()).into(holder.Pimage);




                    @NonNull
                    @Override
                    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_display, parent, false);
                        ProductHolder holder = new ProductHolder(view);
                        return holder;
                    }


                };
        recycleView.setAdapter(adapter);
        adapter.startListening();

    }
}

