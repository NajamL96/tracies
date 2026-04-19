package com.tracies.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracies.R;

public class item_showViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    ImageView nextImageView;
    ImageView previousImageView;
    TextView imageCount;
    public item_showViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image_show);
        nextImageView = (ImageView) itemView.findViewById(R.id.next);
        previousImageView = (ImageView) itemView.findViewById(R.id.previous);
        imageCount = (TextView) itemView.findViewById(R.id.imageCount);


    }
}
