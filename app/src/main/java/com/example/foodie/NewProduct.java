package com.example.foodie;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.SimpleFormatter;

public class NewProduct extends AppCompatActivity {

    EditText Foodname,Discription,Price;
    Button Btn1 , button7;

    ImageView imageView;
    String Category,key,Ctime,Cdate,ImageUrl,Test ;
    int Pick =1;
    Uri Image;
    StorageReference Product;
    DatabaseReference database;

    //Test
    Bitmap bmp;
    ByteArrayOutputStream baos;
    //Test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        Foodname = findViewById(R.id.Foodname);
        Discription = findViewById(R.id.Discription);
        Price = findViewById(R.id.Price);
        Btn1 = findViewById(R.id.Btn1);
        button7 = findViewById(R.id.button7);
        imageView = findViewById(R.id.imageView);
        Product = FirebaseStorage.getInstance().getReference().child("Product Images");
        database = FirebaseDatabase.getInstance().getReference().child("Foods");

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProducts.class);
                startActivity(intent);
            }
        });
        Category = getIntent().getExtras().get("Category").toString();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Gallery = new Intent();
                Gallery.setAction(Intent.ACTION_GET_CONTENT);
                Gallery.setType("image/*");
                startActivityForResult(Gallery, Pick);
            }

        });

        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //convert to string
                String foodname = Foodname.getText().toString();
                String discription = Discription.getText().toString();
                String price = Price.getText().toString();

                if (Image == null){
                    Toast.makeText(NewProduct.this, "Select a image", Toast.LENGTH_SHORT).show();
                } else if (foodname.isEmpty() || discription.isEmpty() || price.isEmpty()) {
                    Toast.makeText(NewProduct.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }else{

                    //get date and time to create unic id for image

                    Calendar calendar = Calendar.getInstance();

                    SimpleDateFormat date = new SimpleDateFormat("MMM dd, yyy");
                    Cdate = date.format(calendar.getTime());
                    SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss a");
                    Ctime = time.format(calendar.getTime());

                    key = Cdate + Ctime;

                    //upload image to storage firebase

                    //StorageReference filePath = Product.child(Image.getLastPathSegment()+key+".jpg");
                    //UploadTask upload = filePath.putFile(Image);


                    //Test
                    // images are stored with timestamp as their name
                    String timestamp = "" + System.currentTimeMillis();

                    bmp = null;
                    try {
                        bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), Image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    baos = new ByteArrayOutputStream();

                    // here we can choose quality factor
                    // in third parameter(ex. here it is 25)
                    bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);

                    byte[] fileInBytes = baos.toByteArray();
                    StorageReference filePath = Product.child(key+".jpg");
                    UploadTask upload = filePath.putBytes(fileInBytes);
                    //Test


                    upload.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewProduct.this, "Image upload fail!!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(NewProduct.this, "Successfully upload", Toast.LENGTH_SHORT).show();

                            Task<Uri> urlTask = upload.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                                    if (!task.isSuccessful()){
                                        throw task.getException();
                                    }


                                    //Image url store in here - ImageUrl
                                    ImageUrl = filePath.getDownloadUrl().toString();
                                    return filePath.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(NewProduct.this, "Successfully Upload to database", Toast.LENGTH_SHORT).show();

                                        ImageUrl = task.getResult().toString();

                                        //save data to database
                                        HashMap<String,Object> productMap = new HashMap<>();
                                        productMap.put("Pid",key);
                                        productMap.put("Foodname",foodname);
                                        productMap.put("Discription",discription);
                                        productMap.put("Price",price);
                                        productMap.put("Image",ImageUrl);
                                        productMap.put("Category",Category);


                                        database.child(key).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(NewProduct.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), AddProducts.class);
                                                    startActivity(intent);
                                                }else{
                                                    Toast.makeText(NewProduct.this, "Upload error", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }
                                }
                            });
                        }
                    });

                }


            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Pick && resultCode==RESULT_OK && data!=null){
            Image = data.getData();
            imageView.setImageURI(Image);
        }
    }
}