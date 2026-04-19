package com.tracies.Adapter;

import static com.tracies.ViewAll.viewAll_select_size;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tracies.ItemView;
import com.tracies.R;
import com.tracies.SaleItemView;
import com.tracies.ShopFragment;
import com.tracies.ViewAll_ItemView;
import com.tracies.model.Model;
import com.tracies.model.ViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewAll_Adapter extends RecyclerView.Adapter<ViewAll_ViewHolder> {

    ArrayList<Model> data;
    Context context;

    public ViewAll_Adapter(ArrayList<Model> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAll_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trieces_item, parent, false);
        return new ViewAll_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAll_ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Model model = data.get(position);

        if (ShopFragment.card == 1) {
            ShopFragment.card = 0;
            holder.card_big.setVisibility(View.VISIBLE);
            holder.card_small.setVisibility(View.GONE);
            holder.b1.setText(model.getTitle());
            holder.b2.setText("$" + model.getPrice());
            Glide.with(context).load(model.getListimages().get(0)).into(holder.imageView2);
        } else {
            ShopFragment.card = 1;
            holder.card_small.setVisibility(View.VISIBLE);
            holder.card_big.setVisibility(View.GONE);
            holder.t1.setText(model.getTitle());
            holder.t2.setText("$" + model.getPrice());

            Glide.with(context).load(model.getListimages().get(0)).into(holder.imageView);

            Log.e("adap786","" + model.getListimages().get(0));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("categories")
                        .child(data.get(position).getCategoryID())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                Model catoModel = snapshot.getValue(Model.class);


                                if (catoModel.getName().equals("Sale")){

                                    Log.e("log123",""+ model.getItemID());
                                    Intent intent = new Intent(context, SaleItemView.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("detail",model);
                                    context.startActivity(intent);

                                } else {
                                    Log.e("log123",""+ model.getItemID());
                                    Intent intent = new Intent(context, ItemView.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("detail",model);
                                    context.startActivity(intent);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

            }
        });


        if (data.get(position).isFav() == true) {

            Log.e("fav007", "" + data.get(position).isFav());

            holder.favortie.setVisibility(View.GONE);
            holder.favorite_fill.setVisibility(View.VISIBLE);

            holder.fav_img.setVisibility(View.GONE);
            holder.b_fav_fill.setVisibility(View.VISIBLE);
        }else {
            holder.favorite_fill.setVisibility(View.GONE);
            holder.favortie.setVisibility(View.VISIBLE);

            holder.b_fav_fill.setVisibility(View.GONE);
            holder.fav_img.setVisibility(View.VISIBLE);
        }

        if (data.get(position).isWait() == true) {

            Log.e("fav00777887", "" + data.get(position).isWait());

            holder.b_heart.setVisibility(View.VISIBLE);
            holder.b_heart_w.setVisibility(View.GONE);

            holder.waitlist_fill.setVisibility(View.VISIBLE);
            holder.waitlist_w.setVisibility(View.GONE);
        }else{
            holder.b_heart.setVisibility(View.GONE);
            holder.b_heart_w.setVisibility(View.VISIBLE);

            holder.waitlist_fill.setVisibility(View.GONE);
            holder.waitlist_w.setVisibility(View.VISIBLE);
        }



        holder.fav_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.e("nam123", data.get(position).getCategoryID());

                holder.fav_img.setVisibility(View.GONE);
                holder.b_fav_fill.setVisibility(View.VISIBLE);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites")
                        .child(data.get(position).getItemID())
                        .setValue(data.get(position).getCategoryID());

            }



        });

        holder.b_fav_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                holder.b_fav_fill.setVisibility(View.GONE);
                holder.fav_img.setVisibility(View.VISIBLE);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites")
                        .child(data.get(position).getItemID())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            }



        });


        holder.favortie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.favortie.setVisibility(View.GONE);
                holder.favorite_fill.setVisibility(View.VISIBLE);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites")
                        .child(data.get(position).getItemID())
                        .setValue(data.get(position).getCategoryID());
            }
        });
        holder.favorite_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.favorite_fill.setVisibility(View.GONE);
                holder.favortie.setVisibility(View.VISIBLE);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites")
                        .child(data.get(position).getItemID()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            }
        });

        holder.b_heart_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.b_heart.setVisibility(View.VISIBLE);
                holder.b_heart_w.setVisibility(View.GONE);

                holder.waitlist_fill.setVisibility(View.VISIBLE);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("waitlist")
                        .child(data.get(position).getItemID())
                        .setValue(data.get(position).getCategoryID());
            }
        });
        holder.b_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.b_heart.setVisibility(View.GONE);
                holder.b_heart_w.setVisibility(View.VISIBLE);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("waitlist")
                        .child(data.get(position).getItemID()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            }
        });


        holder.waitlist_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.waitlist_fill.setVisibility(View.GONE);
                holder.waitlist_w.setVisibility(View.VISIBLE);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("waitlist")
                        .child(data.get(position).getItemID()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

            }

        });
        holder.waitlist_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.waitlist_fill.setVisibility(View.VISIBLE);
                holder.waitlist_w.setVisibility(View.GONE);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("waitlist")
                        .child(data.get(position).getItemID())
                        .setValue(data.get(position).getCategoryID());

            }

        });


        holder.b_add_To_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("categories")
                        .child(data.get(position).getCategoryID())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                Model catoModel = snapshot.getValue(Model.class);


                                if (catoModel.getName().equals("Sale")){

                                    Log.e("log123",""+ model.getItemID());
                                    Intent intent = new Intent(context, SaleItemView.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("detail",model);
                                    context.startActivity(intent);

                                } else {
                                    Log.e("log123",""+ model.getItemID());
                                    Intent intent = new Intent(context, ItemView.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("detail",model);
                                    context.startActivity(intent);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                if (data.get(position).isSelected()){

                    data.get(position).setSelected(false);
                }
                else {
                    data.get(position).setSelected(true);
                }
            }
        });
        holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.get(position).isSelected()){

                    data.get(position).setSelected(false);
                }
                else {
                    data.get(position).setSelected(true);
                }
                FirebaseDatabase.getInstance().getReference("categories")
                        .child(data.get(position).getCategoryID())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                Model catoModel = snapshot.getValue(Model.class);


                                if (catoModel.getName().equals("Sale")){

                                    Log.e("log123",""+ model.getItemID());
                                    Intent intent = new Intent(context, SaleItemView.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("detail",model);
                                    context.startActivity(intent);

                                } else {
                                    Log.e("log123",""+ model.getItemID());
                                    Intent intent = new Intent(context, ItemView.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("detail",model);
                                    context.startActivity(intent);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

            }
        });

    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }
}
