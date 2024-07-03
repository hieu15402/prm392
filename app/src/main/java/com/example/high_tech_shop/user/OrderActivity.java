package com.example.high_tech_shop.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.adapter.OrderAdapter;
import com.example.high_tech_shop.entity.Order;
import com.example.high_tech_shop.entity.OrderItem;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.repositories.OrderItemRepository;
import com.example.high_tech_shop.repositories.OrderRepository;

import java.util.List;

public class OrderActivity extends AppCompatActivity {
    OrderAdapter adapter;
    List<OrderItem> list;
    ListView listView;
    OrderItemRepository orderItemRepository;
    OrderRepository orderRepository;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        final User user = (User) intent.getSerializableExtra("user");

        listView = findViewById(R.id.lvOrder);
        backBtn = findViewById(R.id.backBtn);

        orderRepository = new OrderRepository(this);
        orderItemRepository = new OrderItemRepository(this);

        assert user != null;
        if(user == null) finish();
        List<Integer> orderIds = orderRepository.getOrdersByUserId(user.getId());

        list = orderItemRepository.getOrderItemsByOrderIds(orderIds);

        adapter = new OrderAdapter(this,list);
        listView.setAdapter(adapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}