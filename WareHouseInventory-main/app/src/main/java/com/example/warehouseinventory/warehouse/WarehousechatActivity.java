package com.example.warehouseinventory.warehouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.warehouseinventory.R;
import com.example.warehouseinventory.model.MessageAdapter;
import com.example.warehouseinventory.model.MessageModel;
import com.example.warehouseinventory.vendor.ChatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WarehousechatActivity extends AppCompatActivity {

    FirebaseFirestore fs;
    EditText chat;
    ImageButton send;
    String uname, uid;
    ArrayList<MessageModel> chaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousechat);
        fs = FirebaseFirestore.getInstance();
        chat = findViewById(R.id.edit_gchat_message);
        send = findViewById(R.id.button_gchat_send);

        Intent i = getIntent();
        uname = i.getStringExtra("Username");
        uid = i.getStringExtra("Uid");

        RecyclerView recyclerView = findViewById(R.id.recycler_gchat);
        chaa = new ArrayList<>();
        MessageAdapter adapter = new MessageAdapter(chaa);
        fs.collection("Message").orderBy("Created At", Query.Direction.ASCENDING).whereEqualTo("User", uid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    for(DocumentSnapshot d: queryDocumentSnapshots){
                        chaa.add(new MessageModel(d.getString("Message"),d.getString("username"), !d.getBoolean("Sender"), d.getDate("Created At")));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = chat.getText().toString();
                if(txt.length()==0){
                    Toast.makeText(WarehousechatActivity.this, "You Need To Type Something to Send", Toast.LENGTH_SHORT).show();
                }else{
                    Map<String, Object> user = new HashMap<>();
                    user.put("Message", txt);
                    user.put("username", uname);
                    user.put("User",  uid);
                    user.put("Sender", false);
                    user.put("Created At", new Date());
                    fs.collection("Message").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            finish();
                            Intent i = new Intent(getIntent());
                            i.putExtra("username", uname);
                            startActivity(i);
                        }
                    });
                }
            }
        });
    }
}