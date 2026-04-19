package com.tracies;



import static java.lang.System.currentTimeMillis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tracies.Adapter.cart_Adapter;
import com.tracies.model.Cart_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Cart extends AppCompatActivity {

    RecyclerView rcv_my_cart;
    cart_Adapter c_adapter;
    ImageView checkout,close, empty_image;
    Button btn_done;
    RadioGroup radioGroup;
    RadioButton radioCash;
    RadioButton radioCard;
    long time;
    users user;
    String Cartid;
    Long CreatedTime;

    String current;
    ArrayList<Cart_Model> data, cart, wholeSaleCart;
    DatabaseReference mDatabase, vDatabase,Odatabase,wholeSaleDatabase;
    Cart_Model cart_model;
   // String categoriesId = String.valueOf(123456);
    ArrayList<Cart_Model> orderitem;
    String paymentMethod = "cash";
    private int totalPrice = 0;
    String total;
    int Quantiy = 0;
    String size = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        checkout = (ImageView) findViewById(R.id.checkout);
        rcv_my_cart = (RecyclerView) findViewById(R.id.rcv_my_cart);
        rcv_my_cart.setLayoutManager(new LinearLayoutManager(this));
        btn_done = (Button) findViewById(R.id.btn_done);
        radioGroup = (RadioGroup) findViewById(R.id.rdGroup);
        radioCash = (RadioButton) findViewById(R.id.radioCash);
        radioCard = (RadioButton) findViewById(R.id.radioCard);
        close = (ImageView) findViewById(R.id.close);

        empty_image = (ImageView) findViewById(R.id.empty_image);


        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("cart");

        vDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("wholesaleCart");

        Odatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));

        wholeSaleDatabase = (DatabaseReference)   FirebaseDatabase.getInstance().getReference("users");

        data = new ArrayList<Cart_Model>();
        cart = new ArrayList<Cart_Model>();
        wholeSaleCart = new ArrayList<Cart_Model>();

        c_adapter = new cart_Adapter(data, getApplicationContext());
        rcv_my_cart.setAdapter(c_adapter);
        getData();

        Log.e("emptyimg", "" + c_adapter.getItemCount());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.dialogs).setVisibility(View.GONE);

            }
        });


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (data.size() == 0){

                }else {
                    if (cart_model.isSelected()) {
                        findViewById(R.id.dialogs).setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(Cart.this, "select item to checkout", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == radioCash.getId()) {
                    paymentMethod = "cash";
                } else {
                    paymentMethod = "card";
                }
            }
        });
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (paymentMethod == "card") {
                    Intent intent = new Intent(Cart.this, YourCards.class);
                    intent.putExtra("cardDetail", data);
                    startActivity(intent);
                    Log.e("cvbnm",""+ data.size());

                } else {
                    total = String.valueOf(currentTimeMillis()/1000);

                    for (int i = 0; i < data.size(); i++) {

                        if (data.get(i).isSelected()) {
                            totalPrice = totalPrice  + data.get(i).getPrice() * data.get(i).getTotalitem();
                        }

                    }

                    HashMap<String, Object> fields = new HashMap<>();
                    fields.put("totalPrice", totalPrice);
                    fields.put("status", "pending");
                    fields.put("paymentMethod", "on delivery");



                    Log.e("totalprice:", "" + totalPrice);


                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders");
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(total)
                            .setValue(fields).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                for (int i = 0; i < data.size(); i++) {
                                    Log.e("cartListItem:", "" + data.get(i).getItemID());
                                    Log.e("cartListItem:", "" + data.get(i).isSelected());
                                    Log.e("cartListprice:", "" + data.get(i).getPrice());

                                    if (data.get(i).isSelected()) {

                                        Cart_Model model = data.get(i);

                                        if (model.getSize() != null) {


                                            HashMap<String, Object> orderFields = new HashMap<>();
                                            orderFields.put("categoryID", model.getCategoryID());
                                            orderFields.put("image", model.getImage());
                                            orderFields.put("itemID", model.getItemID());
                                            orderFields.put("price", model.getPrice());
                                            orderFields.put("totalitem", model.getTotalitem());
                                            orderFields.put("size", model.getSize());
                                            orderFields.put("title", model.getTitle());

                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders");
                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child(total)
                                                    .child("items").child(String.valueOf(currentTimeMillis()))
                                                    .setValue(orderFields);
                                        } else {
                                            HashMap<String, Object> WorderFields = new HashMap<>();
                                            WorderFields.put("categoryID", model.getCategoryID());
                                            WorderFields.put("image", model.getImage());
                                            WorderFields.put("itemID", model.getItemID());
                                            WorderFields.put("price", model.getPrice());
                                            WorderFields.put("totalitem", model.getTotalitem());
                                            WorderFields.put("small", model.getSmall());
                                            WorderFields.put("medium", model.getSmall());
                                            WorderFields.put("large", model.getSmall());
                                            WorderFields.put("extraLarge", model.getSmall());
                                            WorderFields.put("title", model.getTitle());

                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders");
                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child(total)
                                                    .child("items").child(String.valueOf(currentTimeMillis()))
                                                    .setValue(WorderFields);

                                        }

                                        Intent intent = new Intent(Cart.this, order_complete.class);
                                        startActivity(intent);

                                    } else {

                                    }
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
                                            .child(data.get(i).getCartID())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });
                                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                            .child(data.get(i).getCartID())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });
                                    c_adapter.notifyDataSetChanged();
                                }

                            }

                        }
                    });
                }
            }
        });


    }

    private void getData() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                cart.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Log.e("name456", "" + snapshot1);
                    cart_model = snapshot1.getValue(Cart_Model.class);
                    assert cart_model != null;
                    cart_model.setCartID(snapshot1.getKey());



                    Log.e("name777", "" + cart_model.getCartID());
                    cart.add(cart_model);

                }
                wholeSalegetData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void wholeSalegetData() {

        vDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                wholeSaleCart.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    Log.e("name0111", "" + snapshot1);
                    cart_model = snapshot1.getValue(Cart_Model.class);
                    cart_model.setCartID(snapshot1.getKey());

                    time=Long.parseLong(cart_model.getCartID());
                    Log.e("timeAcheck",""+ time);

                    long timeAfter30Minutes = time + 60000;
                    Log.e("timeAfter",""+ timeAfter30Minutes);
                    Log.e("timeCheck",""+ time);
                    if(System.currentTimeMillis() > timeAfter30Minutes) {
                        Log.e("min1111222","Delete");
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                            .child(cart_model.getCartID())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                    }
                    Log.e("CartID",""+ cart_model.getCartID());
                    wholeSaleCart.add(cart_model);


                }
                data.addAll(cart);
                data.addAll(wholeSaleCart);


                if (data.size() == 0) {
                    rcv_my_cart.setVisibility(View.GONE);
                    empty_image.setVisibility(View.VISIBLE);
                } else {
                    rcv_my_cart.setVisibility(View.VISIBLE);
                    empty_image.setVisibility(View.GONE);
                }
                c_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Cart.this,Bottom_nav.class);
        startActivity(intent);
    }
}
