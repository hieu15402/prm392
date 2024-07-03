package com.example.high_tech_shop.user;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.adapter.FilterProductAdapter;
import com.example.high_tech_shop.entity.Cart;
import com.example.high_tech_shop.entity.CartItem;
import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.repositories.CartItemRepository;
import com.example.high_tech_shop.repositories.CartRepository;
import com.example.high_tech_shop.repositories.CategoryRepository;
import com.example.high_tech_shop.repositories.ProductRepository;

import java.util.List;

public class FilterProductActivity extends AppCompatActivity {
    EditText etSearch;
    ImageView backBtn;
    int categoryId;
    String search;
    TextView categoryTxt;
    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    CartItemRepository cartItemRepository;
    CartRepository cartRepository;
    FilterProductAdapter adapter;
    List<Product> products;
    ListView lvProduct;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        user= (User) getIntent().getSerializableExtra("user");
        if(user == null) finish();
        search = getIntent().getStringExtra("search");
        categoryId = getIntent().getIntExtra("categoryId",5);
        etSearch = findViewById(R.id.etSearch);
        backBtn = findViewById(R.id.imageView20);
        categoryTxt = findViewById(R.id.categoryTxt);
        lvProduct = findViewById(R.id.lvProduct);

        productRepository = new ProductRepository(this);
        categoryRepository = new CategoryRepository(this);
        cartRepository = new CartRepository(this);
        cartItemRepository = new CartItemRepository(this);

        if(search != null) etSearch.setText(search);
        if(categoryId == 5) {
            categoryTxt.setText("All");
            products = productRepository.getAll();
        }
        else {
            categoryTxt.setText(categoryRepository.findById(categoryId).getName());
            products = productRepository.getProductsByCategoryId(categoryId);
        }


        adapter = new FilterProductAdapter(this,products);
        lvProduct.setAdapter(adapter);


        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {

                    return true;
                }
                return false;
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void addToCart(Product product){
        if (product.getUnitInStock() < 1) {
            Toast.makeText(getApplicationContext(), "Out of product", Toast.LENGTH_SHORT).show();
            return;
        }

        Cart cart = cartRepository.getCartByUserIdTrue(user.getId());
        if (cart == null) {
            int cartId = (cartRepository.getAll() == null || cartRepository.getAll().isEmpty()) ? 1 : cartRepository.getAll().size() + 1;
            cart = new Cart(cartId, 0, user.getId(), true);
            cartRepository.insert(cart);
        }

        CartItem cartItem = cartItemRepository.getCartItemByCartAndProductId(cart.getId(), product.getId());
        if (cartItem == null) {
            cartItem = new CartItem(cartItemRepository.getAll().size() + 1, 1, product.getPrice(), cart.getId(), product.getId());
            cartItemRepository.insert(cartItem);
        } else {
            cartItem.setPrice(cartItem.getPrice() + product.getPrice());
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.update(cartItem);
        }

        cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
        cartRepository.update(cart);

        Toast.makeText(getApplicationContext(), "Insert successfully", Toast.LENGTH_SHORT).show();
    }

}