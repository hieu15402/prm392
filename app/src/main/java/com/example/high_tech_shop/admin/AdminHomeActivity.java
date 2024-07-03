package com.example.high_tech_shop.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.high_tech_shop.R;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech);

        findViewById(R.id.productCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, ProductManageActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.orderCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, OrderManageActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.paymentCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, PaymentManageActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.notificationCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, NotificationManageActivity.class);
                startActivity(intent);
            }
        });
    }
}
