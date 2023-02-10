package com.example.warehouseinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText ProductName, Brand, price;
    Button Registerbtn;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        ProductName = findViewById(R.id.firstName);
        Brand = findViewById(R.id.lastName);
        price = findViewById(R.id.age);
        Registerbtn = findViewById(R.id.btnRegister);



        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pname = ProductName.getText().toString();
                String pbrand = Brand.getText().toString();
                String pprice = price.getText().toString();
                Map<String,Object> product = new HashMap<>();
                product.put("Product",pname);
                product.put("Brand",pbrand);
                product.put("Price",pprice);


                db.collection("product")
                        .add(product)


                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MainActivity.this,"Added Successful",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {

                                Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();


                            }
                        });

            }
        });

    }
}