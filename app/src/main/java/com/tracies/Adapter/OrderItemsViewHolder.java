package com.tracies.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracies.R;

public class OrderItemsViewHolder extends RecyclerView.ViewHolder{

    ImageView shoe;
    TextView t1,t2,t3,t4;
    public OrderItemsViewHolder(@NonNull View itemView) {
        super(itemView);

        t1=(TextView) itemView.findViewById(R.id.order_title);
        t2=(TextView) itemView.findViewById(R.id.cart_price);
        shoe =(ImageView) itemView.findViewById(R.id.shoe);
//        t3=(TextView) itemView.findViewById(R.id.Address);
//        t4=(TextView) itemView.findViewById(R.id.totalPrice);
    }
}
