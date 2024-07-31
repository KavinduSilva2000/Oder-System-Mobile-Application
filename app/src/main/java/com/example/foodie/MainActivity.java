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
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    EditText E_mail,Password;
    Button button1,button2,button3 ;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        E_mail = findViewById(R.id.E_mail);
        Password = findViewById(R.id.Password);
        auth = FirebaseAuth.getInstance();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AdminLogin.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = E_mail.getText().toString();
                String P_assword = Password.getText().toString();

                if( TextUtils.isEmpty(Email) || TextUtils.isEmpty(P_assword)  ){

                    Toast.makeText(getApplicationContext(), "Fields are empty!!!", Toast.LENGTH_SHORT).show();
                }else{

                    auth.signInWithEmailAndPassword(Email, P_assword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                Intent intent =new Intent(getApplicationContext(), Home.class );
                                startActivity(intent);

                            }else{
                                Toast.makeText(MainActivity.this, "Login error!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }



            }
        });

    }
}