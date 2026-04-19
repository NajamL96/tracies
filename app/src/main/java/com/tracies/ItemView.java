package com.tracies;

import static com.tracies.R.drawable.round_corner_pink;
import static com.tracies.R.drawable.size_btn_grey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tracies.Adapter.SliderAdapter;
import com.tracies.model.Cart_Model;
import com.tracies.model.Model;
import com.tracies.model.favoritelistModel;
import com.tracies.model.item_showModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ItemView extends AppCompatActivity {

    public static SliderView rcv;
    SliderAdapter adapter;
    ImageView slider,cart1,previous, next, back, addCart, wholesale,pop_image,cross_white,item_shopping,go_to_item,cross_item;
    TextView discrip,image_count,title,price,pop_title,pop_price,Sleftitems,MleftItem,LleftItem,EleftItem;
    ArrayList<item_showModel> data;
    String CartID = "";
    String itemID = "";
    String match = "";
    String categoryId = "";
    int totalItem;

    int wholeSmall ;
    int wholeMedium ;
    int wholeLarge ;
    int wholeExtraLarge ;

    ImageView done;
    String CartSet;
    Model model = null;
    item_showModel item_shomodel = null;
    favoritelistModel favoritelistModel = null;
    String  categoriesId = "123456";
    DatabaseReference mDatabase;
    String size = "size";
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
        setContentView(R.layout.activity_item_view);
        slider = (ImageView) findViewById(R.id.cart1);
        next = (ImageView) findViewById(R.id.next);
        previous = (ImageView) findViewById(R.id.previous);
        back = (ImageView) findViewById(R.id.back);
        discrip = (TextView) findViewById(R.id.discription);
        title = (TextView) findViewById(R.id.title);
        price = (TextView) findViewById(R.id.price);
        addCart = (ImageView) findViewById(R.id.addCart);
        done = (ImageView) findViewById(R.id.WholeSale_done);
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
        cross_white = (ImageView) findViewById(R.id.cross_item);
        image_count = (TextView) findViewById(R.id.imageCount);
        item_small = (LinearLayout) findViewById(R.id.item_small);
        item_medium = (LinearLayout) findViewById(R.id.item_medium);
        item_large = (LinearLayout) findViewById(R.id.item_large);
        item_extraLage = (LinearLayout) findViewById(R.id.item_extraExtra);
        small_2 = (TextView) findViewById(R.id.S_2);
        small_4 = (TextView) findViewById(R.id.S_4);
        medium_2 = (TextView) findViewById(R.id.M_2);
        medium_4 = (TextView) findViewById(R.id.M_4);
        large_2 = (TextView) findViewById(R.id.L_2);
        large_4 = (TextView) findViewById(R.id.L_4);
        extarLarge_2 = (TextView) findViewById(R.id.EL_2);
        extraLarge_4 = (TextView) findViewById(R.id.EL_4);
        MleftItem = (TextView) findViewById(R.id.MleftItem);
        LleftItem = (TextView) findViewById(R.id.LleftItem);
        EleftItem = (TextView) findViewById(R.id.EleftItem);
        item_shopping = (ImageView) findViewById(R.id.item_shopping);
        go_to_item = (ImageView) findViewById(R.id.go_to_item);
        cross_item = (ImageView) findViewById(R.id.cross_item);
        cart1 = (ImageView) findViewById(R.id.cart1);



        final Object object = getIntent().getSerializableExtra("detail");
        model = (Model)  object;

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

        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("categories").child(ShopFragment.categoryId).child("items");

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
                if (model.getSmall() >= 4) {
                    count = 0;
                    small_2.setBackgroundResource(round_corner_pink);
                    small_4.setBackgroundResource(size_btn_grey);

                    s = count + 2;

                    Whole_size = "small";
                    Log.e("totalcountsmall", "" + count);
                } else {
                    Toast.makeText(ItemView.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });
        small_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getSmall() >= 4) {
                    count = 0;
                    small_4.setBackgroundResource(round_corner_pink);
                    small_2.setBackgroundResource(size_btn_grey);
                    s = count + 4;
                    Whole_size = "small";

                }else {
                    Toast.makeText(ItemView.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });


        medium_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getMedium() >= 2) {
                    medium_count = 0;
                    medium_2.setBackgroundResource(round_corner_pink);
                    medium_4.setBackgroundResource(size_btn_grey);
                    m = medium_count + 2;
                    Log.e("totalcountmedium", "" + medium_count);
                }else {
                    Toast.makeText(ItemView.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }

            }
        });
        medium_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getMedium() >= 4) {
                    medium_count = 0;
                    medium_4.setBackgroundResource(round_corner_pink);
                    medium_2.setBackgroundResource(size_btn_grey);

                    m = medium_count + 4;

                    Whole_size = "medium";

                } else {
                    Toast.makeText(ItemView.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });



        large_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getLarge() >= 2) {
                    large_count = 0;
                    large_2.setBackgroundResource(round_corner_pink);
                    large_4.setBackgroundResource(size_btn_grey);
                    l = large_count + 2;
                    Whole_size = "large";

                }else {
                    Toast.makeText(ItemView.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });
        large_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getLarge() >= 4) {
                    large_count = 0;
                    large_4.setBackgroundResource(round_corner_pink);
                    large_2.setBackgroundResource(size_btn_grey);

                    l = large_count + 4;

                    Whole_size = "large";
//                item_small.setBackgroundResource(round_corner);
//                item_medium.setBackgroundResource(round_corner);
//                item_large.setBackgroundResource(round_corner_pink);
//                item_extraLage.setBackgroundResource(round_corner);
                }else {
                    Toast.makeText(ItemView.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }

            }
        });


        extarLarge_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (model.getExtraLarge() >= 2) {
                    extraLarge_count = 0;
                    extarLarge_2.setBackgroundResource(round_corner_pink);
                    extraLarge_4.setBackgroundResource(size_btn_grey);

                    e = extraLarge_count + 2;

                    Whole_size = "extraLarge";
//                item_small.setBackgroundResource(round_corner);
//                item_medium.setBackgroundResource(round_corner);
//                item_large.setBackgroundResource(round_corner);
//                item_extraLage.setBackgroundResource(round_corner_pink);
                } else {
                    Toast.makeText(ItemView.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });
        extraLarge_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getExtraLarge() >= 4) {
                    extraLarge_count = 0;
                    extraLarge_4.setBackgroundResource(round_corner_pink);
                    extarLarge_2.setBackgroundResource(size_btn_grey);

                    e = extraLarge_count + 4;

                    Whole_size = "extraLarge";
//                item_small.setBackgroundResource(round_corner);
//                item_medium.setBackgroundResource(round_corner);
//                item_large.setBackgroundResource(round_corner);
//                item_extraLage.setBackgroundResource(round_corner_pink);
                }
                else {
                    Toast.makeText(ItemView.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });

        cross_white.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                findViewById(R.id.select_an_option).setVisibility(View.GONE);
            }
        });

        cart1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemView.this,Cart.class);
                startActivity(intent);
            }
        });

//        small.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View view) {
//                size = "small";
//
//                small.setBackgroundResource(round_corner_pink);
//                medium.setBackgroundResource(round_corner);
//                large.setBackgroundResource(round_corner);
//                extralarge.setBackgroundResource(round_corner);
//
//
//                }
//            });
//        medium.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                size = "medium";
//                small.setBackgroundResource(round_corner);
//                medium.setBackgroundResource(round_corner_pink);
//                large.setBackgroundResource(round_corner);
//                extralarge.setBackgroundResource(round_corner);
//
//            }
//        });
//        large.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                size = "large";
//                small.setBackgroundResource(round_corner);
//                medium.setBackgroundResource(round_corner);
//                large.setBackgroundResource(round_corner_pink);
//                extralarge.setBackgroundResource(round_corner);
//
//            }
//        });
//        extralarge.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                size = "extraLarge";
//                small.setBackgroundResource(round_corner);
//                medium.setBackgroundResource(round_corner);
//                large.setBackgroundResource(round_corner);
//                extralarge.setBackgroundResource(round_corner_pink);
//
//
//            }
//        });


        Log.e("totalcount","" + T);

//        wholesale.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                findViewById(R.id.dialogs1).setVisibility(View.VISIBLE);
//                wholesale.bringToFront();
//            }
//        });
        item_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemView.this,Bottom_nav.class);
                startActivity(intent);
            }
        });
        go_to_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemView.this,Cart.class);
                startActivity(intent);
            }
        });
//        cross_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                findViewById(R.id.select_an_option).setVisibility(View.GONE);
//            }
//        });

        CartSet = String.valueOf(System.currentTimeMillis());


        wholesale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




//
//                model.setImage(model.getListimages().get(0));
//                model.setSelected(true);

                HashMap<String, Object> WholesaleFields = new HashMap<>();
                WholesaleFields.put("categoryID", model.getCategoryID());
                WholesaleFields.put("image", model.getListimages().get(0));
                WholesaleFields.put("itemID", model.getItemID());
                WholesaleFields.put("price", model.getPrice());
                WholesaleFields.put("large", model.getLarge());
                WholesaleFields.put("extraLarge", model.getExtraLarge());
                WholesaleFields.put("title", model.getTitle());
                WholesaleFields.put("selected", true);
                WholesaleFields.put("medium", model.getMedium());
                WholesaleFields.put("small", model.getSmall());
                wholeSale = 1;
                T = s + m + l + e;
                Log.e("totalcount","" + T);
                if (T < 8){
                    Toast.makeText(ItemView.this, "select minimum of 8 pieces", Toast.LENGTH_SHORT).show();
                }else {
                    findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);
                    FirebaseDatabase.getInstance().getReference("users")
                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("wholesaleCart")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                        Cart_Model C_Model = snapshot1.getValue(Cart_Model.class);
                                        assert C_Model != null;
                                        C_Model.setCartID(snapshot1.getKey());
                                        Log.e("snapshot007", "RUNINNG");
                                        if (model.getItemID().equals(C_Model.getItemID())) {
                                            Log.e("getItemID", "running");

                                            Log.e("SIZE0004", "yesSize" + C_Model.getSize());
                                            match = "found";
                                            CartID = C_Model.getCartID();
                                            itemID = C_Model.getItemID();
                                            categoryId = C_Model.getCategoryID();
                                            wholeSmall = C_Model.getSmall();
                                            wholeMedium = C_Model.getMedium();
                                            wholeLarge = C_Model.getLarge();
                                            wholeExtraLarge = C_Model.getExtraLarge();

                                            Log.e("arheH", "" + itemID);
                                            totalItem = C_Model.getTotalitem();
                                            break;

                                        } else {
                                            Log.e("else0015", "outside");
                                        }
                                    }
                                    if (match.equals("found")){

                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                .child(CartID)
                                                .child("small")
                                                .setValue(wholeSmall + s);
                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                .child(CartID)
                                                .child("medium")
                                                .setValue(wholeMedium + m);
                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                .child(CartID)
                                                .child("large")
                                                .setValue(wholeLarge + l);
                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                .child(CartID)
                                                .child("extraLarge")
                                                .setValue(wholeExtraLarge + e);

                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                .child(CartID)
                                                .child("totalItem")
                                                .setValue(totalItem + T);

                                        FirebaseDatabase.getInstance().getReference("categories")
                                                .child(model.getCategoryID()).child("items").child(model.getItemID())
                                                .child("small")
                                                .setValue(model.getSmall() - s);
                                        FirebaseDatabase.getInstance().getReference("categories")
                                                .child(model.getCategoryID()).child("items").child(model.getItemID())
                                                .child("medium")
                                                .setValue(model.getMedium() - m);
                                        FirebaseDatabase.getInstance().getReference("categories")
                                                .child(model.getCategoryID()).child("items").child(model.getItemID())
                                                .child("large")
                                                .setValue(model.getLarge() - l);
                                        FirebaseDatabase.getInstance().getReference("categories")
                                                .child(model.getCategoryID()).child("items").child(model.getItemID())
                                                .child("extraLarge")
                                                .setValue(model.getExtraLarge() - e);

                                    } else {


                                        Toast.makeText(ItemView.this, "Sucessfully added to cart", Toast.LENGTH_SHORT).show();

                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                .child(CartSet)
                                                .setValue(WholesaleFields);

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

//                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
//                            .child(CartSet)
//                            .child("price")
//                            .setValue(T * model.getPrice());

                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                .child(CartSet)
                                                .child("totalitem")
                                                .setValue(T);



//                    Intent intent = new Intent(ItemView.this,Cart.class);
//                    startActivity(intent);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                }


            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemView.this, Bottom_nav.class);
                startActivity(intent);
            }
        });



//        addCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.e("addToCart","buttonRunning");
//
////                model.setImage(model.getListimages().get(0));
////                model.setTotalitem(1);
////                model.setSelected(true);
//
//                HashMap<String, Object> CartFields = new HashMap<>();
//                CartFields.put("categoryID", model.getCategoryID());
//                CartFields.put("image", model.getListimages().get(0));
//                CartFields.put("itemID", model.getItemID());
//                CartFields.put("price", model.getPrice());
//                CartFields.put("size", size);
//                CartFields.put("totalitem", 1);
//                CartFields.put("title", model.getTitle());
//                CartFields.put("selected", true);
//
//
//
//                FirebaseDatabase.getInstance().getReference("users")
//                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("cart")
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                                    Cart_Model C_Model = snapshot1.getValue(Cart_Model.class);
//                                    assert C_Model != null;
//                                    C_Model.setCartID(snapshot1.getKey());
//                                    Log.e("snapshot007", "RUNINNG" + C_Model.getCartID());
//                                    if (model.getItemID().equals(C_Model.getItemID())) {
//                                        Log.e("getItemID", "running");
//                                        if (size.equals(C_Model.getSize())) {
//                                            Log.e("SIZE0004", "yesSize" + C_Model.getSize());
//                                            match = "found";
//                                            CartID = C_Model.getCartID();
//                                            itemID = C_Model.getItemID();
//                                            categoryId = C_Model.getCategoryID();
//
//                                            Log.e("arheH",""+itemID);
//                                            totalItem = C_Model.getTotalitem();
//                                            break;
//                                        } else {
//
//                                            match = "isfound";
//                                            Log.e("else0015", "insideYesRunning"+ match);
//
//                                        }
//                                    } else {
//                                        Log.e("else0015", "outside");
//                                    }
//                                }
//
//                                Log.e("found111555",""+ match);
//                                Log.e("cartId",""+ CartID);
//                                Log.e("itemID",""+ itemID);
//                                if (match.equals("found")){
//                                    findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);
//                                    Log.e("running","yesRuning");
//                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                            .child(CartID)
//                                            .child("totalitem")
//                                            .setValue(totalItem + 1);
//                                    Log.e("sizekeaarha"," "+size);
//                                    if (size.equals("small")) {
//                                        Log.e("smallArhaH","hanarhaha " + CartID);
//                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                        ref.child(categoryId).child("items")
//                                                .child(itemID)
//                                                .child("small")
//                                                .setValue(model.getSmall() - 1);
//                                    } else if (size.equals("medium")) {
//                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                        ref.child(categoryId).child("items")
//                                                .child(itemID)
//                                                .child("medium")
//                                                .setValue(model.getMedium() - 1);
//                                    } else if (size.equals("large")) {
//                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                        ref.child(categoryId).child("items")
//                                                .child(itemID)
//                                                .child("large")
//                                                .setValue(model.getLarge() - 1);
//                                    } else if (size.equals("extraLarge")) {
//                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                        ref.child(categoryId).child("items")
//                                                .child(itemID)
//                                                .child("extraLarge")
//                                                .setValue(model.getExtraLarge() - 1);
//                                    }
//                                } else {
//                    if (size.equals("size")) {
//
//                        Toast.makeText(ItemView.this, "Select the size", Toast.LENGTH_SHORT).show();
//                    } else if (size.equals("small")) {
//
//                        if (model.getSmall() >= 1) {
//                            findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);
//
////
//
//                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                    .child(CartSet)
//                                    .setValue(CartFields);
//
//                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                    .child(CartSet)
//                                    .child("size")
//                                    .setValue("small");
//
//                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                            ref.child(ShopFragment.categoriesId).child("items")
//                                    .child(model.getItemID())
//                                    .child("small")
//                                    .setValue(model.getSmall() - 1);
//                        } else {
//                            Toast.makeText(ItemView.this, "There is no Item left in this size  ", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    } else if (size.equals("medium")) {
//
//
//                        if (model.getMedium() >= 1) {
//
//                            Log.e("mediumcheck", "" + model.getMedium());
//
//                            findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);
//
//                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                    .child(CartSet)
//                                    .setValue(CartFields);
//
//                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                    .child(CartSet)
//                                    .child("size")
//                                    .setValue("medium");
//
//                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                            ref.child(ShopFragment.categoriesId).child("items")
//                                    .child(model.getItemID())
//                                    .child("medium")
//                                    .setValue(model.getMedium() - 1);
//
//                        } else {
//                            Toast.makeText(ItemView.this, "There is no Item left in this size  ", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    } else if (size.equals("large")) {
//
//                        if (model.getLarge() >= 1) {
//                            findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);
//
//                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                    .child(CartSet)
//                                    .setValue(CartFields);
//
//                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                    .child(CartSet)
//                                    .child("size")
//                                    .setValue("large");
//
//                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                            ref.child(ShopFragment.categoriesId).child("items")
//                                    .child(model.getItemID())
//                                    .child("large")
//                                    .setValue(model.getLarge() - 1);
//
//                        } else {
//                            Toast.makeText(ItemView.this, "There is no Item left in this size  ", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    } else if (size.equals("extraLarge")) {
//
//                        if (model.getExtraLarge() >= 1) {
//
//                            findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);
//
//                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                    .child(CartSet)
//                                    .setValue(CartFields);
//                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                    .child(CartSet)
//                                    .child("size")
//                                    .setValue("extraLarge");
//                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                            ref.child(ShopFragment.categoriesId).child("items")
//                                    .child(model.getItemID())
//                                    .child("extraLarge")
//                                    .setValue(model.getExtraLarge() - 1);
//
//                        } else {
//                            Toast.makeText(ItemView.this, "There is no Item left in this size  ", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                                }
//                FirebaseDatabase.getInstance().getReference("users")
//                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("cart")
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                Cart_Model cartModel = snapshot.getValue(Cart_Model.class);
//
//                                if (cartModel == null) {
//                                    if (size.equals("size")) {
//
//                                        Toast.makeText(ItemView.this, "Select the size", Toast.LENGTH_SHORT).show();
//                                    } else if (size.equals("small")) {
//
//                                        if (model.getSmall() >= 1) {
//                                            findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);
//
//                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                                    .child(CartSet)
//                                                    .setValue(CartFields);
//
//                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                                    .child(CartSet)
//                                                    .child("size")
//                                                    .setValue("small");
//
//                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                            ref.child(ShopFragment.categoriesId).child("items")
//                                                    .child(model.getItemID())
//                                                    .child("small")
//                                                    .setValue(model.getSmall() - 1);
//                                        } else {
//                                            Toast.makeText(ItemView.this, "There is no Item left in this size  ", Toast.LENGTH_SHORT).show();
//                                        }
//
//
//                                    } else if (size.equals("medium")) {
//
//
//                                        if (model.getMedium() >= 1) {
//
//                                            Log.e("mediumcheck", "" + model.getMedium());
//
//                                            findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);
//
//                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                                    .child(CartSet)
//                                                    .setValue(CartFields);
//
//                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                                    .child(CartSet)
//                                                    .child("size")
//                                                    .setValue("medium");
//
//                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                            ref.child(ShopFragment.categoriesId).child("items")
//                                                    .child(model.getItemID())
//                                                    .child("medium")
//                                                    .setValue(model.getMedium() - 1);
//
//                                        } else {
//                                            Toast.makeText(ItemView.this, "There is no Item left in this size  ", Toast.LENGTH_SHORT).show();
//                                        }
//
//
//                                    } else if (size.equals("large")) {
//
//                                        if (model.getLarge() >= 1) {
//                                            findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);
//
//                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                                    .child(CartSet)
//                                                    .setValue(CartFields);
//
//                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                                    .child(CartSet)
//                                                    .child("size")
//                                                    .setValue("large");
//
//                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                            ref.child(ShopFragment.categoriesId).child("items")
//                                                    .child(model.getItemID())
//                                                    .child("large")
//                                                    .setValue(model.getLarge() - 1);
//
//                                        } else {
//                                            Toast.makeText(ItemView.this, "There is no Item left in this size  ", Toast.LENGTH_SHORT).show();
//                                        }
//
//
//                                    } else if (size.equals("extraLarge")) {
//
//                                        if (model.getExtraLarge() >= 1) {
//
//                                            findViewById(R.id.select_an_option).setVisibility(View.VISIBLE);
//
//                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                                    .child(CartSet)
//                                                    .setValue(CartFields);
//                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                                    .child(CartSet)
//                                                    .child("size")
//                                                    .setValue("extraLarge");
//                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                            ref.child(ShopFragment.categoriesId).child("items")
//                                                    .child(model.getItemID())
//                                                    .child("extraLarge")
//                                                    .setValue(model.getExtraLarge() - 1);
//
//                                        } else {
//                                            Toast.makeText(ItemView.this, "There is no Item left in this size  ", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//
//                            }
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//
//
//
//
//
//
//
//
//
////         }
//        }
//                                   });

//                    int Sleft = model.getSmall();
//                    int Mleft = model.getMedium();
//                    int Lleft = model.getLarge();
//                    int Eleft = model.getExtraLarge();
//
//                    Sleftitems.setText("left " +  Sleft);
//                    MleftItem.setText("left " +  Mleft);
//                    LleftItem.setText("left " +  Lleft);
//                    EleftItem.setText("left " +  Eleft);




    }
}
