package com.tracies.Adapter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracies.R;

public class cartViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView,greentick, greytick,del,decrease_btn,increase_btn,empty_image;
    TextView t1,t2,t3,size;
    LinearLayout cart_item;

    EditText count_quant;
    public cartViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=(ImageView) itemView.findViewById(R.id.shoe);
        t1=(TextView) itemView.findViewById(R.id.cart_title);
        t2=(TextView) itemView.findViewById(R.id.cart_price);
        t3=(TextView) itemView.findViewById(R.id.cart_quantity);
        decrease_btn =(ImageView) itemView.findViewById(R.id.decrease_btn);
        increase_btn = (ImageView) itemView.findViewById(R.id.increase_btn);
        count_quant = (EditText) itemView.findViewById(R.id.edit_quantity);
        cart_item =(LinearLayout) itemView.findViewById(R.id.cart_item);
        greentick = (ImageView) itemView.findViewById(R.id.greenTick);
        greytick = (ImageView) itemView.findViewById(R.id.greyTick);
        del = (ImageView) itemView.findViewById(R.id.del);
        size = (TextView) itemView.findViewById(R.id.sizeItem);





    }
}
