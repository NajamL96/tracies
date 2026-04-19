package com.tracies.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracies.R;

public class myorderViewHolder extends RecyclerView.ViewHolder {

    TextView t1,t2,t3,order_Date;
    public myorderViewHolder(@NonNull View itemView) {
        super(itemView);
        t1=(TextView) itemView.findViewById(R.id.order_Status);
        t2=(TextView) itemView.findViewById(R.id.totalPrice);
        t3=(TextView) itemView.findViewById(R.id.Address);
        order_Date= (TextView) itemView.findViewById(R.id.order_Date);
    }
}
