package com.tracies.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracies.R;

public class favoriteViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView, fav_icon, waitlist, add_cart,f_heart_un,fav_icon_unsel;
    TextView t1,t2,t3;

    public favoriteViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=(ImageView) itemView.findViewById(R.id.favorite_imagePost);
        t1=(TextView) itemView.findViewById(R.id.favorite_title);
        t2=(TextView) itemView.findViewById(R.id.favorite_disc);
        fav_icon = (ImageView) itemView.findViewById(R.id.fav_icon);
        waitlist=(ImageView) itemView.findViewById(R.id.f_heart);
        add_cart = (ImageView) itemView.findViewById(R.id.favorite_add_to_cart);
        f_heart_un = (ImageView) itemView.findViewById(R.id.f_heart_un);
        fav_icon_unsel = (ImageView) itemView.findViewById(R.id.fav_icon_unsel);




    }
}
