package com.example.warehouseinventory.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warehouseinventory.R;
import com.example.warehouseinventory.model.CheckinAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserInventoryActivity extends AppCompatActivity {


    GridView simpleGrid;
    ArrayList<String> names;
    ArrayList<String> brands;
    ArrayList<String> price;
    ArrayList<String> cid;
    ArrayList<String> pid;
    FirebaseFirestore fs;
    CheckinAdapter customCheckinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_inventory);


        SearchView searchView = findViewById(R.id.searchView);


        fs = FirebaseFirestore.getInstance();

        simpleGrid = findViewById(R.id.simpleGridView);
        names = new ArrayList<>();
        names = new ArrayList<>();
        brands = new ArrayList<>();
        price = new ArrayList<>();
        cid = new ArrayList<>();
        pid = new ArrayList<>();
        customCheckinAdapter =new CheckinAdapter(UserInventoryActivity.this, names, brands, price, cid);
        fs.collection("Products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if(!q.isEmpty()){
                    for(DocumentSnapshot d : q){
                        cid.add(d.getString("Check ID"));
                        names.add(d.getString("Product Name"));
                        price.add(d.getString("Price"));
                        brands.add(d.getString("Brand"));
                        pid.add(d.getId());
                    }
                    customCheckinAdapter.notifyDataSetChanged();
                }
            }
        });
        simpleGrid.setAdapter(customCheckinAdapter);


        simpleGrid.setOnItemClickListener((parent, view, position, id) -> {

            final Dialog dialog = new Dialog(UserInventoryActivity.this);dialog.setContentView(R.layout.custom_dialog1);
            TextView tit = dialog.findViewById(R.id.title);
            EditText address = dialog.findViewById(R.id.address);
            tit.setText(names.get(position));


            Map<String, Object> user = new HashMap<>();
            Button dialogButton =  dialog.findViewById(R.id.send);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(address.getText().toString().length() ==0)){
                        user.put("User Id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                        user.put("Product ID", pid.get(position));
                        user.put("Ship Address", address.getText().toString());
                        user.put("Date", new Date());
                        user.put("Status", "Pending");
                        fs.collection("Request").add(user);
                        Toast.makeText(getApplicationContext(),"Request Added for " + names.get(position) + "\nWait For WareHouse To Process!!",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else
                        Toast.makeText(getApplicationContext(),"Add the Shipping Address",Toast.LENGTH_SHORT).show();
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
        });




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customCheckinAdapter =new CheckinAdapter(UserInventoryActivity.this, names, brands, price, cid);
                simpleGrid.setAdapter(customCheckinAdapter);
                customCheckinAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}