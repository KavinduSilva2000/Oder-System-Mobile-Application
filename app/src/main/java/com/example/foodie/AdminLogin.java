package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {

    EditText  email,password;
    Button Back , Login;

    FirebaseAuth auth;
    public String c = "" ,check="" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Back = findViewById(R.id.Back);
        Login = findViewById(R.id.Login);

        auth = FirebaseAuth.getInstance();


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if( TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)  ){

                    Toast.makeText(getApplicationContext(), "Fields are empty!!!", Toast.LENGTH_SHORT).show();
                }
                else{

                    auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if(task.isSuccessful()){

                                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                DatabaseReference user = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("Admin");

                                user.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        check = snapshot.getValue().toString();
                                        Log.e("check" , check);

                                        c = "Admin";
                                        Log.e("c" , c);
                                        if(check.equals(c) ){
                                            Intent intent = new Intent(getApplicationContext(), AddProducts.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(AdminLogin.this, "Access Denied", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                            }else{

                                Toast.makeText(AdminLogin.this, "Login error!!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });

    }
}