package com.example.warehouseinventory.warehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.SearchView;

import com.example.warehouseinventory.R;
import com.example.warehouseinventory.model.CheckinAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity {


    GridView simpleGrid;
    ArrayList<String> names;
    ArrayList<String> brands;
    ArrayList<String> price;
    ArrayList<String> cid;
    FirebaseFirestore fs;
    CheckinAdapter customCheckinAdapter;
    String sss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        SearchView searchView = findViewById(R.id.searchView);


        fs = FirebaseFirestore.getInstance();

        simpleGrid = findViewById(R.id.simpleGridView);
        names = new ArrayList<>();
        names = new ArrayList<>();
        brands = new ArrayList<>();
        price = new ArrayList<>();
        cid = new ArrayList<>();
        customCheckinAdapter =new CheckinAdapter(InventoryActivity.this, names, brands, price, cid);
        fs.collection("Products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                    for(DocumentSnapshot d : q){
                        names.add(d.getString("Product Name"));
                        cid.add(d.getString("Check ID"));
                        price.add(d.getString("Price"));
                        brands.add(d.getString("Brand"));
                    }
                    customCheckinAdapter.notifyDataSetChanged();
            }
        });
        simpleGrid.setAdapter(customCheckinAdapter);




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customCheckinAdapter =new CheckinAdapter(InventoryActivity.this, names, brands, price, cid);
                simpleGrid.setAdapter(customCheckinAdapter);
                customCheckinAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}