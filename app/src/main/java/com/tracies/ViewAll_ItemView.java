package com.tracies;

import static com.tracies.R.drawable.round_corner;
import static com.tracies.R.drawable.round_corner_pink;
import static com.tracies.R.drawable.size_btn_grey;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tracies.Adapter.SliderAdapter;
import com.tracies.model.Cart_Model;
import com.tracies.model.ViewModel;
import com.tracies.model.item_showModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class ViewAll_ItemView extends AppCompatActivity {

        public static SliderView rcv;
        SliderAdapter adapter;
        ImageView slider,cart1,previous, next, back, addCart, wholesale,pop_image,cross_white,item_shopping,go_to_item,cross_item;
        TextView discrip,image_count,title,price,pop_title,pop_price,Sleftitems,MleftItem,LleftItem,EleftItem;
        ArrayList<item_showModel> data;
        Cart_Model cart_model;
        Button done;
        ViewModel model = null;
        item_showModel item_shomodel = null;
        com.tracies.model.favoritelistModel favoritelistModel = null;
        String  categoriesId = "123456";
        DatabaseReference mDatabase;
        String size = "size";
        String CartSet;
        public static int wholeSale = 0;
        String Whole_size = "size";
        LinearLayout small,medium,large,extralarge;
        LinearLayout item_small, item_medium,item_large,item_extraLage;
        TextView small_2,small_4,medium_2,medium_4,large_2,large_4,extarLarge_2,extraLarge_4,wholesale_pieces;
        int count = 0;
        int medium_count = 0;
        int large_count = 0;
        int extraLarge_count = 0;
        int s,m,l,e,T;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_all_item_view);
            slider = (ImageView) findViewById(R.id.cart1);
            next = (ImageView) findViewById(R.id.next);
            previous = (ImageView) findViewById(R.id.previous);
            back = (ImageView) findViewById(R.id.back);
            discrip = (TextView) findViewById(R.id.discription);
            title = (TextView) findViewById(R.id.title);
            price = (TextView) findViewById(R.id.price);
            addCart = (ImageView) findViewById(R.id.addCart);
            done = (Button) findViewById(R.id.WholeSale_done);
            wholesale = (ImageView) findViewById(R.id.wholesale_btn);
            small =(LinearLayout) findViewById(R.id.small);
            medium = (LinearLayout) findViewById(R.id.medium);
            large = (LinearLayout) findViewById(R.id.large);
            extralarge = (LinearLayout) findViewById(R.id.extra_large);
            wholesale_pieces = (TextView) findViewById(R.id.wholesale_pieces);
            pop_image = (ImageView) findViewById(R.id.pop_image);
            pop_price = (TextView) findViewById(R.id.pop_price);
            pop_title = (TextView) findViewById(R.id.pop_title);
            Sleftitems = (TextView) findViewById(R.id.SleftItems);
            cross_white = (ImageView) findViewById(R.id.cross_white);
            image_count = (TextView) findViewById(R.id.imageCount);
            item_small = (LinearLayout) findViewById(R.id.item_small);
            item_medium = (LinearLayout) findViewById(R.id.item_medium);
            item_large = (LinearLayout) findViewById(R.id.item_large);
            item_extraLage = (LinearLayout) findViewById(R.id.item_extraExtra);
            small_2 = (TextView) findViewById(R.id.small_2);
            small_4 = (TextView) findViewById(R.id.small_4);
            medium_2 = (TextView) findViewById(R.id.medium_2);
            medium_4 = (TextView) findViewById(R.id.medium_4);
            large_2 = (TextView) findViewById(R.id.large_2);
            large_4 = (TextView) findViewById(R.id.large_4);
            extarLarge_2 = (TextView) findViewById(R.id.extraLarge_2);
            extraLarge_4 = (TextView) findViewById(R.id.extraLarge_4);
            MleftItem = (TextView) findViewById(R.id.MleftItem);
            LleftItem = (TextView) findViewById(R.id.LleftItem);
            EleftItem = (TextView) findViewById(R.id.EleftItem);
            item_shopping = (ImageView) findViewById(R.id.item_shopping);
            go_to_item = (ImageView) findViewById(R.id.go_to_item);
            cross_item = (ImageView) findViewById(R.id.cross_item);
            cart1 = (ImageView) findViewById(R.id.cart1);



            final Object object = getIntent().getSerializableExtra("detailView");
            model = (ViewModel)  object;
            rcv=(SliderView)findViewById(R.id.imageSlider);

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rcv.setCurrentPagePosition(rcv.getCurrentPagePosition() + 1);
                    rcv.stopAutoCycle();
                }
            });
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rcv.setCurrentPagePosition(rcv.getCurrentPagePosition() - 1);
                    rcv.stopAutoCycle();
                }
            });
            ArrayList<String> images = model.getListimages();
            rcv.setCurrentPageListener(new SliderView.OnSliderPageListener() {
                @Override
                public void onSliderPageChanged(int position) {
                    image_count.setText(position + 1 +"/"+ images.size());
                    if (position == 0){
                        Log.e("loaasds","loads" + position);
                        previous.setVisibility(View.GONE);
                        next.setVisibility(View.VISIBLE);
                    } else{
                        Log.e("loaasds","loads2" + position);
                        previous.setVisibility(View.VISIBLE);
                        if (position == images.size() - 1){
                            next.setVisibility(View.GONE);
                        }
                    }
                }
            });

            mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("categories").child(categoriesId).child("items");

            discrip.setText(model.getDescription());
            title.setText(model.getTitle());
            price.setText("$" + model.getPrice());
            pop_title.setText(model.getTitle());
            pop_price.setText("$"+model.getPrice());
            Glide.with(getApplicationContext()).load(model.getListimages().get(0)).into(pop_image);

            adapter = new SliderAdapter(model.getListimages(),getApplicationContext());

            rcv.setSliderAdapter(adapter);



            small_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count = 0;
                    small_2.setBackgroundResource(round_corner_pink);
                    small_4.setBackgroundResource(size_btn_grey);

                    s = count + 2;

                    Whole_size = "small";
//                item_small.setBackgroundResource(round_corner_pink);
//                item_medium.setBackgroundResource(round_corner);
//                item_large.setBackgroundResource(round_corner);
//                item_extraLage.setBackgroundResource(round_corner);

                    Log.e("totalcountsmall","" + count);


                }
            });
            small_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count = 0;
                    small_4.setBackgroundResource(round_corner_pink);
                    small_2.setBackgroundResource(size_btn_grey);

                    s = count + 4;

                    Whole_size = "small";
//                item_small.setBackgroundResource(round_corner_pink);
//                item_medium.setBackgroundResource(round_corner);
//                item_large.setBackgroundResource(round_corner);
//                item_extraLage.setBackgroundResource(round_corner);


                }
            });


            medium_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    medium_count = 0;
                    medium_2.setBackgroundResource(round_corner_pink);
                    medium_4.setBackgroundResource(size_btn_grey);

                    m = medium_count + 2;

                    Whole_size = "medium";
//                item_small.setBackgroundResource(round_corner);
//                item_medium.setBackgroundResource(round_corner_pink);
//                item_large.setBackgroundResource(round_corner);
//                item_extraLage.setBackgroundResource(round_corner);

                    Log.e("totalcountmedium","" + medium_count);

                }
            });
            medium_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    medium_count = 0;
                    medium_4.setBackgroundResource(round_corner_pink);
                    medium_2.setBackgroundResource(size_btn_grey);

                    m =  medium_count + 4;

                    Whole_size = "medium";
//                item_small.setBackgroundResource(round_corner);
//                item_medium.setBackgroundResource(round_corner_pink);
//                item_large.setBackgroundResource(round_corner);
//                item_extraLage.setBackgroundResource(round_corner);


                }
            });



            large_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    large_count = 0;
                    large_2.setBackgroundResource(round_corner_pink);
                    large_4.setBackgroundResource(size_btn_grey);
                    l = large_count + 2;
                    Whole_size = "large";
//                item_small.setBackgroundResource(round_corner);
//                item_medium.setBackgroundResource(round_corner);
//                item_large.setBackgroundResource(round_corner_pink);
//                item_extraLage.setBackgroundResource(round_corner);


                }
            });
            large_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    large_count = 0;
                    large_4.setBackgroundResource(round_corner_pink);
                    large_2.setBackgroundResource(size_btn_grey);

                    l = large_count + 4;

                    Whole_size = "large";
//                item_small.setBackgroundResource(round_corner);
//                item_medium.setBackgroundResource(round_corner);
//                item_large.setBackgroundResource(round_corner_pink);
//                item_extraLage.setBackgroundResource(round_corner);

                }
            });


            extarLarge_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    extraLarge_count = 0;
                    extarLarge_2.setBackgroundResource(round_corner_pink);
                    extraLarge_4.setBackgroundResource(size_btn_grey);

                    e = extraLarge_count +2;

                    Whole_size = "extraLarge";
//                item_small.setBackgroundResource(round_corner);
//                item_medium.setBackgroundResource(round_corner);
//                item_large.setBackgroundResource(round_corner);
//                item_extraLage.setBackgroundResource(round_corner_pink);


                }
            });
            extraLarge_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    extraLarge_count = 0;
                    extraLarge_4.setBackgroundResource(round_corner_pink);
                    extarLarge_2.setBackgroundResource(size_btn_grey);

                    e = extraLarge_count +4;

                    Whole_size = "extraLarge";
//                item_small.setBackgroundResource(round_corner);
//                item_medium.setBackgroundResource(round_corner);
//                item_large.setBackgroundResource(round_corner);
//                item_extraLage.setBackgroundResource(round_corner_pink);


                }
            });

            cross_white.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    findViewById(R.id.dialogs1).setVisibility(View.GONE);
                }
            });

            cart1.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ViewAll_ItemView.this,Cart.class);
                    startActivity(intent);
                }
            });










            small.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    size = "small";

                    small.setBackgroundResource(round_corner_pink);
                    medium.setBackgroundResource(round_corner);
                    large.setBackgroundResource(round_corner);
                    extralarge.setBackgroundResource(round_corner);


                }
            });
            medium.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    size = "medium";
                    small.setBackgroundResource(round_corner);
                    medium.setBackgroundResource(round_corner_pink);
                    large.setBackgroundResource(round_corner);
                    extralarge.setBackgroundResource(round_corner);

                }
            });
            large.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    size = "large";
                    small.setBackgroundResource(round_corner);
                    medium.setBackgroundResource(round_corner);
                    large.setBackgroundResource(round_corner_pink);
                    extralarge.setBackgroundResource(round_corner);

                }
            });
            extralarge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    size = "extraLarge";
                    small.setBackgroundResource(round_corner);
                    medium.setBackgroundResource(round_corner);
                    large.setBackgroundResource(round_corner);
                    extralarge.setBackgroundResource(round_corner_pink);


                }
            });


            Log.e("totalcount","" + T);

            wholesale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findViewById(R.id.dialogs1).setVisibility(View.VISIBLE);
                    wholesale.bringToFront();
                }
            });
            item_shopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ViewAll_ItemView.this,Bottom_nav.class);
                    startActivity(intent);
                }
            });
            go_to_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ViewAll_ItemView.this,Cart.class);
                    startActivity(intent);
                }
            });
            cross_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    findViewById(R.id.select_an_option).setVisibility(View.GONE);
                }
            });

            CartSet = String.valueOf(System.currentTimeMillis());


            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    model.setImage(model.getListimages().get(0));
                    model.setSelected(true);


                    wholeSale = 1;

                    T = s + m + l + e;
                    Log.e("totalcount","" + T);


                    if (T < 8){
                        Toast.makeText(ViewAll_ItemView.this, "select minimum of 8 pieces", Toast.LENGTH_SHORT).show();
                    }else {
                        findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);


                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                .child(CartSet)
                                .setValue(model);


                        mDatabase.child(model.getItemID())
                                .child("small")
                                .setValue(model.getSmall() - s);

                        mDatabase.child(model.getItemID())
                                .child("medium")
                                .setValue(model.getMedium() - m);

                        mDatabase.child(model.getItemID())
                                .child("large")
                                .setValue(model.getLarge() - l);

                        mDatabase.child(model.getItemID())
                                .child("extraLarge")
                                .setValue(model.getExtraLarge() - e);

                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                .child(CartSet)
                                .child("small")
                                .setValue(s);

                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                .child(CartSet)
                                .child("medium")
                                .setValue(m);

                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                .child(CartSet)
                                .child("large")
                                .setValue(l);

                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                .child(CartSet)
                                .child("extraLarge")
                                .setValue(e);

                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                .child(CartSet)
                                .child("type")
                                .setValue("wholesale");

                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                .child(CartSet)
                                .child("price")
                                .setValue(T * model.getPrice());

                        findViewById(R.id.dialogs1).setVisibility(View.GONE);

//                        Intent intent = new Intent(ViewAll_ItemView.this,Cart.class);
//                        startActivity(intent);

                    }


                }
            });


            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ViewAll_ItemView.this, Bottom_nav.class);
                    startActivity(intent);
                }
            });



            addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    model.setImage(model.getListimages().get(0));
                    model.setTotalitem(1);
                    model.setSelected(true);

                    if (size == "size"){

                        Toast.makeText(ViewAll_ItemView.this, "Select the size", Toast.LENGTH_SHORT).show();
                    }
                    else if (size == "small"){
                        if (model.getSmall() >= 1) {
                            findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
                                    .child(CartSet)
                                    .setValue(model);

                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
                                    .child(CartSet)
                                    .child("size")
                                    .setValue("small");

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                            ref.child(categoriesId).child("items")
                                    .child(model.getItemID())
                                    .child("small")
                                    .setValue(model.getSmall() - 1);
                        } else {
                            Toast.makeText(ViewAll_ItemView.this, "There is no Item left in this size  ", Toast.LENGTH_SHORT).show();
                        }



                    }else if (size == "medium"){


                        if (model.getMedium() >= 1) {

                            Log.e("mediumcheck", "" + model.getMedium());

                            findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
                                    .child(CartSet)
                                    .setValue(model);

                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
                                    .child(CartSet)
                                    .child("size")
                                    .setValue("medium");

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                            ref.child(categoriesId).child("items")
                                    .child(model.getItemID())
                                    .child("medium")
                                    .setValue(model.getMedium() - 1);

                        } else {
                            Toast.makeText(ViewAll_ItemView.this, "There is no Item left in this size  ", Toast.LENGTH_SHORT).show();
                        }




                    }else if (size == "large"){

                        if (model.getLarge() >= 1){
                            findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
                                    .child(CartSet)
                                    .setValue(model);

                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
                                    .child(CartSet)
                                    .child("size")
                                    .setValue("large");

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                            ref.child(categoriesId).child("items")
                                    .child(model.getItemID())
                                    .child("large")
                                    .setValue(model.getLarge() - 1);

                        } else {
                            Toast.makeText(ViewAll_ItemView.this, "There is no Item left in this size  ", Toast.LENGTH_SHORT).show();
                        }


                    }else if (size == "extraLarge"){

                        if (model.getExtraLarge() >= 1){

                            findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
                                    .child(CartSet)
                                    .setValue(model);
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
                                    .child(CartSet)
                                    .child("size")
                                    .setValue("extraLarge");
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
                            ref.child(categoriesId).child("items")
                                    .child(model.getItemID())
                                    .child("extraLarge")
                                    .setValue(model.getExtraLarge() - 1);

                        } else {
                            Toast.makeText(ViewAll_ItemView.this, "There is no Item left in this size  ", Toast.LENGTH_SHORT).show();
                        }


                    }



                }
            });

            int Sleft = model.getSmall();
            int Mleft = model.getMedium();
            int Lleft = model.getLarge();
            int Eleft = model.getExtraLarge();

            Sleftitems.setText("left " +  Sleft);
            MleftItem.setText("left " +  Mleft);
            LleftItem.setText("left " +  Lleft);
            EleftItem.setText("left " +  Eleft);




        }
    }
