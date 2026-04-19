package com.tracies.Adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tracies.R;
import com.tracies.favorite_itemView;
import com.tracies.model.favoritelistModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class favoriteAdapter extends RecyclerView.Adapter<favoriteViewHolder>{

    ArrayList<favoritelistModel> favorite_data;
    Context context;
    public static favoritelistModel FavListStatic;

    public favoriteAdapter(ArrayList<favoritelistModel> favorite_data, Context context) {

        this.favorite_data = favorite_data;
        this.context = context;
    }

    @NonNull
    @Override
    public favoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.favorite_item,parent,false);
        return new favoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull favoriteViewHolder holder, @SuppressLint("RecyclerView") int position) {

        favoritelistModel  favoritelistmodel = favorite_data.get(position);

        Log.e("1298",favorite_data.toString());

        holder.t1.setText(favoritelistmodel.getTitle());
        holder.t2.setText("$"+ favoritelistmodel.getPrice());
        Glide.with(context).load(favoritelistmodel.getListimages().get(0)).into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("log123",""+ favoritelistmodel.getItemID());
                Intent intent = new Intent(context, favorite_itemView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("detailfavorite", (Serializable) favoritelistmodel);

                context.startActivity(intent);
            }
        });


        if (favorite_data.get(position).isFav() == true) {

            Log.e("fav007789", "" + favorite_data.get(position));



            holder.fav_icon_unsel.setVisibility(View.GONE);
            holder.fav_icon.setVisibility(View.VISIBLE);

        }else{


            holder.fav_icon.setVisibility(View.GONE);
            holder.fav_icon_unsel.setVisibility(View.VISIBLE);

        }

        if (favorite_data.get(position).isWait() == true){


            holder.waitlist.setVisibility(View.VISIBLE);
            holder.f_heart_un.setVisibility(View.GONE);


        }else{
            holder.waitlist.setVisibility(View.GONE);
            holder.f_heart_un.setVisibility(View.VISIBLE);


        }


            holder.fav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.fav_icon.setVisibility(View.GONE);
                holder.fav_icon_unsel.setVisibility(View.VISIBLE);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites")
                        .child(favorite_data.get(position).getItemID()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                notifyDataSetChanged();

                            }
                        });

            }
        });
        holder.fav_icon_unsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.fav_icon_unsel.setVisibility(View.GONE);
                holder.fav_icon.setVisibility(View.VISIBLE);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites")
                        .child(favorite_data.get(position).getItemID())
                        .setValue(favorite_data.get(position).getCategoryID());

            }
        });
        holder.f_heart_un.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.waitlist.setVisibility(View.VISIBLE);
                holder.f_heart_un.setVisibility(View.GONE);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("waitlist")
                        .child(favorite_data.get(position).getItemID())
                        .setValue(favorite_data.get(position).getCategoryID());

            }
        });
        holder.waitlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.waitlist.setVisibility(View.GONE);
                holder.f_heart_un.setVisibility(View.VISIBLE);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("waitlist")
                        .child(favorite_data.get(position).getItemID()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

            }
        });

        holder.add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favorite_data.get(position).isSelected()){

                    favorite_data.get(position).setSelected(false);
                }
                else {
                    favorite_data.get(position).setSelected(true);
                }
                Log.e("log123",""+ favoritelistmodel.getItemID());
                Intent intent = new Intent(context, favorite_itemView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("detailfavorite", (Serializable) favoritelistmodel);

                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return favorite_data.size();
    }
}
