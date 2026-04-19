package com.tracies.Adapter;

import static com.tracies.YourCards.card_model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tracies.R;
import com.tracies.model.card_Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class card_Adapter extends RecyclerView.Adapter<cardViewHolder> {

    ArrayList<card_Model> card_data ;
    Context context;
    int lastItemSelected = 0;



    public card_Adapter(ArrayList<card_Model> card_data, Context context) {
        this.card_data = card_data;
        this.context = context;
    }
    @NonNull
    @Override
    public cardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_item,parent,false);
        return new cardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cardViewHolder holder, @SuppressLint("RecyclerView") int position) {

        card_Model card_modelabc = card_data.get(position);

        Log.e("cardNAme",""+ card_modelabc.getCardNumber());

        holder.card_holder.setText(card_modelabc.getCardHolderName());
        holder.card_number.setText(card_modelabc.getCardNumber());
        holder.ccv.setText(card_modelabc.getCvv());
        holder.month.setText(card_modelabc.getExpiryMonth());
        holder.year.setText(card_modelabc.getExpiryYear());

        Log.e("siz", String.valueOf(card_data.size()));

        card_Model model = card_data.get(lastItemSelected);
        lastItemSelected = position;
        holder.cardimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("OnClick","clicked");
                for (int i=0;i<card_data.size();i++) {
                    Log.e("list", "" + card_data.get(i).isSelected());
                }
                if (card_data.get(position).isSelected() == false){

                    Log.e("cardif","clicked");
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cards")
                            .child(card_modelabc.getCardNumber()).child("selected")
                            .setValue(true);
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cards")
                                .child(model.getCardNumber()).child("selected")
                                .setValue(false);
                    card_model = card_modelabc;

                } else {

                    Log.e("cardelse","clicked");
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cards")
                            .child(card_modelabc.getCardNumber()).child("selected")
                            .setValue(false);
                }
            }
        });
        if (card_modelabc.isSelected() == true){
            Log.e("logisslected","Selected");
            holder.greenTick.setVisibility(View.VISIBLE);
            holder.greyTick.setVisibility(View.GONE);
        } else {
            Log.e("notselected","Notselected");
            holder.greenTick.setVisibility(View.GONE);
            holder.greyTick.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return card_data.size();
    }
}
