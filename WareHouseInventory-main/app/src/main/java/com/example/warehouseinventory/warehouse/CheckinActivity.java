package com.example.warehouseinventory.warehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.warehouseinventory.LoginActivity;
import com.example.warehouseinventory.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CheckinActivity extends AppCompatActivity {


    FirebaseFirestore fstore;
    EditText checkid, pname, price, brand;
    Button checkin, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        fstore = FirebaseFirestore.getInstance();
        checkid = findViewById(R.id.checkid);
        pname = findViewById(R.id.name);
        price = findViewById(R.id.price);
        brand = findViewById(R.id.brand);


        checkin = findViewById(R.id.checkin);
        cancel = findViewById(R.id.cancel);

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkid.getText().length()==0 || brand.getText().length()==0 || pname.getText().length()==0 || price.getText().length()==0){
                    Toast.makeText(CheckinActivity.this, "All Fields Are Required!!", Toast.LENGTH_SHORT).show();
                }else if(checkid.length()<6){
                    Toast.makeText(CheckinActivity.this, "Check Id Has to be 6 Character Long!!", Toast.LENGTH_SHORT).show();
                } else{
                    Map<String, Object> user = new HashMap<>();
                    user.put("Check ID", checkid.getText().toString());
                    user.put("Product Name", pname.getText().toString());
                    user.put("Price", price.getText().toString());
                    user.put("Brand", brand.getText().toString());
                    fstore.collection("Products").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            finish();
                            Toast.makeText(getApplicationContext(),"Checked is Successfully",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}