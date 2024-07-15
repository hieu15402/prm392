package com.example.high_tech_shop.user;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.Cart;
import com.example.high_tech_shop.entity.CartItem;
import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.repositories.CartItemRepository;
import com.example.high_tech_shop.repositories.CartRepository;

import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    private Button addToCartBtn;
    private TextView titleTxt, titlePrice,descriptionTxt;
    private ImageView pic, back;
    private Product product;
    private int numberOrder = 1;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.tittleTxt);
        titlePrice = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        pic = findViewById(R.id.imageView13);
        back = findViewById(R.id.imageView2);
        product = (Product) getIntent().getSerializableExtra("product");
        User user= (User) getIntent().getSerializableExtra("user");
        cartRepository = new CartRepository(this);
        cartItemRepository = new CartItemRepository(this);
        if(user ==null) finish();
        int drawableResourceId = this.getResources().getIdentifier(product.getCoverImage()
                ,"drawable",this.getPackageName());
        Glide.with(this)
                //.load(products.get(position).getCoverImage())
                .load(drawableResourceId)
                //.placeholder(drawableResourceId)
                .into(this.pic);
        titleTxt.setText(product.getName());
        titlePrice.setText("$"+product.getPrice());
        descriptionTxt.setText(product.getDescription());
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                // Gọi finish() sau khi đã hoàn tất xử lý trong onClick
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}