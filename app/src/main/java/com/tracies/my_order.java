package com.tracies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tracies.Adapter.myorderAdapter;
import com.tracies.model.Cart_Model;
import com.tracies.model.myordermodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class my_order extends AppCompatActivity {

    RecyclerView rcv_my_order;
    myorderAdapter order_adapter;
    ArrayList<users> address_data =new ArrayList<>();
    ArrayList<myordermodel> myorder_data;
    DatabaseReference mDatabase,vDatabase;
    myordermodel order_model;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);


        rcv_my_order=(RecyclerView)findViewById(R.id.rcv_my_order);
        rcv_my_order.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("orders")
                .child(FirebaseAuth.getInstance().getUid());
        vDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getUid());

        myorder_data = new ArrayList<myordermodel>();
        order_adapter = new myorderAdapter(myorder_data,getApplicationContext());
        rcv_my_order.setAdapter(order_adapter);

        getUsersAddress();
        getData();

    }

    private void getData() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myorder_data.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        Log.e("name1khn", "" + snapshot1);
                        order_model = snapshot1.getValue(myordermodel.class);
                        ArrayList<Cart_Model> M_data =new ArrayList<>();
                        order_model.setTimeStamp(snapshot1.getKey());

                        Log.e("56612",""+order_model.getTimeStamp());

                        //order_model.setAddress(snapshot1.);
                    Log.e("khan","" + order_model.getAddress());
                    for (DataSnapshot snapshot2 : snapshot1.child("items").getChildren()) {
                        Cart_Model cart_model = snapshot2.getValue(Cart_Model.class);
                        M_data.add(cart_model);
                        Log.e("snap233345",""+ M_data);
                    }
                    order_model.setAddress(address);
                    order_model.setMdata(M_data);
                    if (order_model.getStatus().equals("completed")) {
                        myorder_data.add(0, order_model);
                    }
                    order_adapter.notifyItemInserted(0);

                }
                order_adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(my_order.this,Bottom_nav.class);
        startActivity(intent);
        super.onBackPressed();
    }

    private void getUsersAddress() {
        vDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    users user = snapshot.getValue(users.class);
                    user.setAddress(user.getAddress());
                    Log.e("user", "" + user.getAddress());
                    address = user.getAddress();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}