package com.tracies;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import com.tracies.Adapter.CategoryAdapter;
import com.tracies.Adapter.MyAdapter;
import com.tracies.Adapter.VediosAdapter;
import com.tracies.model.CategoryModel;
import com.tracies.model.Model;
import com.tracies.model.VediosModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopFragment extends Fragment {


    RecyclerView rcv,VedioRecyclerView,CategoriesRecyclerView;
    MyAdapter adapter;
    VediosAdapter videoAdapter;
    CategoryAdapter categoryAdapter;
    ImageView cart,search_btn,fav_img,cross_w;
    ArrayList<Model> data;
    ArrayList<Model> alldata;
    ArrayList<Model> fav_data;
    ArrayList<VediosModel> videos;
    ArrayList<CategoryModel> Category_data;
    TextView welcome, viewAll;
    public  static TextView categoriesName;
    EditText search;
    Model model;
    public static int cato;
    public static Context context;
    public static ShopFragment shopFragment;
    users User;
    VediosModel videoModel;
    String CartSet;
    String CartID = "";
    String itemID = "";
    String match = "";
    public static String categoryId = "1234567";
    int totalItem;
    private static final String Model = "headline";
    public static int cat = 123456;
    TextView C_tops,C_bottom,graphic_top,accessories,shoe,swim,sale;
    DatabaseReference mDatabase,Wdatabase,Vdatabase;
    public static int card = 1;
    public static ConstraintLayout select_size;

    Button btn_done;
    TextView shop_small,shop_medium,shop_large,shop_extraLarge;
    public static TextView Left_small,Left_mediuim,Left_Large,Left_extraLarge;
    public static String size = "small";
    ArrayList<String> fav_List;
    ArrayList<String> wait_List;
    FirebaseAuth firebaseAuth;
    int Remove = 0;
    private String CategoriesName1;

    @RequiresApi(api = Build.VERSION_CODES.M)
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_shop, container, false);



            shopFragment = this;
        cart = (ImageView) rootView.findViewById(R.id.Cart);

        C_tops = (TextView) rootView.findViewById(R.id.C_tops);

        viewAll = (TextView) rootView.findViewById(R.id.viewAll);

        fav_img = (ImageView) rootView.findViewById(R.id.fav_img);

        welcome = (TextView) rootView.findViewById(R.id.welcome);

        cross_w = (ImageView) rootView.findViewById(R.id.cross_w);

        search = (EditText) rootView.findViewById(R.id.search);

        categoriesName = (TextView) rootView.findViewById(R.id.CategoriesName);

        search_btn = (ImageView) rootView.findViewById(R.id.search_btn);

        btn_done = (Button) rootView.findViewById(R.id.btn_done);

        select_size = (ConstraintLayout) rootView.findViewById(R.id.select_size);


            Vdatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("videos");

            CategoriesRecyclerView = (RecyclerView) rootView.findViewById(R.id.CategoriesRecyclerView);
            CategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        VedioRecyclerView = (RecyclerView) rootView.findViewById(R.id.VedioRecyclerView);
        VedioRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        context = getActivity();

        rcv=(RecyclerView) rootView.findViewById(R.id.postRecyclerView);
        shop_small = (TextView) rootView.findViewById(R.id.shop_small);
        shop_medium=  (TextView) rootView.findViewById(R.id.shop_medium);
        shop_large = (TextView) rootView.findViewById(R.id.shop_large);
        shop_extraLarge = (TextView) rootView.findViewById(R.id.shop_extraLarge);



        StaggeredGridLayoutManager layoutManager= new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(  StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rcv.setLayoutManager(layoutManager);



                Category_data = new ArrayList<CategoryModel>();
                data = new ArrayList<Model>();
                alldata = new ArrayList<>();
                fav_data = new ArrayList<>();
                wait_List = new ArrayList<>();

                videos = new ArrayList<VediosModel>();

                fav_List = new ArrayList<>();
                adapter = new MyAdapter(data, getContext());
                rcv.setAdapter(adapter);

            videoAdapter = new VediosAdapter(videos, (FragmentActivity) getContext());
            VedioRecyclerView.setAdapter(videoAdapter);

            categoryAdapter = new CategoryAdapter(Category_data, (FragmentActivity) getContext());
            CategoriesRecyclerView.setAdapter(categoryAdapter);






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



        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Cart.class);
                startActivity(intent);

            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    card = 1;
                    Intent intent = new Intent(getActivity(), ViewAll.class);


                    intent.putExtra("categoriesID", model.getCategoryID());


                    startActivity(intent);

                }
            });


            Wdatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference();

            Wdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()){

                        Model model = snapshot.getValue(Model.class);

                        Log.e("lkjhgf",""+snapshot);

                        String wel = model.getHeadline();

                        welcome.setText(wel);

                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {


                }
            });

            CartSet = String.valueOf(System.currentTimeMillis());

            FirebaseDatabase.getInstance().getReference("categories")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            Category_data.clear();
                            for (DataSnapshot snapshot1: snapshot.getChildren()) {

                                CategoryModel category = snapshot1.getValue(CategoryModel.class);

                                category.setCategoryID(snapshot1.getKey());

                                Log.e("CategoryCOMING",""+category.getCategoryID());

                                Category_data.add(category);
                            }
                            categoryAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


            getVedios();
            getwaitlist();

            return rootView;
    }
    private void getVedios(){
            Vdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    videos.clear();
                    for (DataSnapshot snapshot1: snapshot.getChildren()) {
                        videoModel = snapshot1.getValue(VediosModel.class);

                        assert videoModel != null;
                        videoModel.setVideoID(snapshot1.getKey());
                        Log.e("videoModelData",""+ videoModel.getVideoID());
                        videos.add(videoModel);

                    }
                    videoAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }



    private void edit_search(String editable) {

            data.clear();
            for (Model model: alldata){
                if (model.getTitle().toLowerCase().contains(editable.toLowerCase())){
                    data.add(model);
                }
            }
            adapter.notifyDataSetChanged();
    }

    public void updateCategories() {
        getwaitlist();

    }

    public void getwaitlist() {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid())
                .child("waitlist").addListenerForSingleValueEvent(new ValueEventListener() {
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
                .child("favorites").addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void getData() {
        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("categories").child(categoryId).child("items");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("name000",""+snapshot);
                data.clear();
                alldata.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()){

                    model = snapshot1.getValue(Model.class);
                    model.setCategoryID(categoryId);
                    model.setItemID(snapshot1.getKey());

                    Log.e("favorite125456","" + snapshot1);

                    if (fav_List.contains(model.getItemID())){
                        Log.e("favoriteHEreIF","" + fav_List);
                        model.setFav(true);
                    }else{
                        Log.e("favoriteHEreElse","" + fav_List);
                        model.setFav(false);
                    }
                    if (wait_List.contains(model.getItemID())){
                        Log.e("favoriteHEreIF","" + fav_List);
                        model.setWait(true);
                    }else{
                        Log.e("favoriteHEreElse","" + fav_List);
                        model.setWait(false);
                    }
                    Log.e("favorite1255","" + fav_List);
                    ArrayList<String> Listimages =new ArrayList<>();
                    for (DataSnapshot snapshot2 : snapshot1.child("images").getChildren()) {
                        Log.e("images123","" + snapshot2.getValue().toString());
                        Listimages.add(snapshot2.getValue().toString());
                    }
                    model.setListimages(Listimages);
                    Log.e("images003211111","" + Listimages);
                    data.add(model);
                    alldata.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}

