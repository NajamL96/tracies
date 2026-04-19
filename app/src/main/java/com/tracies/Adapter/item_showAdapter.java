package com.tracies.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tracies.R;

import java.util.ArrayList;

public class item_showAdapter extends RecyclerView.Adapter<item_showViewHolder> {

    ArrayList<String> data;
    Context context;

    public item_showAdapter(ArrayList<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public item_showViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.item_show,parent,false);
        return new item_showViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull item_showViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(position == 0)
            holder.nextImageView.setVisibility(View.GONE);
        else
            holder.nextImageView.setVisibility(View.VISIBLE);

        Log.e("positionCHeck", "Outisde: " + position + ", " + (data.size() - 1));
        if(data.size() == 1 || position == data.size() - 1)
            holder.previousImageView.setVisibility(View.GONE);
        else
            holder.previousImageView.setVisibility(View.VISIBLE);
        String item_showmodel = data.get(position);
        holder.previousImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.nextImageView.setVisibility(View.VISIBLE);
                int tempPosition = position + 1;
                if(tempPosition < data.size()) {
                    //Log.e("posicheck")
                    //favorite_itemView.rcv.smoothScrollToPosition(tempPosition);
                }
                if(tempPosition >= data.size() )
                    holder.previousImageView.setVisibility(View.GONE);
                else
                    holder.previousImageView.setVisibility(View.VISIBLE);

            }
        });

        holder.nextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("pos", String.valueOf(position));
                holder.previousImageView.setVisibility(View.VISIBLE);
                int tempPosition = position - 1 ;
                if(tempPosition >= 0) {
                    //favorite_itemView.rcv.smoothScrollToPosition(position - 1);
                    Log.e("positionCHeck", "Inside: " + tempPosition + ", " + (data.size() - 1));
                }
                if (position == 0){
                    holder.nextImageView.setVisibility(View.GONE);
                    notifyDataSetChanged();
                }

            }
        });

        Glide.with(context).load(item_showmodel).into(holder.imageView);
        holder.imageCount.setText(position +"/"+ data.size());

    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }
}
