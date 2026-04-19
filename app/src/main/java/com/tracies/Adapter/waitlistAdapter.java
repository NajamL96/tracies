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
import com.tracies.model.waitlistModel;
import com.tracies.waitlist_itemView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class waitlistAdapter extends RecyclerView.Adapter<waitlistViewHolder>{

    ArrayList<waitlistModel> waitlist_data;
    Context context;
    int fav = 1;
    public static waitlistModel waitListStatic;

    public waitlistAdapter(ArrayList<waitlistModel> waitlist_data, Context context) {
        this.waitlist_data = waitlist_data;
        this.context = context;
    }

    @NonNull
    @Override
    public waitlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.waitlist_item,parent,false);
        return new waitlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull waitlistViewHolder holder, @SuppressLint("RecyclerView") int position) {

        waitlistModel  waitlistModel = waitlist_data.get(position);

        holder.t1.setText(waitlistModel.getTitle());
        holder.t2.setText("$"+ waitlistModel.getPrice());
        Glide.with(context).load(waitlistModel.getListimages().get(0)).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("log123",""+ waitlistModel.getItemID());
                Intent intent = new Intent(context, waitlist_itemView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("detailwaitlist", (Serializable) waitlistModel);
                context.startActivity(intent);
            }
        });


        if (waitlist_data.get(position).isFav() == true) {

            Log.e("fav00778956", "" + waitlist_data.get(position).isFav());

            holder.fav_w.setVisibility(View.GONE);
            holder.fav_fil.setVisibility(View.VISIBLE);

        }else{

            holder.fav_w.setVisibility(View.VISIBLE);
            holder.fav_fil.setVisibility(View.GONE);

        }

        if (waitlist_data.get(position).isWait() == true){


            holder.waitlist_img.setVisibility(View.VISIBLE);
            holder.b_heart_wait_unsel.setVisibility(View.GONE);


        }else{
            holder.waitlist_img.setVisibility(View.GONE);
            holder.b_heart_wait_unsel.setVisibility(View.VISIBLE);


        }



        holder.waitlist_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.waitlist_img.setVisibility(View.GONE);
                holder.b_heart_wait_unsel.setVisibility(View.VISIBLE);



                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("waitlist")
                            .child(waitlist_data.get(position).getItemID()).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    notifyDataSetChanged();
                                }
                            });

                    Log.e("check123", waitlist_data.get(position).getItemID());
                }


        });

        holder.b_heart_wait_unsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.waitlist_img.setVisibility(View.VISIBLE);
                holder.b_heart_wait_unsel.setVisibility(View.GONE);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("waitlist")
                        .child(waitlist_data.get(position).getItemID())
                        .setValue(waitlist_data.get(position).getCategoryID());
            }
        });
        holder.fav_fil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (fav == 0) {
//                    fav = 1;

                    holder.fav_w.setVisibility(View.VISIBLE);
                    holder.fav_fil.setVisibility(View.GONE);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites")
                            .child(waitlist_data.get(position).getItemID()).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    notifyDataSetChanged();

                                }
                            });
               // }
            }
        });

        holder.fav_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.fav_w.setVisibility(View.GONE);
                holder.fav_fil.setVisibility(View.VISIBLE);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites")
                        .child(waitlist_data.get(position).getItemID())
                        .setValue(waitlist_data.get(position).getCategoryID());

            }
        });

        holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (waitlist_data.get(position).isSelected()){

                    waitlist_data.get(position).setSelected(false);
                }
                else {
                    waitlist_data.get(position).setSelected(true);
                }
                Log.e("log123",""+ waitlistModel.getCategoryID());
                Intent intent = new Intent(context, waitlist_itemView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("detailwaitlist", (Serializable) waitlistModel);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return waitlist_data.size();
    }
}
