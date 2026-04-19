package com.tracies;

import static com.tracies.Adapter.favoriteAdapter.FavListStatic;
import static com.tracies.R.drawable.round_corner_pink;
import static com.tracies.R.drawable.size_btn_grey;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tracies.Adapter.favoriteAdapter;
import com.tracies.model.Cart_Model;
import com.tracies.model.FavoriteModel;
import com.tracies.model.favoritelistModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class fragment_favorite extends Fragment {

    RecyclerView rcv;
    favoriteAdapter adapter;
    ArrayList<favoritelistModel> data, alldata;
    DatabaseReference mDatabase, vDatabase;
    favoritelistModel favoritelistmodel;
    String categoriesId = "123456";
    EditText fav_search;
    String CartSet;
    String CartID = "";
    String itemID = "";
    String match = "";
    String categoryId = "";
    int totalItem;
    ImageView fav_cart,cross_w,WholeSale_Fav;
    ArrayList<String> fav_List;
    ArrayList<String> wait_List;
    ArrayList<FavoriteModel> favoriteArrayList;
    Button btn_done;
    TextView shop_small,shop_medium,shop_large,shop_extraLarge;
    public static String size = "small";
    public static ConstraintLayout favorite_select_size, wholeSalePopUpFav;
    TextView small_2,small_4,medium_2,medium_4,large_2,large_4,extarLarge_2,extraLarge_4;
    int count = 0;
    int medium_count = 0;
    int large_count = 0;
    int extraLarge_count = 0;
    int small,medium,large,extraLarge,Total;
    String Whole_size = "size";
    public static TextView Fav_pop_price,Fav_pop_title;
    public static ImageView Fav_pop_image;


    int wholeSmall ;
    int wholeMedium ;
    int wholeLarge ;
    int wholeExtraLarge ;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        rcv = (RecyclerView) rootView.findViewById(R.id.favoriteRecyclerView);

        fav_search = (EditText) rootView.findViewById(R.id.fav_search);
        cross_w = (ImageView) rootView.findViewById(R.id.cross_w);
        Fav_pop_price = (TextView) rootView.findViewById(R.id.Fav_pop_price);
        Fav_pop_title = (TextView) rootView.findViewById(R.id.Fav_pop_title);
        Fav_pop_image = (ImageView) rootView.findViewById(R.id.Fav_pop_image);

        fav_cart = (ImageView) rootView.findViewById(R.id.fav_Cart);

        shop_small = (TextView) rootView.findViewById(R.id.shop_small);
        shop_medium=  (TextView) rootView.findViewById(R.id.shop_medium);
        shop_large = (TextView) rootView.findViewById(R.id.shop_large);
        shop_extraLarge = (TextView) rootView.findViewById(R.id.shop_extraLarge);

        WholeSale_Fav = (ImageView) rootView.findViewById(R.id.WholeSale_Fav);

       // cross_white = (ImageView) rootView.findViewById(R.id.cross_white);

        small_2 = (TextView) rootView.findViewById(R.id.small_2);
        small_4 = (TextView) rootView.findViewById(R.id.small_4);
        medium_2 = (TextView) rootView.findViewById(R.id.medium_2);
        medium_4 = (TextView) rootView.findViewById(R.id.medium_4);
        large_2 = (TextView) rootView.findViewById(R.id.large_2);
        large_4 = (TextView) rootView.findViewById(R.id.large_4);
        extarLarge_2 = (TextView) rootView.findViewById(R.id.extraLarge_2);
        extraLarge_4 = (TextView) rootView.findViewById(R.id.extraLarge_4);

        favorite_select_size = (ConstraintLayout) rootView.findViewById(R.id.select_size);
        wholeSalePopUpFav = (ConstraintLayout) rootView.findViewById(R.id.wholeSalePopUpFav);

        btn_done = (Button) rootView.findViewById(R.id.btn_done);

        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).child("favorites");

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rcv.setLayoutManager(layoutManager);

        alldata = new ArrayList<>();
        data = new ArrayList<>();
        wait_List = new ArrayList<>();
        fav_List = new ArrayList<>();
        favoriteArrayList = new ArrayList<>();

        adapter = new favoriteAdapter(data, getActivity());
        rcv.setAdapter(adapter);


        fav_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Cart.class);
                startActivity(intent);

            }
        });

        cross_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootView.findViewById(R.id.select_size).setVisibility(View.GONE);
            }
        });

        fav_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {

                    edit_search(fav_search.getText().toString());
                } else {

                    edit_search("");
                }

            }
        });
        small_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( FavListStatic.getSmall() >= 2) {
                    count = 0;
                    small_2.setBackgroundResource(round_corner_pink);
                    small_4.setBackgroundResource(size_btn_grey);

                    small = count + 2;


                    Whole_size = "small";

                    Log.e("totalcountsmall", "" + small);
                } else {
                    Toast.makeText(getActivity(), "Not enough items left", Toast.LENGTH_SHORT).show();
                }
            }
        });
        small_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FavListStatic.getSmall() >= 4) {
                    count = 0;
                    small_4.setBackgroundResource(round_corner_pink);
                    small_2.setBackgroundResource(size_btn_grey);
                    small = count + 4;
                    Whole_size = "small";

                }else {
                    Toast.makeText(getActivity(), "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });


        medium_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FavListStatic.getMedium() >= 2) {
                    medium_count = 0;
                    medium_2.setBackgroundResource(round_corner_pink);
                    medium_4.setBackgroundResource(size_btn_grey);
                    medium = medium_count + 2;
                    Log.e("totalcountmedium", "" + medium_count);
                }else {
                    Toast.makeText(getActivity(), "Not enough items left", Toast.LENGTH_SHORT).show();
                }

            }
        });
        medium_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FavListStatic.getMedium() >= 4) {
                    medium_count = 0;
                    medium_4.setBackgroundResource(round_corner_pink);
                    medium_2.setBackgroundResource(size_btn_grey);
                    medium = medium_count + 4;

                    Whole_size = "medium";

                } else {
                    Toast.makeText(getActivity(), "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });



        large_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FavListStatic.getLarge() >= 2) {
                    large_count = 0;
                    large_2.setBackgroundResource(round_corner_pink);
                    large_4.setBackgroundResource(size_btn_grey);
                    large = large_count + 2;
                    Whole_size = "large";

                }else {
                    Toast.makeText(getActivity(), "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });
        large_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FavListStatic.getLarge() >= 4) {
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
                    Toast.makeText(getActivity(), "Not enough items left", Toast.LENGTH_SHORT).show();
                }

            }
        });


        extarLarge_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FavListStatic.getExtraLarge() >= 2) {
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
                    Toast.makeText(getActivity(), "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });
        extraLarge_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FavListStatic.getExtraLarge() >= 4) {
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
                    Toast.makeText(getActivity(), "Not enough items left", Toast.LENGTH_SHORT).show();
                }


            }
        });

        CartSet = String.valueOf(System.currentTimeMillis());

        WholeSale_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Total = small + medium + large + extraLarge;
                Log.e("totalitems",""+FavListStatic.getItemID());
                if (Total >= 8) {
                    Log.e("called777","working");
                    wholeSalePopUpFav.setVisibility(View.GONE);
                    if (FavListStatic.isSelected()) {
                        FirebaseDatabase.getInstance().getReference("users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("wholesaleCart")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                            Cart_Model C_Model = snapshot1.getValue(Cart_Model.class);
                                            assert C_Model != null;
                                            C_Model.setCartID(snapshot1.getKey());
                                            Log.e("snapshot007", "RUNINNG" + FavListStatic.getItemID());
                                            if (FavListStatic.getItemID().equals(C_Model.getItemID())) {
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
                                            Log.e("yesbootomsmall", "" + FavListStatic.getSmall());
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
                                                    .child(FavListStatic.getCategoryID()).child("items").child(FavListStatic.getItemID())
                                                    .child("small")
                                                    .setValue(FavListStatic.getSmall() - small);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(FavListStatic.getCategoryID()).child("items").child(FavListStatic.getItemID())
                                                    .child("medium")
                                                    .setValue(FavListStatic.getMedium() - medium);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(FavListStatic.getCategoryID()).child("items").child(FavListStatic.getItemID())
                                                    .child("large")
                                                    .setValue(FavListStatic.getLarge() - large);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(FavListStatic.getCategoryID()).child("items").child(FavListStatic.getItemID())
                                                    .child("extraLarge")
                                                    .setValue(FavListStatic.getExtraLarge() - extraLarge);


                                        } else {

                                            Log.e("categoryfavoritecoming",""+ FavListStatic.getCategoryID());
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(FavListStatic.getCategoryID()).child("items").child(FavListStatic.getItemID())
                                                    .child("small")
                                                    .setValue(FavListStatic.getSmall() - small);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(FavListStatic.getCategoryID()).child("items").child(FavListStatic.getItemID())
                                                    .child("medium")
                                                    .setValue(FavListStatic.getMedium() - medium);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(FavListStatic.getCategoryID()).child("items").child(FavListStatic.getItemID())
                                                    .child("large")
                                                    .setValue(FavListStatic.getLarge() - large);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(FavListStatic.getCategoryID()).child("items").child(FavListStatic.getItemID())
                                                    .child("extraLarge")
                                                    .setValue(FavListStatic.getExtraLarge() - extraLarge);

                                            HashMap<String, Object> wholeCartFields = new HashMap<>();

                                            wholeCartFields.put("categoryID", FavListStatic.getCategoryID());
                                            wholeCartFields.put("image", FavListStatic.getListimages().get(0));
                                            wholeCartFields.put("itemID", FavListStatic.getItemID());
                                            wholeCartFields.put("price", FavListStatic.getPrice());
                                            wholeCartFields.put("small", small);
                                            wholeCartFields.put("medium", medium);
                                            wholeCartFields.put("large", large);
                                            wholeCartFields.put("extraLarge", extraLarge);
                                            wholeCartFields.put("totalitem", Total);
                                            wholeCartFields.put("title", FavListStatic.getTitle());
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
                    Toast.makeText(getActivity(), "Select minimum of 8 pieces", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        shop_small.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                size = "small";
//                shop_small.setBackgroundResource(round_corner_pink);
//                shop_medium.setBackgroundResource(round_corner);
//                shop_large.setBackgroundResource(round_corner);
//                shop_extraLarge.setBackgroundResource(round_corner);
//
//
//            }
//        });
//        shop_medium.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                size = "medium";
//                shop_small.setBackgroundResource(round_corner);
//                shop_medium.setBackgroundResource(round_corner_pink);
//                shop_large.setBackgroundResource(round_corner);
//                shop_extraLarge.setBackgroundResource(round_corner);
//
//            }
//        });
//        shop_large.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                size = "large";
//                shop_small.setBackgroundResource(round_corner);
//                shop_medium.setBackgroundResource(round_corner);
//                shop_large.setBackgroundResource(round_corner_pink);
//                shop_extraLarge.setBackgroundResource(round_corner);
//
//            }
//        });
//        shop_extraLarge.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                size = "extraLarge";
//
//                shop_small.setBackgroundResource(round_corner);
//                shop_medium.setBackgroundResource(round_corner);
//                shop_large.setBackgroundResource(round_corner);
//                shop_extraLarge.setBackgroundResource(round_corner_pink);
//
//            }
//        });

//        CartSet = String.valueOf(System.currentTimeMillis());
//
//        btn_done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                favorite_select_size.setVisibility(View.GONE);
//
//                for (int i = 0; i < data.size(); i++) {
//
//                    if (data.get(i).isSelected()){
//
//                        Log.e("itemID", "" + data.get(i).getItemID());
//
//                        favoritelistModel model = data.get(i);
////                        model.setImage(model.getListimages().get(0));
////                        model.setTotalitem(1);
//
//                        HashMap<String, Object> CartFields = new HashMap<>();
//                        CartFields.put("categoryID", model.getCategoryID());
//                        CartFields.put("image", model.getListimages().get(0));
//                        CartFields.put("itemID", model.getItemID());
//                        CartFields.put("price", model.getPrice());
//                        CartFields.put("size", size);
//                        CartFields.put("totalitem", 1);
//                        CartFields.put("title", model.getTitle());
//                        CartFields.put("selected", true);
//
//                        Log.e("category",""+ model.getCategoryID());
//                        Log.e("itemID",""+ model.getItemID());
//                        Log.e("small",""+ model.getSmall());
//
//                        FirebaseDatabase.getInstance().getReference("users")
//                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("cart")
//                                .addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                                            Cart_Model C_Model = snapshot1.getValue(Cart_Model.class);
//                                            assert C_Model != null;
//                                            C_Model.setCartID(snapshot1.getKey());
//                                            Log.e("snapshot007", "RUNINNG" + C_Model.getCartID());
//                                            if (model.getItemID().equals(C_Model.getItemID())) {
//                                                Log.e("getItemID", "running");
//                                                if (size.equals(C_Model.getSize())) {
//                                                    Log.e("SIZE0004", "yesSize" + C_Model.getSize());
//                                                    match = "found";
//                                                    CartID = C_Model.getCartID();
//                                                    itemID = C_Model.getItemID();
//                                                    categoryId = C_Model.getCategoryID();
//                                                    Log.e("arheH",""+itemID);
//                                                    totalItem = C_Model.getTotalitem();
//                                                    break;
//                                                } else {
//
//                                                    match = "isfound";
//                                                    Log.e("else0015", "insideYesRunning"+ match);
//
//                                                }
//                                            } else {
//                                                Log.e("else0015", "outside");
//                                            }
//                                        }
//                                        if (match.equals("found")){
//                                            Log.e("running","yesRuning");
//                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                                    .child(CartID)
//                                                    .child("totalitem")
//                                                    .setValue(totalItem + 1);
//                                            Log.e("sizekeaarha"," "+size);
//                                            if (size.equals("small")) {
//                                                Log.e("smallArhaH","hanarhaha " + CartID);
//                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                                ref.child(categoryId).child("items")
//                                                        .child(itemID)
//                                                        .child("small")
//                                                        .setValue(model.getSmall() - 1);
//                                            } else if (size.equals("medium")) {
//                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                                ref.child(categoryId).child("items")
//                                                        .child(itemID)
//                                                        .child("medium")
//                                                        .setValue(model.getMedium() - 1);
//                                            } else if (size.equals("large")) {
//                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                                ref.child(categoryId).child("items")
//                                                        .child(itemID)
//                                                        .child("large")
//                                                        .setValue(model.getLarge() - 1);
//                                            } else if (size.equals("extraLarge")) {
//                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                                ref.child(categoryId).child("items")
//                                                        .child(itemID)
//                                                        .child("extraLarge")
//                                                        .setValue(model.getExtraLarge() - 1);
//                                            }
//                                        } else {
//                                            FirebaseDatabase.getInstance().getReference("categories")
//                                                    .child(model.getCategoryID()).child("items").child(model.getItemID())
//                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                                                        @Override
//                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                            Model ShopModel = snapshot.getValue(Model.class);
//                                                            if (size.equals("small")) {
//                                                                Log.e("ifSmall",""+model.getSmall());
//
//                                                                FirebaseDatabase.getInstance().getReference("categories")
//                                                                        .child(model.getCategoryID()).child("items").child(model.getItemID())
//                                                                        .child("small")
//                                                                        .setValue(ShopModel.getSmall() - 1);
//
//                                                            } else if (size.equals("medium")){
//
//                                                                FirebaseDatabase.getInstance().getReference("categories")
//                                                                        .child(model.getCategoryID()).child("items").child(model.getItemID())
//                                                                        .child("medium")
//                                                                        .setValue(ShopModel.getMedium() - 1);
//
//                                                            }else if (size.equals("large")){
//
//                                                                FirebaseDatabase.getInstance().getReference("categories")
//                                                                        .child(model.getCategoryID()).child("items").child(model.getItemID())
//                                                                        .child("large")
//                                                                        .setValue(ShopModel.getLarge() - 1);
//                                                            }
//                                                            else if (size.equals("extraLarge")){
//
//                                                                FirebaseDatabase.getInstance().getReference("categories")
//                                                                        .child(model.getCategoryID()).child("items").child(model.getItemID())
//                                                                        .child("extraLarge")
//                                                                        .setValue(ShopModel.getExtraLarge() - 1);
//                                                            }
//                                                        }
//
//                                                        @Override
//                                                        public void onCancelled(@NonNull DatabaseError error) {
//
//                                                        }
//                                                    });
//
//
//
//                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                                    .child(CartSet)
//                                                    .setValue(CartFields);
//
//                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                                    .child(CartSet).child("size")
//                                                    .setValue(size);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//                    }
//                }
//            }
//        });

        getWaitListNew();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getFavoritesNew();
            }
        }, 1500);

        return rootView;

    }
    void getWaitListNew() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid())
                .child("waitlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                wait_List.clear();
                for (DataSnapshot child: snapshot.getChildren()){
                    wait_List.add(child.getKey());
                }
                for(int i=0;i<data.size();i++) {
                    if(wait_List.contains(data.get(i).getItemID()))
                        data.get(i).setWait(true);
                    else data.get(i).setWait(false);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void getFavoritesNew() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid())
                .child("favorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fav_List.clear();
                favoriteArrayList.clear();
                data.clear();
                for (DataSnapshot child: snapshot.getChildren()){
                    fav_List.add(child.getKey());
                    favoriteArrayList.add(new FavoriteModel(child.getValue(String.class), child.getKey()));
                }
                Log.e("favoriteList", "" + favoriteArrayList.size());
                if(favoriteArrayList.size() == 0)
                    adapter.notifyDataSetChanged();
                else
                    getProductDetail(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getProductDetail(int index) {
        FirebaseDatabase.getInstance().getReference().child("categories")
                .child(favoriteArrayList.get(index).getCategoryID())
                .child("items")
                .child(favoriteArrayList.get(index).getProductID())
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("favoriteList", "" + snapshot);
                favoritelistModel favoritelistmodelNew = snapshot.getValue(favoritelistModel.class);
                if(favoritelistmodelNew != null) {
                    Log.e("favoriteList", "Found");
                    favoritelistmodelNew.setItemID(snapshot.getKey());
                    favoritelistmodelNew.setCategoryID(favoriteArrayList.get(index).getCategoryID());
                    ArrayList<String> Listimages = new ArrayList<>();
                    for (DataSnapshot snapshot2 : snapshot.child("images").getChildren()) {
                        Log.e("images123", "" + snapshot2);
                        Listimages.add(snapshot2.getValue().toString());
                    }
                    favoritelistmodelNew.setListimages(Listimages);
                    favoritelistmodelNew.setFav(true);
                    if(wait_List.contains(snapshot.getKey()))
                        favoritelistmodelNew.setWait(true);
                    else
                        favoritelistmodelNew.setWait(false);
                    data.add(favoritelistmodelNew);
                    Log.e("fsafa","called");
                    adapter.notifyDataSetChanged();
                }
                if(index + 1 < favoriteArrayList.size())
                    getProductDetail(index + 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void edit_search(String editable) {


        alldata.clear();
        for (favoritelistModel favoritelistmodel : data) {
            if (favoritelistmodel.getTitle().toLowerCase().contains(editable.toLowerCase())) {

                Log.e("check123456",""+ favoritelistmodel.getTitle());
                alldata.add(favoritelistmodel);

            }

        }
        Log.e("check123",""+ alldata.size());
        adapter = new favoriteAdapter(alldata, getActivity());
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

//    private void getwaitlist() {
//        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid())
//                .child("waitlist").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                wait_List.clear();
//
//                for (DataSnapshot child: snapshot.getChildren()){
//
//                    Log.e("wait_LIST123",""+ child.getKey());
//
//                    wait_List.add(child.getKey());
//
//                }
//                //getfavorites();
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//    private void getfavorites() {
//        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid())
//                .child("favorites").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                fav_List.clear();
//
//                for (DataSnapshot child: snapshot.getChildren()){
//
//                    Log.e("fav_LIST123",""+ child.getKey());
//
//                    fav_List.add(child.getKey());
//
//                }
//                //getData();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


//    private void getData() {
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                data.clear();
//
//                Log.e("titileCheck123", "Called");
//                for (DataSnapshot child : snapshot.getChildren()) {
//
//
//                    vDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("categories").child(child.getValue().toString()).child("items").child(child.getKey());
//
//                    vDatabase.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                            favoritelistmodel = dataSnapshot.getValue(favoritelistModel.class);
//                            favoritelistmodel.setItemID(dataSnapshot.getKey());
//                            favoritelistmodel.setCategoriesId(categoriesId);
//
//                            if (fav_List.contains(favoritelistmodel.getItemID())){
//                                Log.e("favoriteHEreIF","" + fav_List);
//                                favoritelistmodel.setFav(true);
//                            }else{
//                                Log.e("favoriteHEreElse","" + fav_List);
//                                favoritelistmodel.setFav(false);
//                            }
//                            if (wait_List.contains(favoritelistmodel.getItemID())) {
//                                Log.e("favoriteHEreIF123123", "" + wait_List);
//                                favoritelistmodel.setWait(true);
//                            } else {
//                                Log.e("favoriteHEreElse123123", "" + wait_List);
//                                favoritelistmodel.setWait(false);
//                            }
//
//
//                            ArrayList<String> Listimages = new ArrayList<>();
//
//                            for (DataSnapshot snapshot2 : dataSnapshot.child("images").getChildren()) {
//
//                                Log.e("images123", "" + snapshot2);
//                                Listimages.add(snapshot2.getValue().toString());
//
//
//                            }
//
//                            favoritelistmodel.setListimages(Listimages);
//
//
//                            data.add(favoritelistmodel);
//
//
//
//                        }
//
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//
//                    });
//
//                }
//                adapter.notifyDataSetChanged();
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


}
