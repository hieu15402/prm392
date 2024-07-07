package com.example.high_tech_shop.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.adapter.AddressAdapter;
import com.example.high_tech_shop.adapter.AddressDefaultAdapter;
import com.example.high_tech_shop.adapter.CartAdapter;
import com.example.high_tech_shop.adapter.CombinedAdapter;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.entity.UserAddress;
import com.example.high_tech_shop.repositories.UserAddressRepository;

import java.util.List;

public class AddressActivity extends AppCompatActivity {
    private User user;
    private UserAddressRepository repository;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapterDefault;
    private RecyclerView rvAddress;
    private RecyclerView rvAddressDefault;
    private ImageView ivAddAddress;
    private ImageView ivBackProfile;
    private TextView tvAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_address);

        rvAddress = findViewById(R.id.rvAddress);
        rvAddressDefault = findViewById(R.id.rvAddressDefault);
        ivAddAddress = findViewById(R.id.ivAddAddress);
        tvAddAddress = findViewById(R.id.tvAddAddress);
        ivBackProfile = findViewById(R.id.ivBackProfile);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvAddress.getContext(), DividerItemDecoration.VERTICAL);
        DividerItemDecoration _dividerItemDecoration = new DividerItemDecoration(rvAddressDefault.getContext(), DividerItemDecoration.VERTICAL);

        rvAddress.setLayoutManager(new LinearLayoutManager(this));
        rvAddressDefault.setLayoutManager(new LinearLayoutManager(this));

        rvAddress.addItemDecoration(dividerItemDecoration);
        rvAddressDefault.addItemDecoration(_dividerItemDecoration);

        user = (User) getIntent().getSerializableExtra("user");
        if (user == null) finish();

        repository = new UserAddressRepository(this);
        List<UserAddress> userAddressList = repository.getUserAddressByUserId(user.getId());
        UserAddress addressDefault = repository.getUserAddressByUserIdDefault(user.getId());
        if (addressDefault != null && !userAddressList.isEmpty()) {
            adapter = new AddressAdapter(AddressActivity.this, userAddressList);
            adapterDefault = new AddressDefaultAdapter(AddressActivity.this, addressDefault);
            rvAddress.setAdapter(adapter);
            rvAddressDefault.setAdapter(adapterDefault);
        }
        if (addressDefault != null && userAddressList.isEmpty()) {
            adapterDefault = new AddressDefaultAdapter(AddressActivity.this, addressDefault);
            rvAddressDefault.setAdapter(adapterDefault);
        }

        ivAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
        tvAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
        ivBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, ProfileActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
    }
}