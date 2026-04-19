package com.tracies;

import static com.tracies.YourCards.cardModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragment_account extends Fragment {
    ImageView A_cart, Edit_Profile,my_cards, my_order,user_dp,topay,to_ship,to_receive;
    TextView user_name;
    DatabaseReference mdatabase;
    private static final String USER = "users";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        Edit_Profile = (ImageView) rootView.findViewById(R.id.Edit_profile);
        my_cards = (ImageView)rootView.findViewById(R.id.my_card);
        my_order = (ImageView)rootView.findViewById(R.id.my_orders);
        user_dp = (ImageView) rootView.findViewById(R.id.userDP);
        topay = (ImageView) rootView.findViewById(R.id.topay);
        to_ship = (ImageView) rootView.findViewById(R.id.to_ship);
        to_receive = (ImageView) rootView.findViewById(R.id.to_receive);

        user_name = (TextView) rootView.findViewById(R.id.user_name);

        A_cart = (ImageView) rootView.findViewById(R.id.A_Cart);


        mdatabase = FirebaseDatabase.getInstance().getReference(USER).child(FirebaseAuth.getInstance().getUid());

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){

                    users user = snapshot.getValue(users.class);

                    Log.e("name1",""+snapshot);

                    String name = user.name;

                    Log.e("image55",""+snapshot);

                    user_name.setText(name);

                    Log.e("khan15698",""+user.getImageUrl());
                    if (user.getImageUrl() != null) {

                        Glide.with(fragment_account.this).load(user.getImageUrl()).into(user_dp);
                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });




        my_cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), YourCards.class);
                startActivity(intent);

            }
        });
        topay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ToPay.class);
                startActivity(intent);

            }
        });
        to_ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ToShip.class);
                startActivity(intent);

            }
        });
        to_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ToReceive.class);
                startActivity(intent);

            }
        });

        Edit_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Profile.class);
                startActivity(intent);

            }
        });

        my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), my_order.class);
                startActivity(intent);

            }
        });


        A_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Cart.class);
                startActivity(intent);

            }
        });


return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (cardModel != null) {
            Log.e("cardback", "cardcheck " + cardModel.getCardNumber());
        }
    }
}