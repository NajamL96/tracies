package com.tracies.Adapter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tracies.R;

public class ViewAll_ViewHolder extends RecyclerView.ViewHolder{


    ImageView imageView, imageView2, fav_img,b_heart,b_heart_w,b_add_To_Cart,b_fav_fill,waitlist_fill,waitlist_w,favortie,add_to_cart,favorite_fill;
    TextView t1,t2,t3,b1,b2,b3;
    CardView card_small, card_big;
    EditText search;


    public ViewAll_ViewHolder(@NonNull View itemView) {
        super(itemView);


        imageView2 = (ImageView) itemView.findViewById(R.id.b_imagePost);
        imageView=(ImageView) itemView.findViewById(R.id.imagePost);
        fav_img=(ImageView) itemView.findViewById(R.id.fav_img);
        b_add_To_Cart = (ImageView) itemView.findViewById(R.id.b_add_to_cart);
        b_heart = (ImageView) itemView.findViewById(R.id.b_heart);
        b_heart_w =(ImageView) itemView.findViewById(R.id.b_heart_w);
        waitlist_fill = (ImageView) itemView.findViewById(R.id.waitlist_fill);
        waitlist_w = (ImageView) itemView.findViewById(R.id.waitlist_w);
        favortie = (ImageView) itemView.findViewById(R.id.favorite_icon);
        favorite_fill =(ImageView) itemView.findViewById(R.id.favorite_fill);
        b_fav_fill = (ImageView) itemView.findViewById(R.id.b_favorite_fill);
        add_to_cart = (ImageView) itemView.findViewById(R.id.add_to_cart);
        search = (EditText) itemView.findViewById(R.id.search);
        b1 = (TextView) itemView.findViewById(R.id.b_title);
        t1=(TextView) itemView.findViewById(R.id.title);
        b2 = (TextView) itemView.findViewById(R.id.b_disc);
        t2=(TextView) itemView.findViewById(R.id.disc);
        card_big=(CardView) itemView.findViewById(R.id.card_big);
        card_small=(CardView) itemView.findViewById(R.id.card_small);
    }
}
