package com.example.high_tech_shop.shipper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.Order;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.repositories.OrderRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class ShipperMainActivity extends AppCompatActivity {
    private FrameLayout frameLayouts;
    private BottomNavigationView bottomNavigationView;
    private OrderRepository orderRepository;
    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_main);

        // Get intent
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        // Initializing widgets
        frameLayouts = findViewById(R.id.frameLayouts);
        bottomNavigationView = findViewById(R.id.bottomNavigations);

        // Init repo
        orderRepository = new OrderRepository(this);


        orders = orderRepository.getOrdersByStatus("Processing");
        Log.d("TAG", orders.toString());
        // Creating 5 orders
//        orderList.add(new Order(1, 200.0, "Pending", "Minh", "123456789", "Hanoi", "Ba Dinh", "123 Street", "minh@example.com", "Please deliver fast", 1, 1));
//        orderList.add(new Order(2, 150.0, "Shipped", "Nam", "987654321", "Hanoi", "Hoan Kiem", "456 Street", "nam@example.com", "Leave at door", 2, 2));
//        orderList.add(new Order(3, 300.0, "Delivered", "Hoa", "555666777", "HCM City", "District 1", "789 Street", "hoa@example.com", "Call before delivery", 3, 3));
//        orderList.add(new Order(4, 250.0, "Canceled", "Lan", "222333444", "Da Nang", "Hai Chau", "101 Street", "lan@example.com", "Refund request", 4, 4));
//        orderList.add(new Order(5, 100.0, "Pending", "Huy", "111222333", "Hue", "Phu Nhuan", "202 Street", "huy@example.com", "Deliver in morning", 5, 5));
//
//        orders = orderList;

        // Set default fragment
        if (savedInstanceState == null) {
            ShipperHomeFragment homeFragment = ShipperHomeFragment.newInstance(user, orders);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayouts, homeFragment).commit();
        }

        // Bottom Navigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.bottom_nav_homes) {
                    selectedFragment = ShipperHomeFragment.newInstance(user, orders);
                } else if (item.getItemId() == R.id.bottom_nav_profiles) {
                    selectedFragment = ShipperProfileFragment.newInstance(user);
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayouts, selectedFragment).commit();
                    return true;
                }
                return false;
            }
        });
    }
}