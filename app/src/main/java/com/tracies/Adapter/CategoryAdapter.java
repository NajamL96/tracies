package com.tracies.Adapter;

import static com.tracies.ShopFragment.categoriesName;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tracies.R;
import com.tracies.ShopFragment;
import com.tracies.model.CategoryModel;
import com.tracies.model.Model;
import com.tracies.model.card_Model;

import java.util.ArrayList;
import java.util.HashMap;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    ArrayList<CategoryModel> Category_data;
    Context contextAdapter;
    CategoryModel setsel;
    int row_index = 1;

    public CategoryAdapter(ArrayList<CategoryModel> category_data,Context activity) {
        Category_data = category_data;
        contextAdapter = activity;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_item,parent,false);
        return new CategoryViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {

        CategoryModel categoryModel = Category_data.get(position);

        holder.CategoryItem.setText(categoryModel.getName());

        //categoriesName.setText(Category_data.get(0).getName());


        Log.e("CAtNAME",""+categoryModel.getName());

        holder.CategoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShopFragment.categoryId = categoryModel.getCategoryID();
                ShopFragment.shopFragment.updateCategories();
                categoriesName.setText(categoryModel.getName());
                row_index = position;
                notifyDataSetChanged();
                ShopFragment.card = 1;

            }
        });


        if(row_index==position){
            holder.CategoryItem.setTextColor(Color.parseColor("#FFB6C1"));
        }
        else
        {
            holder.CategoryItem.setTextColor(Color.parseColor("#808080"));
        }
    }


    @Override
    public int getItemCount() {
        return (Category_data == null) ? 0 : Category_data.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView CategoryItem;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            CategoryItem = (TextView) itemView.findViewById(R.id.CategoryItem);


        }
    }
}


