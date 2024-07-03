package com.example.high_tech_shop.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.high_tech_shop.MainActivity;
import com.example.high_tech_shop.R;
import com.example.high_tech_shop.common.DataCommon;
import com.example.high_tech_shop.dao.UserDAO;
import com.example.high_tech_shop.entity.Role;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.repositories.CartItemRepository;
import com.example.high_tech_shop.repositories.CartRepository;
import com.example.high_tech_shop.room.HighTechShopRoomDatabase;

import java.io.Serializable;

public class UserLoginFormActivity extends AppCompatActivity{
    UserDAO userDAO;
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_form);
        setTitle("Login");
        DataCommon.removeData(this);
        DataCommon.initData(this);
        cartRepository = new CartRepository(this);
        cartItemRepository = new CartItemRepository(this);
        cartItemRepository.deleteAll();
        cartRepository.deleteAll();
        Button loginButton = findViewById(R.id.loginButton);
        userDAO = HighTechShopRoomDatabase.getInstance(this).userDAO();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ( (EditText) findViewById(R.id.username)).getText().toString().trim();
                String password = ( (EditText) findViewById(R.id.password)).getText().toString().trim();
                if (username.equals("") || password.equals(""))
                {
                    Toast.makeText(UserLoginFormActivity.this, "Email or password empty", Toast.LENGTH_SHORT).show();
                }else {
                    User user = userDAO.selectUserByUsernameAndPassword(username,password);
                    if (user==null) {
                        Toast.makeText(UserLoginFormActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                    }else if(user.getRole() == Role.USER){
                        Intent intent = new Intent(UserLoginFormActivity.this, HomePageActivity.class);
                        intent.putExtra("user", (Serializable) user);
                        startActivity(intent);
                    }
                }
            }
        });
//        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}