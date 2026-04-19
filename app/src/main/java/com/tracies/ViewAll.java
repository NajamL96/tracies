package com.tracies;

import static com.tracies.R.drawable.round_corner;
import static com.tracies.R.drawable.round_corner_pink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tracies.Adapter.ViewAll_Adapter;
import com.tracies.model.Cart_Model;
import com.tracies.model.Model;
import com.tracies.model.ViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ViewAll extends AppCompatActivity {


    RecyclerView viewAllRecycler;
    ViewAll_Adapter viewall_adapter;
    ImageView cart,search_btn,fav_img;
    ArrayList<Model> data, alldata,fav_data;
    FirebaseRecyclerOptions options;
    TextView welcome,categoriesName;
    String CartSet;
    String CartID = "";
    String itemID = "";
    String match = "";
    String categoryId = "";
    int totalItem;
    EditText search;
    Model viewModel;
    Chip C_tops,C_bottom;
    DatabaseReference mDatabase;
    public static int card = 1;
    String categoriesId;
    ArrayList<String> fav_List;
    ArrayList<String> wait_List;
    public static String size = "small";
    Button btn_done;
    ImageView cross_w;
    TextView shop_small,shop_medium,shop_large,shop_extraLarge;
    public static ConstraintLayout viewAll_select_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        btn_done = (Button) findViewById(R.id.btn_done);
        cross_w = (ImageView) findViewById(R.id.cross_w);
        viewAll_select_size = (ConstraintLayout) findViewById(R.id.select_size);

        shop_small = (TextView) findViewById(R.id.shop_small);
        shop_medium=  (TextView) findViewById(R.id.shop_medium);
        shop_large = (TextView) findViewById(R.id.shop_large);
        shop_extraLarge = (TextView) findViewById(R.id.shop_extraLarge);


        cart = (ImageView) findViewById(R.id.Cart);

        C_tops = (Chip) findViewById(R.id.C_tops);

        fav_img = (ImageView) findViewById(R.id.fav_img);

        search = (EditText) findViewById(R.id.search);

        categoriesName = (TextView) findViewById(R.id.CategoriesName);

        search_btn = (ImageView) findViewById(R.id.search_btn);

        C_bottom = (Chip) findViewById(R.id.C_Bottoms);

        viewAllRecycler=(RecyclerView) findViewById(R.id.viewAllRecycler);

        categoriesId = getIntent().getStringExtra("categoriesID");


        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("categories").child(categoriesId).child("items");

        StaggeredGridLayoutManager layoutManager= new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(  StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        viewAllRecycler.setLayoutManager(layoutManager);

        options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(mDatabase,Model.class)
                .build();


        data = new ArrayList<>();
        alldata = new ArrayList<>();
        fav_data = new ArrayList<>();
        wait_List = new ArrayList<>();
        fav_List = new ArrayList<>();
        viewall_adapter = new ViewAll_Adapter(data, getApplicationContext());
        viewAllRecycler.setAdapter(viewall_adapter);

        search.addTextChangedListener(new TextWatcher() {
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

        CartSet = String.valueOf(System.currentTimeMillis());
//        btn_done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewAll_select_size.setVisibility(View.GONE);
//
//                for (int i = 0; i < data.size(); i++) {
//
//                    if (data.get(i).isSelected()){
//
//                        Log.e("itemID", "" + data.get(i).getItemID());
//                        Log.e("itemI455D", "" + viewModel.getAddress());
//
//
//                        ViewModel model = data.get(i);
//                        model.setImage(model.getListimages().get(0));
//                        model.setTotalitem(1);
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
//
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
//                                                if (model.getSmall() != 0) {
//                                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                                    ref.child(categoryId).child("items")
//                                                            .child(itemID)
//                                                            .child("small")
//                                                            .setValue(model.getSmall() - 1);
//                                                } else {
//                                                    Toast.makeText(ViewAll.this, "No Items Left in this Size", Toast.LENGTH_SHORT).show();
//                                                }
//                                            } else if (size.equals("medium")) {
//                                                if (model.getMedium() != 0) {
//                                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                                    ref.child(categoryId).child("items")
//                                                            .child(itemID)
//                                                            .child("medium")
//                                                            .setValue(model.getMedium() - 1);
//                                                } else{
//                                                    Toast.makeText(ViewAll.this, "No Items Left in this Size", Toast.LENGTH_SHORT).show();
//                                                }
//                                            } else if (size.equals("large")) {
//                                                if (model.getLarge() != 0) {
//                                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                                    ref.child(categoryId).child("items")
//                                                            .child(itemID)
//                                                            .child("large")
//                                                            .setValue(model.getLarge() - 1);
//                                                } else {
//                                                    Toast.makeText(ViewAll.this, "No Items Left in this Size", Toast.LENGTH_SHORT).show();
//                                                }
//                                            } else if (size.equals("extraLarge")) {
//                                                if (model.getExtraLarge() != 0) {
//                                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                                    ref.child(categoryId).child("items")
//                                                            .child(itemID)
//                                                            .child("extraLarge")
//                                                            .setValue(model.getExtraLarge() - 1);
//                                                } else {
//                                                    Toast.makeText(ViewAll.this, "No Items Left in this Size", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        } else {
//                                            if (size.equals("small")) {
//                                                Log.e("ifSmall",""+model.getSmall());
//                                                if (model.getSmall() != 0) {
//
//                                                    FirebaseDatabase.getInstance().getReference("categories")
//                                                            .child(model.getCategoryID()).child("items").child(model.getItemID())
//                                                            .child("small")
//                                                            .setValue(model.getSmall() - 1);
//                                                } else {
//                                                    Toast.makeText(ViewAll.this, "No Items Left in this Size", Toast.LENGTH_SHORT).show();
//                                                }
//
//                                            } else if (size.equals("medium")){
//
//                                                if (model.getMedium() != 0) {
//
//                                                    FirebaseDatabase.getInstance().getReference("categories")
//                                                            .child(model.getCategoryID()).child("items").child(model.getItemID())
//                                                            .child("medium")
//                                                            .setValue(model.getMedium() - 1);
//                                                } else {
//                                                    Toast.makeText(ViewAll.this, "No Items Left in this Size", Toast.LENGTH_SHORT).show();
//                                                }
//
//                                            }else if (size.equals("large")){
//                                                if (model.getLarge() != 0) {
//
//                                                    FirebaseDatabase.getInstance().getReference("categories")
//                                                            .child(model.getCategoryID()).child("items").child(model.getItemID())
//                                                            .child("large")
//                                                            .setValue(model.getLarge() - 1);
//                                                } else {
//                                                    Toast.makeText(ViewAll.this, "No Items Left in this Size", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                            else if (size.equals("extraLarge")){
//                                                if (model.getExtraLarge() != 0) {
//
//                                                    FirebaseDatabase.getInstance().getReference("categories")
//                                                            .child(model.getCategoryID()).child("items").child(model.getItemID())
//                                                            .child("extraLarge")
//                                                            .setValue(model.getExtraLarge() - 1);
//                                                } else {
//                                                    Toast.makeText(ViewAll.this, "No Items Left in this Size", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//
//                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
//                                                    .child(CartSet)
//                                                    .setValue(model);
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

        getwaitlist();

    }

    private void edit_search(String editable) {

        data.clear();
        for (Model model: alldata){
            if (model.getTitle().toLowerCase().contains(editable.toLowerCase())){
                data.add(model);
            }
        }
        viewall_adapter.notifyDataSetChanged();
    }

    private void getwaitlist() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid())
                .child("waitlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                wait_List.clear();

                for (DataSnapshot child: snapshot.getChildren()){

                    Log.e("wait_LIST123",""+ child.getKey());

                    wait_List.add(child.getKey());

                }
                getfavorites();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getfavorites() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid())
                .child("favorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fav_List.clear();

                for (DataSnapshot child: snapshot.getChildren()){

                    Log.e("fav_LIST123",""+ child.getKey());

                    fav_List.add(child.getKey());

                }
                getData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getData() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                data.clear();
                alldata.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){

                    Log.e("name000",""+snapshot1);
                    viewModel = snapshot1.getValue(Model.class);
                    viewModel.setCategoryID(categoriesId);
                    viewModel.setItemID(snapshot1.getKey());

                    Log.e("fav789","" + snapshot1);

                    if (fav_List.contains(viewModel.getItemID())){
                        Log.e("favoriteHEreIF","" + fav_List);
                        viewModel.setFav(true);
                    }else{
                        Log.e("favoriteHEreElse","" + fav_List);
                        viewModel.setFav(false);
                    }
                    if (wait_List.contains(viewModel.getItemID())){
                        Log.e("favoriteHEreIF","" + fav_List);
                        viewModel.setWait(true);
                    }else{
                        Log.e("favoriteHEreElse","" + fav_List);
                        viewModel.setWait(false);
                    }


                    Log.e("favorite1255","" + fav_List);

                    ArrayList<String> Listimages =new ArrayList<>();
                    for (DataSnapshot snapshot2 : snapshot1.child("images").getChildren()) {
                        Log.e("images123","" + snapshot2.getValue().toString());
                        Listimages.add(snapshot2.getValue().toString());
                    }
                    viewModel.setListimages(Listimages);
                    Log.e("images0077","" + Listimages);
                    data.add(viewModel);
                    alldata.add(viewModel);
                }
                viewall_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    }


