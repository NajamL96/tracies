package com.tracies.model;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tracies.R;
import com.stripe.android.Stripe;

public class CheckoutActivityJava extends AppCompatActivity {


    private Stripe stripe;
    private TextView amountTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_java);







//        com.stripe.Stripe.apiKey = BuildConfig.STRIPE_SECRET_KEY;
//        Button payButton = findViewById(R.id.payButton);
//        WeakReference<CheckoutActivityJava> weakActivity = new WeakReference<>(this);
//        payButton.setOnClickListener((View view) -> {
//            // Get the card details from the card widget
//            CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
//            CardParams card = cardInputWidget.getCardParams();
//            if (card != null) {
//                // Create a Stripe token from the card details
//                stripe = new Stripe(getApplicationContext(), PaymentConfiguration.getInstance(getApplicationContext()).getPublishableKey());
//                stripe.createCardToken(card, new ApiResultCallback<Token>() {
//                    @Override
//                    public void onSuccess(@NonNull Token result) {
//                        String tokenID = result.getId();
//                        // Send the token identifier to the server...
//                        // Token is created using Stripe Checkout or Elements!
//                        // Get the payment token ID submitted by the form:
//
//                        ChargeCreateParams params =
//                                ChargeCreateParams.builder()
//                                        .setAmount(999L)
//                                        .setCurrency("usd")
//                                        .setDescription("Example charge")
//                                        .setSource(tokenID)
//                                        .build();
//
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Charge charge = null;
//                                try {
//                                    charge = Charge.create(params);
//                                    Log.e("Stripe:",""+ charge);
//                                } catch (StripeException e) {
//                                    Log.e("Stripe:",""+ e.getLocalizedMessage());
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }).start();
//                    }
//
//                    @Override
//                    public void onError(@NonNull Exception e) {
//                        // Handle error
//                    }
//                });
//
//            }
//        });


    }

}
