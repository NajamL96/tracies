package com.tracies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.util.Log;

import com.tracies.Adapter.OrderItemsAdapter;
import com.tracies.model.myordermodel;

public class OrderItems extends AppCompatActivity {


    public static RecyclerView orderItemsRecyclerview;
    OrderItemsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);

        orderItemsRecyclerview =(RecyclerView)findViewById(R.id.orderItemsRecycler);

        //mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("orders").child(categoriesId).child("items");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        orderItemsRecyclerview.setLayoutManager(layoutManager);
        orderItemsRecyclerview.getAdapter();

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(orderItemsRecyclerview);

        myordermodel orderModel = (myordermodel) getIntent().getSerializableExtra("orderDetail");


        Log.e("ModelCheck",""+ orderModel.getMdata().get(0).getImage());

        adapter = new OrderItemsAdapter(orderModel.getMdata(),getApplicationContext());
        orderItemsRecyclerview.setAdapter(adapter);


    }


}