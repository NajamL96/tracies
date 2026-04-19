package com.tracies.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracies.R;

public class waitlistViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView,fav_fil,fav_w,waitlist_img,b_heart_wait_unsel,add_to_cart;
    TextView t1,t2,t3;
    public waitlistViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=(ImageView) itemView.findViewById(R.id.waitlist_imagePost);
        t1=(TextView) itemView.findViewById(R.id.waitlist_title);
        t2=(TextView) itemView.findViewById(R.id.waitlist_disc);
        fav_w = (ImageView) itemView.findViewById(R.id.fav_w);
        waitlist_img = (ImageView) itemView.findViewById(R.id.b_heart_wait);
        fav_fil = (ImageView) itemView.findViewById(R.id.fav_fil);
        b_heart_wait_unsel = (ImageView) itemView.findViewById(R.id.b_heart_wait_unsel);
        add_to_cart = (ImageView) itemView.findViewById(R.id.wait_add_to_cart);

    }
}
