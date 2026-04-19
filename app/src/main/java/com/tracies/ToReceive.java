package com.tracies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.tracies.Adapter.ToPayAdapter;
import com.tracies.model.Cart_Model;
import com.tracies.model.myordermodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ToReceive extends AppCompatActivity {

    RecyclerView rcv_to_pay;
    ToPayAdapter toPayAdapter;

    ArrayList<myordermodel> myorder_data;
    DatabaseReference mDatabase,vDatabase;
    myordermodel order_model;
    String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_pay_activity);


        rcv_to_pay=(RecyclerView)findViewById(R.id.rcv_to_pay);
        rcv_to_pay.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("orders")
                .child(FirebaseAuth.getInstance().getUid());
        vDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getUid());

        myorder_data = new ArrayList<>();
        toPayAdapter = new ToPayAdapter(myorder_data,getApplicationContext());
        rcv_to_pay.setAdapter(toPayAdapter);
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

                    for (DataSnapshot snapshot2 : snapshot1.child("items").getChildren()) {
                        Cart_Model cart_model = snapshot2.getValue(Cart_Model.class);
                        M_data.add(cart_model);

                        Log.e("snap233345",""+ M_data);
                    }
                    order_model.setAddress(address);
                    order_model.setMdata(M_data);
                    if (order_model.getStatus().equals("shipped")){
                        myorder_data.add(0,order_model);
                    }
                }
                toPayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void getUsersAddress() {
        vDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users user = snapshot.getValue(users.class);
                Log.e("userTopay", "" + user.getAddress());
                address = user.getAddress();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}