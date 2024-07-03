package com.example.high_tech_shop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.high_tech_shop.common.DataCommon;
import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.repositories.*;
import com.example.high_tech_shop.room.HighTechShopRoomDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private CategoryRepository categoryRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private UserAddressRepository userAddressRepository;
    private ProductSubImageRepository productSubImageRepository;
    private PaymentRepository paymentRepository;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         HighTechShopRoomDatabase database = Room.databaseBuilder(this, HighTechShopRoomDatabase.class, "HighTechShop")
                 .allowMainThreadQueries()
                 .build();

//       Chạy lần đầu thì mở nó ra
//       DataCommon.initData(this);
     }
}