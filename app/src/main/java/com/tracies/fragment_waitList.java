package com.tracies;

import static com.tracies.Adapter.waitlistAdapter.waitListStatic;
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

import com.tracies.Adapter.waitlistAdapter;
import com.tracies.model.Cart_Model;
import com.tracies.model.FavoriteModel;
import com.tracies.model.waitlistModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class fragment_waitList extends Fragment {

    RecyclerView rcv;
    waitlistAdapter adapter;
    ArrayList<waitlistModel> data, alldata;
    EditText waitlist_search;
    ImageView wait_cart,cross_w,WholeSale_done,Wait_cross_white;
    ArrayList<String> fav_List;
    Button btn_done;
    String CartSet;
    String CartID = "";
    String itemID = "";
    String match = "";
    String categoryId = "";
    int totalItem;

    public static ConstraintLayout wait_select_size,wholeSalePopUpwait;
    ArrayList<String> wait_List;
    String categoriesId = "123456";
    ArrayList<FavoriteModel> favoriteArrayList;
    TextView shop_small,shop_medium,shop_large,shop_extraLarge;
    public static String size = "small";
    TextView small_2,small_4,medium_2,medium_4,large_2,large_4,extarLarge_2,extraLarge_4,wholesale_pieces;
    int count = 0;
    int medium_count = 0;
    int large_count = 0;
    int extraLarge_count = 0;
    int small,medium,large,extraLarge,Total;
    String Whole_size = "size";
    public static TextView Wait_pop_price,Wait_pop_title;
    public static ImageView Wait_pop_image;


    int wholeSmall ;
    int wholeMedium ;
    int wholeLarge ;
    int wholeExtraLarge ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_waitlist, container, false);


    rcv=(RecyclerView) rootView.findViewById(R.id.waitListRecyclerView);
        shop_small = (TextView) rootView.findViewById(R.id.shop_small);
        shop_medium=  (TextView) rootView.findViewById(R.id.shop_medium);
        shop_large = (TextView) rootView.findViewById(R.id.shop_large);
        shop_extraLarge = (TextView) rootView.findViewById(R.id.shop_extraLarge);
        WholeSale_done = (ImageView) rootView.findViewById(R.id.WholeSale_WAitList);

        Wait_pop_image = (ImageView) rootView.findViewById(R.id.Wait_pop_image);
        Wait_pop_price = (TextView) rootView.findViewById(R.id.Wait_pop_price);
        Wait_pop_title = (TextView) rootView.findViewById(R.id.Wait_pop_title);

        small_2 = (TextView) rootView.findViewById(R.id.small_2);
        small_4 = (TextView) rootView.findViewById(R.id.small_4);
        medium_2 = (TextView) rootView.findViewById(R.id.medium_2);
        medium_4 = (TextView) rootView.findViewById(R.id.medium_4);
        large_2 = (TextView) rootView.findViewById(R.id.large_2);
        large_4 = (TextView) rootView.findViewById(R.id.large_4);
        extarLarge_2 = (TextView) rootView.findViewById(R.id.extraLarge_2);
        extraLarge_4 = (TextView) rootView.findViewById(R.id.extraLarge_4);

        wait_select_size = (ConstraintLayout) rootView.findViewById(R.id.select_size);
        wholeSalePopUpwait = (ConstraintLayout) rootView.findViewById(R.id.wholeSalePopUpWait);

        cross_w = (ImageView) rootView.findViewById(R.id.cross_w);
        Wait_cross_white = (ImageView) rootView.findViewById(R.id.Wait_cross_white);

        btn_done = (Button) rootView.findViewById(R.id.btn_done);

    waitlist_search = (EditText) rootView.findViewById(R.id.waitlist_search);

    wait_cart = (ImageView) rootView.findViewById(R.id.wait_Cart);


    StaggeredGridLayoutManager layoutManager= new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(  StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rcv.setLayoutManager(layoutManager);

        data = new ArrayList<waitlistModel>();
        alldata = new ArrayList<>();
        fav_List = new ArrayList<>();
        wait_List = new ArrayList<>();
        favoriteArrayList = new ArrayList<>();

         adapter = new waitlistAdapter(data,getActivity());
        rcv.setAdapter(adapter);



        wait_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Cart.class);
                startActivity(intent);

            }
        });

        waitlist_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){

                    edit_search(editable.toString());
                }
                else {

                    edit_search("");
                }

            }
        });

//        cross_w.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rootView.findViewById(R.id.select_size).setVisibility(View.GONE);
//            }
//        });
        Wait_cross_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wholeSalePopUpwait.setVisibility(View.GONE);
            }
        });

//
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

        small_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if ( waitListStatic.getSmall() >= 2) {
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
                if (waitListStatic.getSmall() >= 4) {
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
                if (waitListStatic.getMedium() >= 2) {
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
                if (waitListStatic.getMedium() >= 4) {
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
                if (waitListStatic.getLarge() >= 2) {
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
                if (waitListStatic.getLarge() >= 4) {
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

                if (waitListStatic.getExtraLarge() >= 2) {
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
                if (waitListStatic.getExtraLarge() >= 4) {
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

        WholeSale_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Total = small + medium + large + extraLarge;
                Log.e("totalitems",""+Total);
                if (Total >= 8) {
                    Log.e("called777","working");
                    wholeSalePopUpwait.setVisibility(View.GONE);
                    if (waitListStatic.isSelected()) {
                        FirebaseDatabase.getInstance().getReference("users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("wholesaleCart")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                            Cart_Model C_Model = snapshot1.getValue(Cart_Model.class);
                                            assert C_Model != null;
                                            C_Model.setCartID(snapshot1.getKey());
                                            Log.e("snapshot007", "RUNINNG" + waitListStatic.getItemID());
                                            if (waitListStatic.getItemID().equals(C_Model.getItemID())) {
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
                                            Log.e("yesbootomsmall", "" + waitListStatic.getSmall());
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
                                                    .child(waitListStatic.getCategoryID()).child("items").child(waitListStatic.getItemID())
                                                    .child("small")
                                                    .setValue(waitListStatic.getSmall() - small);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(waitListStatic.getCategoryID()).child("items").child(waitListStatic.getItemID())
                                                    .child("medium")
                                                    .setValue(waitListStatic.getMedium() - medium);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(waitListStatic.getCategoryID()).child("items").child(waitListStatic.getItemID())
                                                    .child("large")
                                                    .setValue(waitListStatic.getLarge() - large);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(waitListStatic.getCategoryID()).child("items").child(waitListStatic.getItemID())
                                                    .child("extraLarge")
                                                    .setValue(waitListStatic.getExtraLarge() - extraLarge);


                                        } else {
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(waitListStatic.getCategoryID()).child("items").child(waitListStatic.getItemID())
                                                    .child("small")
                                                    .setValue(waitListStatic.getSmall() - small);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(waitListStatic.getCategoryID()).child("items").child(waitListStatic.getItemID())
                                                    .child("medium")
                                                    .setValue(waitListStatic.getMedium() - medium);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(waitListStatic.getCategoryID()).child("items").child(waitListStatic.getItemID())
                                                    .child("large")
                                                    .setValue(waitListStatic.getLarge() - large);
                                            FirebaseDatabase.getInstance().getReference("categories")
                                                    .child(waitListStatic.getCategoryID()).child("items").child(waitListStatic.getItemID())
                                                    .child("extraLarge")
                                                    .setValue(waitListStatic.getExtraLarge() - extraLarge);

                                            HashMap<String, Object> wholeCartFields = new HashMap<>();

                                            wholeCartFields.put("categoryID", waitListStatic.getCategoryID());
                                            wholeCartFields.put("image", waitListStatic.getListimages().get(0));
                                            wholeCartFields.put("itemID", waitListStatic.getItemID());
                                            wholeCartFields.put("price", waitListStatic.getPrice());
                                            wholeCartFields.put("small", small);
                                            wholeCartFields.put("medium", medium);
                                            wholeCartFields.put("large", large);
                                            wholeCartFields.put("extraLarge", extraLarge);
                                            wholeCartFields.put("totalitem", Total);
                                            wholeCartFields.put("title", waitListStatic.getTitle());
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

        getFavoritesNew();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getWaitListNew();
            }
        }, 1500);


return rootView;

}



    private void edit_search(String editable) {


        alldata.clear();
        for (waitlistModel waitlistmodel: data){
            if (waitlistmodel.getTitle().toLowerCase().contains(editable.toLowerCase())){
                alldata.add(waitlistmodel);

            }
        }
        adapter = new waitlistAdapter(alldata, getActivity());
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }


    void getFavoritesNew() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid())
                .child("favorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                wait_List.clear();
                for (DataSnapshot child: snapshot.getChildren()){
                    wait_List.add(child.getKey());
                }
                for(int i=0;i<data.size();i++) {
                    if(wait_List.contains(data.get(i).getItemID()))
                        data.get(i).setFav(true);
                    else data.get(i).setFav(false);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void getWaitListNew() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid())
                .child("waitlist").addValueEventListener(new ValueEventListener() {
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
                        waitlistModel favoritelistmodelNew = snapshot.getValue(waitlistModel.class);
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
                            favoritelistmodelNew.setWait(true);
                            if(wait_List.contains(snapshot.getKey()))
                                favoritelistmodelNew.setFav(true);
                            else
                                favoritelistmodelNew.setFav(false);
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



//    private void getfavorites() {
//        if(data.size() != 0) {
//            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid())
//                    .child("favorites").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    fav_List.clear();
//                    for (DataSnapshot child: snapshot.getChildren()){
//                        Log.e("fav_LIST123",""+ child.getKey());
//                        fav_List.add(child.getKey());
//                        boolean found = false;
//                        int foundIndex = 0;
//                        for(int i=0;i<data.size();i++) {
//                            if(data.get(i).getItemID().equals(child.getKey())) {
//                                data.get(i).setFav(true);
//                                found = true;
//                                foundIndex = i;
//                                break;
//                            }
//                        }
//                        if(!found)
//                            data.get(foundIndex).setFav(false);
//                        adapter.notifyDataSetChanged();
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//    }
//
//
//
//    private void getData() {
//        FirebaseDatabase.getInstance().getReference("users")
//                .child(FirebaseAuth.getInstance().getUid()).child("waitlist").addValueEventListener(new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                categoriesID.clear();
//                itemsID.clear();
//                for (DataSnapshot child: snapshot.getChildren()){
//                    itemsID.add(child.getKey());
//                    categoriesID.add(child.getValue(String.class));
//                }
//                if(categoriesID.size() != 0) {
//                    data.clear();
//                    getItemData(0);
//                }
//                else {
//                    data.clear();
//                    adapter.notifyDataSetChanged();
//                }
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
//    private void getItemData(int index) {
//        FirebaseDatabase.getInstance().getReference("categories")
//                .child(categoriesID.get(index)).child("items").child(itemsID.get(index))
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Log.e("titlecheck", "" + dataSnapshot);
//
//                waitlistModel waitlistModel = dataSnapshot.getValue(waitlistModel.class);
//                waitlistModel.setItemID(dataSnapshot.getKey());
//                waitlistModel.setCategoriesId(categoriesID.get(index));
//                waitlistModel.setWait(true);
//                ArrayList<String> Listimages =new ArrayList<>();
//                for (DataSnapshot snapshot2 : dataSnapshot.child("images").getChildren()) {
//                    Log.e("images123","" + snapshot2 );
//                    Listimages.add(snapshot2.getValue().toString());
//                }
//                waitlistModel.setListimages(Listimages);
//                waitlistModel.setFav(false);
//
//                data.add(waitlistModel);
//                //Log.e("ima","" + data );
//                adapter.notifyDataSetChanged();
//                if(index + 1 < categoriesID.size())
//                    getItemData(index + 1);
//                else
//                    getfavorites();
//
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

}