package com.tracies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tracies.Adapter.card_Adapter;
import com.tracies.model.Cart_Model;
import com.tracies.model.card_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.StripeIntent;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.view.CardMultilineWidget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class YourCards extends AppCompatActivity {

    ImageView card,close;
    ImageView pay,plus;
    Button btn_done1;
    RecyclerView card_rcv;
    card_Adapter cardAdapter;
    ArrayList<card_Model> data,cardDetail;
    DatabaseReference mDatabase;
    public static card_Model card_model;
    EditText month,year,cardHolderName, cardNumber,cvm;
    CardMultilineWidget card_multiline_widget;
    Stripe stripe;
    String categoriesId = String.valueOf(123456);
    ArrayList<Cart_Model> C_data = new ArrayList<>();
    public static card_Model cardModel;
    private int totalPrice = 0;
    String total;
    int Quantiy = 0;
    String size = "";
    boolean cardcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_cards);

        plus = (ImageView) findViewById(R.id.plus);
        pay = (ImageView) findViewById(R.id.pay);
        card = (ImageView) findViewById(R.id.A_Cart);
        cardHolderName = (EditText) findViewById(R.id.card_holder_name);
        cardNumber = (EditText) findViewById(R.id.cardNumber);
        cvm = (EditText) findViewById(R.id.cvm);
        card_multiline_widget = (CardMultilineWidget) findViewById(R.id.card_multiline_widget);
        close = (ImageView) findViewById(R.id.close);

        card_rcv =  findViewById(R.id.card_rcv);
        card_rcv.setLayoutManager(new LinearLayoutManager(this));

        btn_done1 = (Button) findViewById(R.id.btn_done1);
        month = (EditText) findViewById(R.id.MM);
        year = (EditText) findViewById(R.id.YY);
        cardDetail = new ArrayList<>();

        mDatabase = (DatabaseReference) FirebaseDatabase.getInstance().getReference("users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("cards");

        data = new ArrayList<card_Model>();



        cardAdapter = new card_Adapter(data,getApplicationContext());

        card_rcv.setAdapter(cardAdapter);


        getData();

        cardNumber.addTextChangedListener(new FourDigitCardFormatWatcher());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YourCards.this, Cart.class);
                startActivity(intent);

            }
        });


        C_data = (ArrayList<Cart_Model>) getIntent().getSerializableExtra("cardDetail");

      //  Log.e("cartdata",""+ C_data.size());
        if (C_data == null) {
            pay.setVisibility(View.GONE);
        } else {
            pay.setVisibility(View.VISIBLE);
        }


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int o = 0; o<data.size(); o++) {

                    Log.e("nowcheck", "" + data.get(o).isSelected());


                    Log.e("cardDataSize", "" + data.size());

                    if (data.get(o).isSelected()) {
                        ProgressDialog.show(YourCards.this, "Loading", "Wait while loading...");
                        Log.e("card", "" + cardModel);
                        total = String.valueOf(System.currentTimeMillis()/1000);
                        for (int i = 0; i < C_data.size(); i++) {
                            Log.e("sel_check", "" + C_data.get(i).isSelected());
                            if (C_data.get(i).isSelected()) {
                                totalPrice = totalPrice + C_data.get(i).getPrice() * C_data.get(i).getTotalitem();
                            }
                        }
                        Log.e("cardNumber", "" + totalPrice);

                        payViaCard(totalPrice, card_model.getCardNumber(), card_model.getExpiryMonth(), card_model.getExpiryYear(), card_model.getCvv());

                        HashMap<String, Object> fields = new HashMap<>();
                        fields.put("totalPrice", totalPrice);
                        fields.put("status", "Pending Shipment");
                        fields.put("paymentMethod","card");

                        Log.e("totalprice:", "" + totalPrice);

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders");
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(total)
                                .setValue(fields).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    for (int i = 0; i < C_data.size(); i++) {
                                        Log.e("cartListItem:", "" + C_data.get(i).getItemID());
                                        Log.e("cartListItem:", "" + C_data.get(i).isSelected());
                                        Log.e("cartListprice:", "" + C_data.get(i).getPrice());

                                        if (C_data.get(i).isSelected()) {
                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("orders");
                                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child(total)
                                                    .child("items").child(C_data.get(i).getItemID())
                                                    .setValue(C_data.get(i));

//                                            if (C_data.get(i).getSize().equals("small")) {
//                                                Quantiy = C_data.get(i).getSmall();
//                                                size = "small";
//                                            } else if (C_data.get(i).getSize().equals("medium")) {
//                                                size = "medium";
//                                                Quantiy = C_data.get(i).getMedium();
//                                            } else if (C_data.get(i).getSize().equals("large")) {
//                                                size = "large";
//                                                Quantiy = C_data.get(i).getLarge();
//                                            } else if (C_data.get(i).getSize().equals("extraLarge")) {
//                                                size = "extraLarge";
//                                                Quantiy = C_data.get(i).getExtraLarge();
//                                            }
//
////
//                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories");
//                                            ref.child(categoriesId).child("items")
//                                                    .child(C_data.get(i).getItemID()).child(size)
//                                                    .setValue(Quantiy - C_data.get(i).getTotalitem());

                                        }
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cart")
                                                .child(C_data.get(i).getCartID())
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                });
                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("wholesaleCart")
                                                .child(C_data.get(i).getCartID())
                                                .removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                    }
                                                });
                                        cardAdapter.notifyDataSetChanged();

                                    }

                                }

                            }
                        });
                    }
                }

                }


        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.dialogs).setVisibility(View.GONE);
            }
        });

        btn_done1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                card_multiline_widget.setCardNumber(cardNumber.getText().toString());
                card_multiline_widget.setCvcCode(cvm.getText().toString());
                card_multiline_widget.setExpiryDate(Integer.parseInt(month.getText().toString()), Integer.parseInt(year.getText().toString()));
                card_multiline_widget.setUsZipCodeRequired(false);
                card_multiline_widget.setPostalCodeRequired(false);

                if (card_multiline_widget.validateAllFields()){
                    Log.e("021","Correct");
                    card_model = new card_Model();
                    card_model.setCardNumber(cardNumber.getText().toString());
                    card_model.setCardHolderName(cardHolderName.getText().toString());
                    card_model.setCvv(cvm.getText().toString());
                    card_model.setExpiryMonth(month.getText().toString());
                    card_model.setExpiryYear(year.getText().toString());


                    HashMap<String, Object> cardFields = new HashMap<>();
                    cardFields.put("cardHolderName", card_model.getCardHolderName());
                    cardFields.put("cvv", card_model.getCvv());
                    cardFields.put("expiryMonth", card_model.getExpiryMonth());
                    cardFields.put("expiryYear", card_model.getExpiryYear());


                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cards")
                            .child(card_model.getCardNumber())
                            .setValue(cardFields);


                    findViewById(R.id.dialogs).setVisibility(View.GONE);

                }else {
                    Log.e("021","else");

                        Toast.makeText(YourCards.this, "Invalid card Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.dialogs).setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onBackPressed() {
       // cardModel = null;
//        Intent intent = new Intent(YourCards.this,Bottom_nav.class);
//        startActivity(intent);
//        finish();
        super.onBackPressed();
    }

    private void getData() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.e("nameCard",""+snapshot1);
                    card_model = snapshot1.getValue(card_Model.class);
                    card_model.setCardNumber(snapshot1.getKey());

                    if (card_model.isSelected() == true){
                        Log.e("selectedcard","card" );
                        cardcheck = true;
                    }
                    data.add(card_model);
                }
                if (cardcheck != true){
                    if (card_model != null) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cards")
                                .child(card_model.getCardNumber()).child("selected")
                                .setValue(true);

                        cardcheck = true;
                    }
                }
                cardAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void payViaCard(int price, String cardNumber, String expiryMonth, String expiryYear, String cvv) {
        try {
            com.stripe.Stripe.apiKey = BuildConfig.STRIPE_SECRET_KEY;
            CardInputWidget cardInputWidget = new CardInputWidget(YourCards.this);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cardInputWidget.setCardNumber(cardNumber);
                    cardInputWidget.setExpiryDate(Integer.parseInt(expiryMonth), Integer.parseInt(expiryYear));
                    cardInputWidget.setCvcCode(cvv);
                }
            });
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params != null) {
                List<Object> paymentMethodTypes = new ArrayList<>();
                paymentMethodTypes.add("card");
                Map<String, Object> paymentIntentParams = new HashMap<>();
                paymentIntentParams.put("amount", price * 100);
                paymentIntentParams.put("currency", "usd");
                paymentIntentParams.put("payment_method_types", paymentMethodTypes);
                try {
                    com.stripe.model.PaymentIntent paymentIntent = com.stripe.model.PaymentIntent.create(paymentIntentParams);
                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntent.getClientSecret());
                    final Context context = getApplicationContext();
                     stripe = new Stripe(
                            context,
                            PaymentConfiguration.getInstance(context).getPublishableKey()
                    );
                    stripe.confirmPayment(YourCards.this, confirmParams);


                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(YourCards.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(YourCards.this, "Params Null", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("stripePayment", "params null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(YourCards.this, "Payment Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private static final class PaymentResultCallback implements ApiResultCallback<PaymentIntentResult> {
        private final WeakReference<YourCards> activityRef;

        PaymentResultCallback(@NonNull YourCards activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final YourCards activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {

                Log.e("working",""+ StripeIntent.Status.Succeeded);
                Toast.makeText(activity, "Payment has been done successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, order_complete.class);
                activity.startActivity(intent);

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method

            } else if (status == PaymentIntent.Status.RequiresConfirmation) {
                // After handling a required action on the client, the status of the PaymentIntent is
                // requires_confirmation. You must send the PaymentIntent ID to your backend
                // and confirm it to finalize the payment. This step enables your integration to
                // synchronously fulfill the order on your backend and return the fulfillment result
                // to your client.

            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final YourCards activity = activityRef.get();
            if (activity == null) {
                return;
            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        stripe.onPaymentResult(requestCode,data, new PaymentResultCallback(YourCards.this));

    }




}