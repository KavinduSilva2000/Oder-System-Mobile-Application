package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    EditText Name,Address,Phone_no,E_mail;
    Button button;
    ImageView logout;

    ImageButton imageButton3, imageButton4, imageButton6 ,imageButton5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Name=findViewById(R.id.editText);
        Address=findViewById(R.id.editText2);
        Phone_no=findViewById(R.id.editText3);
        logout = findViewById(R.id.logout);
        button=findViewById(R.id.button);

        //Display user information

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("UserID", userID);
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if(datasnapshot.exists()){
                    String name = datasnapshot.child("Name").getValue().toString();
                    Name.setText(name);


                    Phone_no.setText(datasnapshot.child("PhoneNumber").getValue().toString());
                    Address.setText(datasnapshot.child("Address").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Log.d("UserID", userID);
                DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                UserRef.child("Name").setValue(Name.getText().toString());
                UserRef.child("Address").setValue(Address.getText().toString());

                UserRef.child("PhoneNumber").setValue(Phone_no.getText().toString());
            }
        });


        //Navigation
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton6);

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Cart.class);
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


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
            }
        });

    }



}