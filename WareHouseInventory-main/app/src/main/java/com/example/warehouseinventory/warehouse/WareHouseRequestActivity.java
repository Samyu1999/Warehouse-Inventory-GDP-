package com.example.warehouseinventory.warehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehouseinventory.R;
import com.example.warehouseinventory.model.RequestAdapter;
import com.example.warehouseinventory.model.RequestModel;
import com.example.warehouseinventory.model.WarehouseRequestAdapter;
import com.example.warehouseinventory.vendor.MyRequestsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WareHouseRequestActivity extends AppCompatActivity {
    public static ArrayList<RequestModel> exampleListFull;
    GridView simpleGrid;
    ArrayList<RequestModel> names;
    FirebaseFirestore fs;
    WarehouseRequestAdapter requestAdapter;
    Button allb, shippedb, deliveredb, pendingb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_house_request);

        fs = FirebaseFirestore.getInstance();

        simpleGrid = findViewById(R.id.simpleGridView);
        allb = findViewById(R.id.all);
        shippedb = findViewById(R.id.shipped);
        deliveredb = findViewById(R.id.delivered);
        pendingb = findViewById(R.id.pending);
        names = new ArrayList<RequestModel>();
        exampleListFull = new ArrayList<RequestModel>();
        requestAdapter = new WarehouseRequestAdapter(WareHouseRequestActivity.this, names);
        fs.collection("Request").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if (!q.isEmpty()) {
                    for (DocumentSnapshot d : q) {
                        fs.collection("Products").document(d.getString("Product ID")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot docs) {
                                fs.collection("Vendors").document(d.getString("User Id")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        names.add(new RequestModel(docs.getString("Product Name"), d.getString("Status"), d.getString("Ship Address"), d.getDate("Date"),documentSnapshot.getString("username"),d.getString("Product ID"),d.getString("User Id"), d.getId()));
                                        exampleListFull.add(new RequestModel(docs.getString("Product Name"), d.getString("Status"), d.getString("Ship Address"), d.getDate("Date"),documentSnapshot.getString("username"),d.getString("Product ID"),d.getString("User Id"), d.getId()));
                                        requestAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        });
                    }
                    requestAdapter.notifyDataSetChanged();
                }
            }
        });
        simpleGrid.setAdapter(requestAdapter);

        allb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestAdapter.getFilter().filter("All");
            }
        });

        pendingb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestAdapter.getFilter().filter("Pending");
            }
        });
        shippedb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestAdapter.getFilter().filter("Shipped");
            }
        });
        deliveredb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestAdapter.getFilter().filter("Delivered");
            }
        });
        simpleGrid.setOnItemClickListener((parent, view, position, id) -> {

            if (names.get(position).getStatus().equals("Pending")) {

                final Dialog dialog = new Dialog(WareHouseRequestActivity.this);
                dialog.setContentView(R.layout.custom_dialog2);
                TextView tit = dialog.findViewById(R.id.title);
                TextView t = dialog.findViewById(R.id.tit);
                tit.setText(names.get(position).getName());
                t.setText("Confirm Shipping of ");
                Button dialogButton = dialog.findViewById(R.id.remove);
                dialogButton.setText("Confirm");
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> user = new HashMap<>();
                        user.put("User Id",names.get(position).getUid());
                        user.put("Product ID", names.get(position).getPid());
                        user.put("Ship Address", names.get(position).getAddress());
                        user.put("Date",names.get(position).getDate());
                        user.put("Status", "Shipped");
                        fs.collection("Request").document(names.get(position).getRid()).set(user);
                        Toast.makeText(getApplicationContext(),"Item Shipping Confirmed " + names.get(position).getName() + "\nTo User "+ names.get(position).getUsername() ,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());
                    }
                });
                Button dialogButto = dialog.findViewById(R.id.cancel);
                dialogButto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            } else if (names.get(position).getStatus().equals("Shipped")) {


                final Dialog dialog = new Dialog(WareHouseRequestActivity.this);
                dialog.setContentView(R.layout.custom_dialog2);
                TextView tit = dialog.findViewById(R.id.title);
                TextView t = dialog.findViewById(R.id.tit);
                tit.setText(names.get(position).getName());
                t.setText("Confirm Delivery of ");
                Button dialogButton = dialog.findViewById(R.id.remove);
                dialogButton.setText("Confirm");
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> user = new HashMap<>();
                        user.put("User Id",names.get(position).getUid());
                        user.put("Product ID", names.get(position).getPid());
                        user.put("Ship Address", names.get(position).getAddress());
                        user.put("Date",names.get(position).getDate());
                        user.put("Status", "Delivered");
                        fs.collection("Request").document(names.get(position).getRid()).set(user);
                        Toast.makeText(getApplicationContext(),"Item Shipping Confirmed " + names.get(position).getName() + "\nTo User "+ names.get(position).getUsername() ,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                        startActivity(getIntent());

                    }
                });
                Button dialogButto = dialog.findViewById(R.id.cancel);
                dialogButto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }else{
                Toast.makeText(getApplicationContext(), "Product Is Delivered", Toast.LENGTH_SHORT).show();
            }
        });


    }
}