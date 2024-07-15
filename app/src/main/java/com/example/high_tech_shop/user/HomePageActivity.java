package com.example.high_tech_shop.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.adapter.ProductAdapter;import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.repositories.CategoryRepository;
import com.example.high_tech_shop.repositories.ProductRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private User user;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ProductRepository productRepository;
    private final int REQUEST_CART_ACTIVITY = 1;
    private EditText etSearch;
    BottomNavigationView bottomNavigationView;
    private TextView category1Txt,category2Txt,category3Txt,category4Txt;
    LinearLayout lyCategory1,lyCategory2,lyCategory3,lyCategory4,lyCategoryAll;
    CategoryRepository categoryRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        if(user == null) finish();
        ( (TextView)findViewById(R.id.textView5)).setText("Welcome "+ user.getFullName());
        productRepository =new ProductRepository(this);
        initRecycleView(user);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    Intent intent = new Intent(HomePageActivity.this, FilterProductActivity.class);
                    intent.putExtra("search", etSearch.getText().toString());
                    intent.putExtra("user", user);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_explorer) {
                    Toast.makeText(HomePageActivity.this, "Explorer selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId() == R.id.menu_cart) {
                    Intent intent = new Intent(HomePageActivity.this, CartActivity.class);
                    intent.putExtra("user", user);
                    startActivityForResult(intent, REQUEST_CART_ACTIVITY);
                    return true;
                } else if (item.getItemId() == R.id.menu_profile) {
                    Intent intent = new Intent(HomePageActivity.this, ProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivityForResult(intent, REQUEST_CART_ACTIVITY);
                    return true;
                }
                return false;
            }
        });

    }

    private void initRecycleView(User user) {
        List<Product> products = productRepository.getAll();
        categoryRepository = new CategoryRepository(this);
        recyclerView = findViewById(R.id.rv_popular);
        etSearch = findViewById(R.id.etSearch2);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        category1Txt = findViewById(R.id.textView12);
        category2Txt = findViewById(R.id.textView13);
        category3Txt = findViewById(R.id.textView14);
        category4Txt = findViewById(R.id.textView15);

        category1Txt.setText(categoryRepository.findById(1).getName());
        category2Txt.setText(categoryRepository.findById(2).getName());
        category3Txt.setText(categoryRepository.findById(3).getName());
        category4Txt.setText(categoryRepository.findById(4).getName());

        lyCategory1 = findViewById(R.id.lyCategory1);
        lyCategory2 = findViewById(R.id.lyCategory2);
        lyCategory3 = findViewById(R.id.lyCategory3);
        lyCategory4 = findViewById(R.id.lyCategory4);
        lyCategoryAll = findViewById(R.id.lyCategory5);

        lyCategory1.setOnClickListener(categoryClickListener);
        lyCategory2.setOnClickListener(categoryClickListener);
        lyCategory3.setOnClickListener(categoryClickListener);
        lyCategory4.setOnClickListener(categoryClickListener);
        lyCategoryAll.setOnClickListener(categoryClickListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ProductAdapter(products,this,user);
        recyclerView.setAdapter(adapter);
    }

    private View.OnClickListener categoryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.lyCategory1) {
                handleCategoryClick(1);
            } else if (v.getId() == R.id.lyCategory2) {
                handleCategoryClick(2);
            } else if (v.getId() == R.id.lyCategory3) {
                handleCategoryClick(3);
            } else if (v.getId() == R.id.lyCategory4) {
                handleCategoryClick(4);
            } else if (v.getId() == R.id.lyCategory5) {
                handleCategoryClick(5);
            }
        }
    };
    private void handleCategoryClick(int categoryId) {
        Intent intent = new Intent(HomePageActivity.this, FilterProductActivity.class);
        intent.putExtra("categoryId", categoryId);
        intent.putExtra("user", user);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CART_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                boolean returnFromCart = data.getBooleanExtra("returnFromCart", false);
                boolean returnFromProfileUpdate = data.getBooleanExtra("returnFromProfileUpdate", false);
                if (returnFromCart) {
                    bottomNavigationView = findViewById(R.id.bottom_nav);
                    bottomNavigationView.setSelectedItemId(R.id.menu_explorer);
                }
                if (returnFromProfileUpdate) {
                    User updatedUser = (User) data.getSerializableExtra("user");
                    user = updatedUser;
                    ( (TextView)findViewById(R.id.textView5)).setText("Welcome "+ updatedUser.getFullName());
                    bottomNavigationView = findViewById(R.id.bottom_nav);
                    bottomNavigationView.setSelectedItemId(R.id.menu_explorer);
                }
            }
        }
    }
}