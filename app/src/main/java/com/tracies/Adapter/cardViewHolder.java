package com.tracies.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracies.R;

public class cardViewHolder extends RecyclerView.ViewHolder{

    ImageView greenTick,greyTick,cardimg,imageView;
    TextView card_holder,card_number,month,year,ccv;

    public cardViewHolder(@NonNull View itemView) {
        super(itemView);
        card_holder=(TextView) itemView.findViewById(R.id.card_holder);
        cardimg = (ImageView) itemView.findViewById(R.id.cardimg);
        month=(TextView) itemView.findViewById(R.id.MM);
        year = (TextView) itemView.findViewById(R.id.YY);
        ccv=(TextView) itemView.findViewById(R.id.ccv);
        greenTick = (ImageView) itemView.findViewById(R.id.cardGtick);
        greyTick =  (ImageView) itemView.findViewById(R.id.greyTick);
        card_number = (TextView) itemView.findViewById(R.id.card_number);
        imageView = (ImageView) itemView.findViewById(R.id.shoe);
    }
}
