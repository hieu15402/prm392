package com.example.high_tech_shop.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.adapter.AddressAdapter;
import com.example.high_tech_shop.adapter.AddressDefaultAdapter;
import com.example.high_tech_shop.adapter.SelectAddressAdapter;
import com.example.high_tech_shop.adapter.SelectAddressDefaultAdapter;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.entity.UserAddress;
import com.example.high_tech_shop.repositories.UserAddressRepository;

import java.util.List;

public class SelectAddressActivity extends AppCompatActivity {
    private User user;
    private UserAddressRepository repository;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapterDefault;
    private RecyclerView rvAddressCart;
    private RecyclerView rvAddressDefaultCart;
    private ImageView ivBackCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_address);

        rvAddressCart = findViewById(R.id.rvAddressCart);
        rvAddressDefaultCart = findViewById(R.id.rvAddressDefaultCart);
        ivBackCart = findViewById(R.id.ivBackCart);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvAddressCart.getContext(), DividerItemDecoration.VERTICAL);
        DividerItemDecoration _dividerItemDecoration = new DividerItemDecoration(rvAddressDefaultCart.getContext(), DividerItemDecoration.VERTICAL);

        rvAddressCart.setLayoutManager(new LinearLayoutManager(this));
        rvAddressDefaultCart.setLayoutManager(new LinearLayoutManager(this));

        rvAddressCart.addItemDecoration(dividerItemDecoration);
        rvAddressDefaultCart.addItemDecoration(_dividerItemDecoration);

        user = (User) getIntent().getSerializableExtra("user");
        if (user == null) finish();

        repository = new UserAddressRepository(this);

        List<UserAddress> userAddressList = repository.getUserAddressByUserId(user.getId());
        UserAddress addressDefault = repository.getUserAddressByUserIdDefault(user.getId());
        if (addressDefault != null && !userAddressList.isEmpty()) {
            adapter = new SelectAddressAdapter(SelectAddressActivity.this, userAddressList);
            adapterDefault = new SelectAddressDefaultAdapter(SelectAddressActivity.this, addressDefault);
            rvAddressCart.setAdapter(adapter);
            rvAddressDefaultCart.setAdapter(adapterDefault);

        }
        if (addressDefault != null && userAddressList.isEmpty()) {
            adapterDefault = new SelectAddressDefaultAdapter(SelectAddressActivity.this, addressDefault);
            rvAddressDefaultCart.setAdapter(adapterDefault);
        }

        ivBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectAddressActivity.this, CartActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
    }
}