package com.tracies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class order_complete extends AppCompatActivity {

    ImageView go_to_order, shopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);

        shopping = (ImageView) findViewById(R.id.shopping);
        go_to_order = (ImageView) findViewById(R.id.go_to_myorder);

        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(order_complete.this,Bottom_nav.class);
                startActivity(intent);
            }
        });

        go_to_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(order_complete.this,my_order.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(order_complete.this,Bottom_nav.class);
        startActivity(intent);
        finish();
    }
}

