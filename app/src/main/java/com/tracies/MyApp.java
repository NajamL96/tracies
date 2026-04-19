package com.tracies;

import android.app.Application;

import com.stripe.android.PaymentConfiguration;

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_4urkldaLyTElkq7cScl8695R"
        );
    }
}
