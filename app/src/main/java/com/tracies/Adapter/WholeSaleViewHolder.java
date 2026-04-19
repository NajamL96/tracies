package com.tracies.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracies.R;

public class WholeSaleViewHolder extends RecyclerView.ViewHolder {
    ImageView WimageView,Wgreentick, Wgreytick;
    TextView b1,b2;
    LinearLayout Wcart_item;
    public WholeSaleViewHolder(@NonNull View itemView) {
        super(itemView);

        WimageView = (ImageView) itemView.findViewById(R.id.Wshoe);
        Wcart_item = (LinearLayout) itemView.findViewById(R.id.Wcart_item);
        Wgreentick = (ImageView) itemView.findViewById(R.id.WgreenTick);
        Wgreytick = (ImageView) itemView.findViewById(R.id.WgreyTick);
        b1 = (TextView) itemView.findViewById(R.id.Wcart_title);
        b2 = (TextView) itemView.findViewById(R.id.Wcart_price);
    }
}
