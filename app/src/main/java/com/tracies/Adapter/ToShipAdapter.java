package com.tracies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracies.OrderItems;
import com.tracies.R;
import com.tracies.model.myordermodel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ToShipAdapter extends RecyclerView.Adapter<ToShipViewHolder> {

    ArrayList<myordermodel> myorder_data;
    Context context;

    public ToShipAdapter(ArrayList<myordermodel> myorder_data, Context context) {
        this.myorder_data = myorder_data;
        this.context = context;
    }

    @NonNull
    @Override
    public ToShipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.myorderitems,parent,false);
        return new ToShipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToShipViewHolder holder, int position) {
        myordermodel myordermodel = myorder_data.get(position);



        Log.e("askdjajk",""+ myordermodel.getStatus());

        long time = Long.parseLong(myordermodel.getTimeStamp());
        String mDate = new SimpleDateFormat("dd MMM,yyyy").format(new Date(time));
        holder.order_Date.setText("" + mDate);

        holder.t1.setText(myordermodel.getStatus());
        holder.t2.setText("$" + myordermodel.getTotalPrice());
        if (myordermodel.getAddress() != null) {
            holder.t3.setText(myordermodel.getAddress());
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OrderItems.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("orderDetail", myordermodel);
                context.startActivity(intent);

            }
        });
    }





    @Override
    public int getItemCount() {
        return myorder_data.size();
    }
}

class ToShipViewHolder extends RecyclerView.ViewHolder {

    TextView t1,t2,t3,order_Date;
    public ToShipViewHolder(@NonNull View itemView) {
        super(itemView);
        t1=(TextView) itemView.findViewById(R.id.order_Status);
        t2=(TextView) itemView.findViewById(R.id.totalPrice);
        t3=(TextView) itemView.findViewById(R.id.Address);
        order_Date= (TextView) itemView.findViewById(R.id.order_Date);
    }
}