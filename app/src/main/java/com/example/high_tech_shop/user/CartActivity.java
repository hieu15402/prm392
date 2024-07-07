package com.example.high_tech_shop.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.adapter.CartAdapter;
import com.example.high_tech_shop.entity.Cart;
import com.example.high_tech_shop.entity.CartItem;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.repositories.CartItemRepository;
import com.example.high_tech_shop.repositories.CartRepository;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private User user;
    private RecyclerView.Adapter adapter;
    private TextView subTotalTxt, deliveryTxt, taxTxt, totalTxt;
    private RecyclerView recyclerView;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        user = (User) getIntent().getSerializableExtra("user");
        if(user == null) finish();
        cartRepository = new CartRepository(this);
        cartItemRepository = new CartItemRepository(this);
        Cart cart = cartRepository.getCartByUserIdTrue(user.getId());
        if(cart == null ) {
            Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("returnFromCart", true);
            setResult(RESULT_OK, intent);
            finish();
        }else{
            List<CartItem> cartItemList = cartItemRepository.getCartItemByCartId(cart.getId());
            adapter = new CartAdapter(cartItemList,this,cart);
            subTotalTxt = findViewById(R.id.textView21);
            deliveryTxt = findViewById(R.id.textView22);
            taxTxt = findViewById(R.id.textView23);
            totalTxt = findViewById(R.id.textView25);
            backBtn = findViewById(R.id.backBtn);
            recyclerView = findViewById(R.id.viewMyCart);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("returnFromCart", true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            subTotalTxt.setText("$0");
            totalTxt.setText("$0");
            deliveryTxt.setText("$0");
            taxTxt.setText("$0");
        }

    }
    public void update(String _subTotalTxt,String _total, String _tax){
        subTotalTxt.setText(_subTotalTxt);
        totalTxt.setText(_total);
        taxTxt.setText(_tax);
    }
}