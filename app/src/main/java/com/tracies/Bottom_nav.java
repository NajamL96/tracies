package com.tracies;
import static com.tracies.Adapter.MyAdapter.bottomModel;
import static com.tracies.R.drawable.round_corner_pink;
import static com.tracies.R.drawable.size_btn_grey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tracies.model.Cart_Model;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Bottom_nav extends AppCompatActivity implements View.OnClickListener {

    LinearLayout homeTab, categoryTab, cartTab, profileTab;
    BottomNavigationView bottomNavigationView;
    ImageView home, category, cart, profile,homeLine,categoryLine, cartLine, profileLine ;
    public static ImageView pop_image;
    ArrayList<String> fav_List;
    public static ConstraintLayout wholeSalePopup;
    public static ConstraintLayout size_selection;
    ImageView WholeSale_done,cross_white;
    String CartSet;
    String CartID = "";
    String itemID = "";
    String match = "";
    String categoryId = "";
    int totalItem;
    public static TextView pop_price,pop_title;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;
    public static String size = "small";
    Fragment fragment1 = new ShopFragment();
    Fragment fragment2 = new fragment_waitList();
    Fragment fragment3 = new fragment_favorite();
    Fragment fragment4 = new fragment_account();
    TextView small_2,small_4,medium_2,medium_4,large_2,large_4,extarLarge_2,extraLarge_4,wholesale_pieces;
    int count = 0;
    int medium_count = 0;
    int large_count = 0;
    int extraLarge_count = 0;
    int small,medium,large,extraLarge,Total;
    String Whole_size = "size";

    int wholeSmall ;
    int wholeMedium ;
    int wholeLarge ;
    int wholeExtraLarge ;

    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment activeFragment;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        wholeSalePopup = (ConstraintLayout) findViewById(R.id.wholeSalePopUp);
        home = findViewById(R.id.bottom_home_img);
        category = findViewById(R.id.bottom_category_img);
        cart = findViewById(R.id.bottom_cart_img);
        profile = findViewById(R.id.bottom_profile_img);

        cross_white = (ImageView) findViewById(R.id.cross_white);

        small_2 = (TextView) findViewById(R.id.small_2);
        small_4 = (TextView) findViewById(R.id.small_4);
        medium_2 = (TextView) findViewById(R.id.medium_2);
        medium_4 = (TextView) findViewById(R.id.medium_4);
        large_2 = (TextView) findViewById(R.id.large_2);
        large_4 = (TextView) findViewById(R.id.large_4);
        extarLarge_2 = (TextView) findViewById(R.id.extraLarge_2);
        extraLarge_4 = (TextView) findViewById(R.id.extraLarge_4);

        pop_image = (ImageView) findViewById(R.id.pop_image);
        pop_price = (TextView) findViewById(R.id.pop_price);
        pop_title = (TextView) findViewById(R.id.pop_title);

        homeTab = findViewById(R.id.home_tab);
        categoryTab = findViewById(R.id.category_tab);
        cartTab = findViewById(R.id.cart_tab);
        profileTab = findViewById(R.id.profile_tab);
        WholeSale_done = findViewById(R.id.WholeSale_finish);


        size_selection = findViewById(R.id.select_size);

        homeLine = findViewById(R.id.home_line);
        categoryLine = findViewById(R.id.category_line);
        cartLine = findViewById(R.id.cart_line);
        profileLine = findViewById(R.id.profile_line);

        fragmentManager.beginTransaction().add(R.id.frameLayout, fragment1, "1").commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, fragment2, "2").hide(fragment2).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, fragment3, "3").hide(fragment3).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, fragment4, "4").hide(fragment4).commit();
        activeFragment = fragment1;

        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).child("favorites");

        home.setImageResource(R.drawable.shop_icon);
        category.setImageResource(R.drawable.waitlist_icon);
        cart.setImageResource(R.drawable.favorites);
        profile.setImageResource(R.drawable.account_icon_unsel);

        homeLine.setImageResource(R.drawable.shop_sel);
        categoryLine.setImageResource(R.drawable.waitlist_sel_t);
        cartLine.setImageResource(R.drawable.fav);
        profileLine.setImageResource(R.drawable.acc_sel);

        //checkUser();



        homeTab.setOnClickListener(this);
        categoryTab.setOnClickListener(this);
        cartTab.setOnClickListener(this);
        profileTab.setOnClickListener(this);

        cross_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wholeSalePopup.setVisibility(View.GONE);
            }
        });




        small_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("TESTMODEL", bottomModel.getItemID());

                if (bottomModel.getSmall() >= 2) {
                    count = 0;
                    small_2.setBackgroundResource(round_corner_pink);
                    small_4.setBackgroundResource(size_btn_grey);

                    small = count + 2;
                    Log.e("BottomModel",""+bottomModel.getSmall());

                    Whole_size = "small";

                    Log.e("totalcountsmall", "" + small);
                } else {
                    Toast.makeText(Bottom_nav.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }
            }
        });
        small_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomModel.getSmall() >= 4) {
                    count = 0;
                    small_4.setBackgroundResource(round_corner_pink);
                    small_2.setBackgroundResource(size_btn_grey);
                    small = count + 4;
                    Whole_size = "small";

                }else {
                    Toast.makeText(Bottom_nav.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });


        medium_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomModel.getMedium() >= 2) {
                    medium_count = 0;
                    medium_2.setBackgroundResource(round_corner_pink);
                    medium_4.setBackgroundResource(size_btn_grey);
                    medium = medium_count + 2;
                    Log.e("totalcountmedium", "" + medium_count);
                }else {
                    Toast.makeText(Bottom_nav.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }

            }
        });
        medium_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomModel.getMedium() >= 4) {
                    medium_count = 0;
                    medium_4.setBackgroundResource(round_corner_pink);
                    medium_2.setBackgroundResource(size_btn_grey);
                    medium = medium_count + 4;

                    Whole_size = "medium";

                } else {
                    Toast.makeText(Bottom_nav.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });



        large_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomModel.getLarge() >= 2) {
                    large_count = 0;
                    large_2.setBackgroundResource(round_corner_pink);
                    large_4.setBackgroundResource(size_btn_grey);
                    large = large_count + 2;
                    Whole_size = "large";

                }else {
                    Toast.makeText(Bottom_nav.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });
        large_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomModel.getLarge() >= 4) {
                    large_count = 0;
                    large_4.setBackgroundResource(round_corner_pink);
                    large_2.setBackgroundResource(size_btn_grey);

                    large = large_count + 4;

                    Whole_size = "large";
//                item_small.setBackgroundResource(round_corner);
//                item_medium.setBackgroundResource(round_corner);
//                item_large.setBackgroundResource(round_corner_pink);
//                item_extraLage.setBackgroundResource(round_corner);
                }else {
                    Toast.makeText(Bottom_nav.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }

            }
        });


        extarLarge_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bottomModel.getExtraLarge() >= 2) {
                    extraLarge_count = 0;
                    extarLarge_2.setBackgroundResource(round_corner_pink);
                    extraLarge_4.setBackgroundResource(size_btn_grey);

                    extraLarge = extraLarge_count + 2;

                    Whole_size = "extraLarge";
//                item_small.setBackgroundResource(round_corner);
//                item_medium.setBackgroundResource(round_corner);
//                item_large.setBackgroundResource(round_corner);
//                item_extraLage.setBackgroundResource(round_corner_pink);
                } else {
                    Toast.makeText(Bottom_nav.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });
        extraLarge_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomModel.getExtraLarge() >= 4) {
                    extraLarge_count = 0;
                    extraLarge_4.setBackgroundResource(round_corner_pink);
                    extarLarge_2.setBackgroundResource(size_btn_grey);

                    extraLarge = extraLarge_count + 4;

                    Whole_size = "extraLarge";
//                item_small.setBackgroundResource(round_corner);
//                item_medium.setBackgroundResource(round_corner);
//                item_large.setBackgroundResource(round_corner);
//                item_extraLage.setBackgroundResource(round_corner_pink);
                }
                else {
                    Toast.makeText(Bottom_nav.this, "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });








        CartSet = String.valueOf(System.currentTimeMillis());

        WholeSale_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Total = small + medium + large + extraLarge;
                Log.e("totalitems",""+Total);
                if (Total >= 8) {
                    Log.e("called777","working");
                    wholeSalePopup.setVisibility(View.GONE);
                    if (bottomModel.isSelected()) {
                        FirebaseDatabase.getInstance().getReference("users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("wholesaleCart")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                            Cart_Model C_Model = snapshot1.getValue(Cart_Model.class);
                                            assert C_Model != null;
                                            C_Model.setCartID(snapshot1.getKey());
                                            Log.e("snapshot007", "RUNINNG" + bottomModel.getItemID());
                                            if (bottomModel.getItemID().equals(C_Model.getItemID())) {
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
                                        if (match.equals("found")) {
                                            Log.e("running", "yesRuning");
                                            Log.e("yesbootomsmall", "" + bottomModel.getSmall());
                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                    .child(CartID)
                                                    .child("small")
                                                    .setValue(wholeSmall + small);
                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                    .child(CartID)
                                                    .child("medium")
                                                    .setValue(wholeMedium + medium);
                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                    .child(CartID)
                                                    .child("large")
                                                    .setValue(wholeLarge + large);
                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                    .child(CartID)
                                                    .child("extraLarge")
                                                    .setValue(wholeExtraLarge + extraLarge);

                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(bottomModel.getCategoryID()).child("items").child(bottomModel.getItemID())
                                                    .child("small")
                                                    .setValue(bottomModel.getSmall() - small);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(bottomModel.getCategoryID()).child("items").child(bottomModel.getItemID())
                                                    .child("medium")
                                                    .setValue(bottomModel.getMedium() - medium);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(bottomModel.getCategoryID()).child("items").child(bottomModel.getItemID())
                                                    .child("large")
                                                    .setValue(bottomModel.getLarge() - large);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(bottomModel.getCategoryID()).child("items").child(bottomModel.getItemID())
                                                    .child("extraLarge")
                                                    .setValue(bottomModel.getExtraLarge() - extraLarge);


                                        } else {
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(bottomModel.getCategoryID()).child("items").child(bottomModel.getItemID())
                                                    .child("small")
                                                    .setValue(bottomModel.getSmall() - small);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(bottomModel.getCategoryID()).child("items").child(bottomModel.getItemID())
                                                    .child("medium")
                                                    .setValue(bottomModel.getMedium() - medium);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(bottomModel.getCategoryID()).child("items").child(bottomModel.getItemID())
                                                    .child("large")
                                                    .setValue(bottomModel.getLarge() - large);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(bottomModel.getCategoryID()).child("items").child(bottomModel.getItemID())
                                                    .child("extraLarge")
                                                    .setValue(bottomModel.getExtraLarge() - extraLarge);

                                            HashMap<String, Object> wholeCartFields = new HashMap<>();

                                            wholeCartFields.put("categoryID", bottomModel.getCategoryID());
                                            wholeCartFields.put("image", bottomModel.getListimages().get(0));
                                            wholeCartFields.put("itemID", bottomModel.getItemID());
                                            wholeCartFields.put("price", bottomModel.getPrice());
                                            wholeCartFields.put("small", small);
                                            wholeCartFields.put("medium", medium);
                                            wholeCartFields.put("large", large);
                                            wholeCartFields.put("extraLarge", extraLarge);
                                            wholeCartFields.put("totalitem", Total);
                                            wholeCartFields.put("title", bottomModel.getTitle());
                                            wholeCartFields.put("selected", true);

                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                    .child(CartSet)
                                                    .setValue(wholeCartFields);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }

                } else {
                    Toast.makeText(Bottom_nav.this, "Select minimum of 8 pieces", Toast.LENGTH_SHORT).show();
                }
            }
        });






}

    //custom bottom bar click listner
    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_tab:
                if (activeFragment != fragment1) {
                    fragmentManager.beginTransaction().show(fragment1).hide(activeFragment).commit();
                    activeFragment = fragment1;

                    home.setImageResource(R.drawable.shop_icon);
                    category.setImageResource(R.drawable.waitlist_icon);
                    cart.setImageResource(R.drawable.favorites);
                    profile.setImageResource(R.drawable.account_icon_unsel);

                    homeLine.setImageResource(R.drawable.shop_sel);
                    categoryLine.setImageResource(R.drawable.waitlist_sel_t);
                    cartLine.setImageResource(R.drawable.fav);
                    profileLine.setImageResource(R.drawable.acc_sel);




                }
                break;
            case R.id.category_tab:
                if (activeFragment != fragment2) {
                    fragmentManager.beginTransaction().show(fragment2).hide(activeFragment).commit();
                    activeFragment = fragment2;

                    home.setImageResource(R.drawable.shop_icon_unsel);
                    category.setImageResource(R.drawable.waitlist_icon_sel);
                    cart.setImageResource(R.drawable.favorites);
                    profile.setImageResource(R.drawable.account_icon_unsel);


                    homeLine.setImageResource(R.drawable.shop_unsel);
                    categoryLine.setImageResource(R.drawable.waitlist_unsel_t);
                    cartLine.setImageResource(R.drawable.fav);
                    profileLine.setImageResource(R.drawable.acc_sel);



                }
                break;
            case R.id.cart_tab:
                if (activeFragment != fragment3) {
                    fragmentManager.beginTransaction().show(fragment3).hide(activeFragment).commit();
                    activeFragment = fragment3;

                    home.setImageResource(R.drawable.shop_icon_unsel);
                    category.setImageResource(R.drawable.waitlist_icon_unsel);
                    cart.setImageResource(R.drawable.favorites_fill);
                    profile.setImageResource(R.drawable.account_icon_unsel);

                    homeLine.setImageResource(R.drawable.shop_unsel);
                    categoryLine.setImageResource(R.drawable.waitlist_sel_t);
                    cartLine.setImageResource(R.drawable.fav);
                    profileLine.setImageResource(R.drawable.acc_sel);

                }
                break;
            case R.id.profile_tab:
                if (activeFragment != fragment4) {
                    fragmentManager.beginTransaction().show(fragment4).hide(activeFragment).commit();
                    activeFragment = fragment4;

                    home.setImageResource(R.drawable.shop_icon_unsel);
                    category.setImageResource(R.drawable.waitlist_icon_unsel);
                    cart.setImageResource(R.drawable.favorites);
                    profile.setImageResource(R.drawable.account_icon_sel);

                    homeLine.setImageResource(R.drawable.shop_unsel);
                    categoryLine.setImageResource(R.drawable.waitlist_sel_t);
                    cartLine.setImageResource(R.drawable.fav);
                    profileLine.setImageResource(R.drawable.acc_unsel);

                }

                break;
        }

    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}

