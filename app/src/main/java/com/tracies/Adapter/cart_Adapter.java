package com.tracies.Adapter;

import static com.google.firebase.database.ServerValue.increment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tracies.R;
import com.tracies.model.Cart_Model;
import com.tracies.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Objects;

public class cart_Adapter extends RecyclerView.Adapter {


    ArrayList<Cart_Model>  cart_data;
    Context context;
    private int counter ;
    private int deccounter;
    String displaySize = "";
    int Quantiy;
    DatabaseReference mDatabase;
    String size;


    public cart_Adapter(ArrayList<Cart_Model> cart_data, Context context) {
        this.cart_data = cart_data;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        //Log.e("zhkAAZ",""+ cart_data.get(position).getSize());
        if (cart_data.get(position).getSize() != null){
            return 1;
        }else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 1) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.my_cartitem, parent, false);
            return new cartViewHolder(view);
        }else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.wholesale_cart, parent, false);
            return new WholeSaleViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Cart_Model model = (cart_data.get(position));

        Log.e("zheckAAZit",""+ model.getCategoryID());

        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("categories")
                .child(model.getCategoryID()).child("items").child(model.getItemID());

        counter=1;
        Log.e("zheckSAAZ",""+ cart_data.get(position).getSize() );

        if (cart_data.get(position).getSize() != null) {
           
            cartViewHolder cartViewHolder = (cart_Adapter.cartViewHolder) holder;
            cartViewHolder.t1.setText(model.getTitle());
            cartViewHolder.t2.setText("$"+model.getPrice());
            Glide.with(context).load(model.getImage()).into(cartViewHolder.imageView);

            if (model.isSelected()){
                cartViewHolder.greentick.setVisibility(View.VISIBLE);

                cartViewHolder.greytick.setVisibility(View.GONE);
            }else {
                cartViewHolder.greentick.setVisibility(View.VISIBLE);

                cartViewHolder.greentick.setVisibility(View.GONE);
            }

            cartViewHolder.count_quant.setText(""+model.getTotalitem());

            int count = model.getTotalitem();

                cartViewHolder.decrease_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.e("checkit","coming "+model.getSize());

                        if (count > 1) {
                            deccounter = 1;
                            deccounter = count - 1;
                        } else {
                            deccounter = count;
                        }

                        FirebaseDatabase.getInstance().getReference("users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("cart")
                                .child(model.getCartID()).child("totalitem").setValue(deccounter);

                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Model Shopmodel = snapshot.getValue(Model.class);

                                if (count > 1) {



                                    if (model.getSize().equals("small")) {

                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                        ref.child(model.getCategoryID()).child("items")
                                                .child(model.getItemID())
                                                .child(model.getSize())
                                                .setValue(Shopmodel.getSmall() + 1);

                                    } else if (model.getSize().equals("medium")) {

                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                        ref.child(model.getCategoryID()).child("items")
                                                .child(model.getItemID())
                                                .child(model.getSize())
                                                .setValue(Shopmodel.getMedium() + 1);

                                    } else if (model.getSize().equals("large")) {

                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                        ref.child(model.getCategoryID()).child("items")
                                                .child(model.getItemID())
                                                .child(model.getSize())
                                                .setValue(Shopmodel.getLarge() + 1);

                                    } else if (model.getSize().equals("extraLarge")) {

                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                        ref.child(model.getCategoryID()).child("items")
                                                .child(model.getItemID())
                                                .child(model.getSize())
                                                .setValue(Shopmodel.getExtraLarge() + 1);

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

            cartViewHolder.count_quant.setText(String.valueOf(model.getTotalitem()));


            Log.e("sizeCheck","" + model.getSize());
            if (model.getSize().equals("small")){
                displaySize =  "S";
                size = "small";
               // Quantiy = model.getSmall();
            }else if(model.getSize().equals("medium")){
                displaySize =  "M";
                size = "medium";
               // Quantiy = model.getMedium();
            }else if(model.getSize().equals("large")){
                displaySize =  "L";
                size = "large";
               // Quantiy = model.getLarge();
            }else if(model.getSize().equals("extraLarge")){
                displaySize =  "E";
                size = "extraLarge";
               // Quantiy = model.getExtraLarge();
            }
            FirebaseDatabase.getInstance().getReference("categories")
                    .child(model.getCategoryID()).child("items")
                    .child(model.getItemID()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Model Shopmodel = snapshot.getValue(Model.class);

                    if (model.getSize().equals("small")){

                        Quantiy = Shopmodel.getSmall();
                    }else if(model.getSize().equals("medium")){

                        Quantiy = Shopmodel.getMedium();
                    }else if(model.getSize().equals("large")){

                        Quantiy = Shopmodel.getLarge();
                    }else if(model.getSize().equals("extraLarge")){

                        Quantiy = Shopmodel.getExtraLarge();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            cartViewHolder.size.setText(displaySize);
            Log.e("789456","coming ");

            cartViewHolder.increase_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (count <= Quantiy) {
                        counter = 0;
                        counter = count + 1;
                    } else {
                        counter = count;
                    }


                    Log.e("khan0000",""+counter);
                    FirebaseDatabase.getInstance().getReference("users")
                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("cart")
                            .child(model.getCartID()).child("totalitem").setValue(counter)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.e("increase","coming 1"+model.getSize());
                            FirebaseDatabase.getInstance().getReference("categories")
                                    .child(model.getCategoryID()).child("items")
                                    .child(model.getItemID()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Model Shopmodel = snapshot.getValue(Model.class);

                                    //   if (count < Quantiy) {
                                    Log.e("increase","coming 21"+model.getSize() + Shopmodel.getMedium());
                                    if (model.getSize().equals("small")) {
                                        Log.e("increase","coming 2"+model.getSize());

                                        if (Shopmodel.getSmall() != 0) {
                                            Log.e("increase","coming 3"+model.getSize());

                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                            ref.child(model.getCategoryID()).child("items")
                                                    .child(model.getItemID())
                                                    .child(model.getSize())
                                                    .setValue(Shopmodel.getSmall() - 1)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.e("increase","coming 4"+model.getSize());
                                                }
                                            });
                                        }else {
                                            Toast.makeText(context, "No more items left", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (model.getSize().equals("medium")) {
                                        Log.e("increase","coming 22"+model.getSize());
                                        if (Shopmodel.getMedium() != 0) {
                                            Log.e("increase","coming 23"+model.getSize());
                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                            ref.child(model.getCategoryID()).child("items")
                                                    .child(model.getItemID())
                                                    .child(model.getSize())
                                                    .setValue(Shopmodel.getMedium() - 1);
                                        }else {
                                            Toast.makeText(context, "No more items left", Toast.LENGTH_SHORT).show();
                                        }

                                    } else if (model.getSize().equals("large")) {

                                        if (Shopmodel.getLarge() != 0) {
                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                            ref.child(model.getCategoryID()).child("items")
                                                    .child(model.getItemID())
                                                    .child(model.getSize())
                                                    .setValue(Shopmodel.getLarge() - 1);
                                        }else {
                                            Toast.makeText(context, "No more items left", Toast.LENGTH_SHORT).show();
                                        }

                                    } else if (model.getSize().equals("extraLarge")) {
                                        if (Shopmodel.getExtraLarge() != 0) {
                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                            ref.child(model.getCategoryID()).child("items")
                                                    .child(model.getItemID())
                                                    .child(model.getSize())
                                                    .setValue(Shopmodel.getExtraLarge() - 1);
                                        }else {
                                            Toast.makeText(context, "No more items left", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    // }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    });

                }
            });

            cartViewHolder.count_quant.setText(String.valueOf(model.getTotalitem()));

            cartViewHolder.greentick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cart_data.get(position).isSelected()){

                        cartViewHolder.greentick.setVisibility(View.GONE);
                        cartViewHolder.greytick.setVisibility(View.VISIBLE);

                        cart_data.get(position).setSelected(false);
                    }
                    else {
                        cart_data.get(position).setSelected(true);
                        cartViewHolder.greytick.setVisibility(View.GONE);
                        cartViewHolder.greentick.setVisibility(View.VISIBLE);
                    }
                }
            });
            cartViewHolder.greytick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cart_data.get(position).isSelected()){

                        cartViewHolder.greentick.setVisibility(View.GONE);
                        cartViewHolder.greytick.setVisibility(View.VISIBLE);
                        cart_data.get(position).setSelected(false);
                    }
                    else {
                        cart_data.get(position).setSelected(true);
                        cartViewHolder.greytick.setVisibility(View.GONE);
                        cartViewHolder.greentick.setVisibility(View.VISIBLE);
                    }
                }
            });

            cartViewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cart_data != null){

                        Log.e("cat",""+model.getCategoryID());

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
                                .child(cart_data.get(position).getCartID())
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        notifyDataSetChanged();
                                    }
                                });



                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Model Shopmodel = snapshot.getValue(Model.class);


                                if(model.getSize().equals("small")) {

                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                    assert Shopmodel != null;
                                    ref.child(model.getCategoryID()).child("items")
                                            .child(model.getItemID())
                                            .child(model.getSize())
                                            .setValue(Shopmodel.getSmall() + model.getTotalitem() );
                                } else if (model.getSize().equals("medium")){

                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                    assert Shopmodel != null;
                                    ref.child(model.getCategoryID()).child("items")
                                            .child(model.getItemID())
                                            .child(model.getSize())
                                            .setValue(Shopmodel.getMedium() + model.getTotalitem());

                                } else if (model.getSize().equals("large")){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                    assert Shopmodel != null;
                                    ref.child(model.getCategoryID()).child("items")
                                            .child(model.getItemID())
                                            .child(model.getSize())
                                            .setValue(Shopmodel.getLarge() + model.getTotalitem());

                                } else if (model.getSize().equals("extraLarge")){
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                    assert Shopmodel != null;
                                    ref.child(model.getCategoryID()).child("items")
                                            .child(model.getItemID())
                                            .child(model.getSize())
                                            .setValue(Shopmodel.getExtraLarge() + model.getTotalitem());
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Log.e("coming",cart_data.get(position).getCartID());
                        Log.e("comingCategories",cart_data.get(position).getCategoryID());
                    } else {
                        Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            WholeSaleViewHolder wholeSaleViewHolder = (WholeSaleViewHolder) holder;

            wholeSaleViewHolder.b1.setText(model.getTitle());
            wholeSaleViewHolder.b2.setText("$"+model.getPrice());
            wholeSaleViewHolder.smallVlaue.setText(""+model.getSmall());
            wholeSaleViewHolder.mediumValu.setText(""+model.getMedium());
            wholeSaleViewHolder.largeValue.setText(""+model.getLarge());
            wholeSaleViewHolder.extraLargeValue.setText(""+model.getExtraLarge());

            Glide.with(context).load(model.getImage()).into(wholeSaleViewHolder.WimageView);

            if (model.isSelected()){

                wholeSaleViewHolder.Wgreentick.setVisibility(View.VISIBLE);
                wholeSaleViewHolder.Wgreytick.setVisibility(View.GONE);
            }else {
                wholeSaleViewHolder.Wgreentick.setVisibility(View.VISIBLE);
                wholeSaleViewHolder.Wgreytick.setVisibility(View.GONE);
            }


            wholeSaleViewHolder.Wgreentick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cart_data.get(position).isSelected()){

                        wholeSaleViewHolder.Wgreentick.setVisibility(View.GONE);
                        wholeSaleViewHolder.Wgreytick.setVisibility(View.VISIBLE);

                        cart_data.get(position).setSelected(false);
                    }
                    else {
                        cart_data.get(position).setSelected(true);
                        wholeSaleViewHolder.Wgreytick.setVisibility(View.GONE);
                        wholeSaleViewHolder.Wgreentick.setVisibility(View.VISIBLE);
                    }
                }
            });
            wholeSaleViewHolder.Wgreytick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cart_data.get(position).isSelected()){

                        wholeSaleViewHolder.Wgreentick.setVisibility(View.GONE);
                        wholeSaleViewHolder.Wgreytick.setVisibility(View.VISIBLE);

                        cart_data.get(position).setSelected(false);
                    }
                    else {
                        cart_data.get(position).setSelected(true);
                        wholeSaleViewHolder.Wgreytick.setVisibility(View.GONE);
                        wholeSaleViewHolder.Wgreentick.setVisibility(View.VISIBLE);
                    }
                }
            });


            wholeSaleViewHolder.Wdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (cart_data != null){
                        Log.e("wholesale1111",cart_data.get(position).getCartID());
                        Log.e("wholesaleCategories",cart_data.get(position).getCategoryID());

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                .child(model.getCartID())
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //cart_data.remove(position);
                                        notifyDataSetChanged();
                                    }
                                });


                    mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("categories")
                            .child(model.getCategoryID()).child("items").child(model.getItemID());

                    Log.e("categoryID",""+model.getCategoryID());
                    Log.e("ItemId",""+ model.getItemID());



                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Model ShopModel = snapshot.getValue(Model.class);

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                                assert ShopModel != null;
                                ref.child(model.getCategoryID()).child("items")
                                        .child(model.getItemID())
                                        .child("small")
                                        .setValue(ShopModel.getSmall() + model.getSmall());

                                assert ShopModel != null;
                                ref.child(model.getCategoryID()).child("items")
                                        .child(model.getItemID())
                                        .child("medium")
                                        .setValue(ShopModel.getMedium() + model.getMedium());


                                ref.child(model.getCategoryID()).child("items")
                                        .child(model.getItemID())
                                        .child("large")
                                        .setValue(ShopModel.getLarge() + model.getLarge());


                                ref.child(model.getCategoryID()).child("items")
                                        .child(model.getItemID())
                                        .child("extraLarge")
                                        .setValue(ShopModel.getExtraLarge() + model.getExtraLarge());



                            Log.e("categoriesData",""+ snapshot);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                        Log.e("coming",cart_data.get(position).getCartID());
                    }
                }
            });

        }












    }


    @Override
    public int getItemCount() {
        return (cart_data == null) ? 0 : cart_data.size();
    }

    public class cartViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,greentick, greytick,del,decrease_btn,increase_btn,decrease_btn_pink;
        TextView t1,t2,t3,size;
        LinearLayout cart_item;

        TextView count_quant;
        public cartViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=(ImageView) itemView.findViewById(R.id.shoe);
            t1=(TextView) itemView.findViewById(R.id.cart_title);
            t2=(TextView) itemView.findViewById(R.id.cart_price);
            t3=(TextView) itemView.findViewById(R.id.cart_quantity);
            decrease_btn =(ImageView) itemView.findViewById(R.id.decrease_btn);
            increase_btn = (ImageView) itemView.findViewById(R.id.increase_btn);
            count_quant = (TextView) itemView.findViewById(R.id.edit_quantity);
            cart_item =(LinearLayout) itemView.findViewById(R.id.cart_item);
            greentick = (ImageView) itemView.findViewById(R.id.greenTick);
            greytick = (ImageView) itemView.findViewById(R.id.greyTick);
            del = (ImageView) itemView.findViewById(R.id.del);
            size = (TextView) itemView.findViewById(R.id.sizeItem);
           // decrease_btn_pink =(ImageView) itemView.findViewById(R.id.decrease_btn_pink);





        }
    }

    public class WholeSaleViewHolder extends RecyclerView.ViewHolder {
        ImageView WimageView,Wgreentick, Wgreytick,Wdel;
        TextView b1,b2,smallVlaue,mediumValu,largeValue,extraLargeValue;
        LinearLayout Wcart_item;
        public WholeSaleViewHolder(@NonNull View itemView) {
            super(itemView);

            WimageView = (ImageView) itemView.findViewById(R.id.Wshoe);
            Wcart_item = (LinearLayout) itemView.findViewById(R.id.Wcart_item);
            Wgreentick = (ImageView) itemView.findViewById(R.id.WgreenTick);
            Wgreytick = (ImageView) itemView.findViewById(R.id.WgreyTick);
            b1 = (TextView) itemView.findViewById(R.id.Wcart_title);
            b2 = (TextView) itemView.findViewById(R.id.Wcart_price);
            smallVlaue = (TextView) itemView.findViewById(R.id.smallValue);
            mediumValu = (TextView) itemView.findViewById(R.id.mediumValue);
            largeValue = (TextView) itemView.findViewById(R.id.largeValue);
            extraLargeValue = (TextView) itemView.findViewById(R.id.extraLargeValue);
            Wdel = (ImageView) itemView.findViewById(R.id.Wdel);

        }
    }

}
