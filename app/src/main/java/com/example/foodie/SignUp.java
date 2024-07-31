package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    EditText Name,P_number,E_mail,Password,Address;
    Button button1,button2;

    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        Name = findViewById(R.id.Name);
        P_number = findViewById(R.id.P_number);
        E_mail = findViewById(R.id.E_mail);
        Password = findViewById(R.id.Password);
        Address = findViewById(R.id.Address);


        auth=FirebaseAuth.getInstance();
        //database=FirebaseDatabase.getInstance();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String N_ame = Name.getText().toString();
                String Pnumber = P_number.getText().toString();
                String Email = E_mail.getText().toString();
                String P_assword = Password.getText().toString();
                String A_ddress = Address.getText().toString();


                if(TextUtils.isEmpty(N_ame) || TextUtils.isEmpty(Pnumber) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(P_assword) || TextUtils.isEmpty(A_ddress) ){

                    Toast.makeText(getApplicationContext(), "Fields are empty!!!", Toast.LENGTH_SHORT).show();

                }
                else{
                    auth.createUserWithEmailAndPassword(Email,P_assword)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){


                                        try {
                                            final DatabaseReference RootRef;
                                            RootRef = FirebaseDatabase.getInstance().getReference();

                                            HashMap<String, Object> userdataMap = new HashMap<>();
                                            userdataMap.put("Name",N_ame);
                                            userdataMap.put("PhoneNumber",Pnumber);
                                            userdataMap.put("Email",Email);
                                            userdataMap.put("Address",A_ddress);
                                            userdataMap.put("Password",P_assword);
                                            userdataMap.put("Admin","");

                                            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            //RootRef.child("Users").child(userID).updateChildren(userdataMap)

                                            RootRef.child("Users").child(userID).updateChildren(userdataMap)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                Toast.makeText(SignUp.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                startActivity(intent);

                                                            }else{
                                                                Toast.makeText(SignUp.this, "Database failled!!!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(SignUp.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }



                                        //String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        //RootRef.child("Users").child(userID).updateChildren(userdataMap)


                                    }else{
                                        Toast.makeText(SignUp.this, "Error!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

    }
}