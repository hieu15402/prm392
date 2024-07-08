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
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_main);

        // Get intent
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        // Initializing widgets
        frameLayouts = findViewById(R.id.frameLayouts);
        bottomNavigationView = findViewById(R.id.bottomNavigations);

        // Init repo
        orderRepository = new OrderRepository(this);

        fetchOrders();

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

    public void fetchOrders() {
        List<String> items = new ArrayList<>();
        items.add("Delivered");
        items.add("Processing");
        orders = orderRepository.getOrdersByStatuses(items);
    }

    public void refreshOrders() {
        fetchOrders();
        ShipperHomeFragment fragment = (ShipperHomeFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayouts);
        if (fragment != null) {
            fragment.updateOrderList(orders);
        }
    }
}
