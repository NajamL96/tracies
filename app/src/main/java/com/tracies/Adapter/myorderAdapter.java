package com.tracies.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracies.OrderItems;
import com.tracies.R;
import com.tracies.model.myordermodel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class myorderAdapter extends RecyclerView.Adapter<myorderViewHolder> {

    ArrayList<myordermodel> myorder_data;
    Context context;

    public myorderAdapter(ArrayList<myordermodel> myorder_data, Context context) {
        this.myorder_data = myorder_data;
        this.context = context;
    }

    @NonNull
    @Override
    public myorderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.myorderitems,parent,false);
        return new myorderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myorderViewHolder holder, @SuppressLint("RecyclerView") int position) {

        myordermodel myorder = myorder_data.get(position);
        long time=Long.parseLong(myorder.getTimeStamp());
        Log.e("khan78562",""+myorder.getTimeStamp());
        @SuppressLint("SimpleDateFormat") String mDate= new SimpleDateFormat("dd MMM,yyyy").format(new Date(time / 1000));
        holder.order_Date.setText(""+mDate );

        holder.t1.setText(myorder.getStatus());
        holder.t2.setText("$" + myorder.getTotalPrice());
        if (myorder.getAddress() != null) {
            holder.t3.setText(myorder.getAddress());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,OrderItems.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("orderDetail", myorder);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return myorder_data.size();
    }
}
