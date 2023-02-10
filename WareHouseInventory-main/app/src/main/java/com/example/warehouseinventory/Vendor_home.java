package com.example.warehouseinventory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Vendor_home extends AppCompatActivity {

    Button profile,SignOutBtn,makeRequest,myrequests,check_In_Product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_home);

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(v -> {
            Intent k = new Intent(Vendor_home.this, VendorProfile.class);
            startActivity(k);
        });
        check_In_Product = findViewById(R.id.checkIn_Product);
        check_In_Product.setOnClickListener(v -> {
            Intent k = new Intent(Vendor_home.this, MainActivity.class);
            startActivity(k);
        });


        SignOutBtn=findViewById(R.id.signOutBtn);
        SignOutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        makeRequest = findViewById(R.id.mk_req);
        makeRequest.setOnClickListener(view -> {
            Intent k = new Intent(Vendor_home.this, HomeActivity.class);
            startActivity(k);
        });
        myrequests = (Button)findViewById(R.id.myreq);
        myrequests.setOnClickListener(v -> {
            Intent k = new Intent(Vendor_home.this, myRequests_Vendor.class);
            startActivity(k);
        });
    }
}