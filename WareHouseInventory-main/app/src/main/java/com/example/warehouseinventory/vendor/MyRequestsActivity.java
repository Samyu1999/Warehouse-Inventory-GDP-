package com.example.warehouseinventory.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehouseinventory.R;
import com.example.warehouseinventory.model.RequestAdapter;
import com.example.warehouseinventory.model.RequestModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyRequestsActivity extends AppCompatActivity {


   public static ArrayList<RequestModel> exampleListFull;
    GridView simpleGrid;
    ArrayList<RequestModel> names;
    FirebaseFirestore fs;
    RequestAdapter requestAdapter;
    Button allb, shippedb, deliveredb, pendingb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);


        fs = FirebaseFirestore.getInstance();

        simpleGrid = findViewById(R.id.simpleGridView);
        allb = findViewById(R.id.all);
        shippedb = findViewById(R.id.shipped);
        deliveredb = findViewById(R.id.delivered);
        pendingb = findViewById(R.id.pending);
        names = new ArrayList<RequestModel>();
        exampleListFull = new ArrayList<RequestModel>();
        requestAdapter = new RequestAdapter(MyRequestsActivity.this, names);
        fs.collection("Request").whereEqualTo("User Id", FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if(!q.isEmpty()){
                    for(DocumentSnapshot d : q){
                        fs.collection("Products").document(d.getString("Product ID")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                names.add(new RequestModel(documentSnapshot.getString("Product Name"),d.getString("Status"),d.getString("Ship Address"),d.getId(),d.getDate("Date")));
                                exampleListFull.add(new RequestModel(documentSnapshot.getString("Product Name"),d.getString("Status"),d.getString("Ship Address"),d.getId(),d.getDate("Date")));
                                requestAdapter.notifyDataSetChanged();
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

            if(names.get(position).getStatus().equals("Pending")){

                final Dialog dialog = new Dialog(MyRequestsActivity.this);dialog.setContentView(R.layout.custom_dialog2);
                TextView tit = dialog.findViewById(R.id.title);
                tit.setText(names.get(position).getName());
                Button dialogButton =  dialog.findViewById(R.id.remove);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            fs.collection("Request").document(names.get(position).getRid()).delete();
                            Toast.makeText(getApplicationContext(),"Request Removed for " + names.get(position),Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            finish();
                            startActivity(getIntent());

                    }
                });
                Button dialogButto =  dialog.findViewById(R.id.cancel);
                dialogButto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }else{
                Toast.makeText(getApplicationContext(),"Product Shipped From Ware House",Toast.LENGTH_SHORT).show();
            }
        });


    }
}