package com.example.high_tech_shop.user;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.entity.UserAddress;
import com.example.high_tech_shop.repositories.UserAddressRepository;

public class AddAddressActivity extends AppCompatActivity {
    private TextView text_location,text_address;
    private ImageButton button_location;
    private ImageView btnBackListAddress;
    private Button btnComplete;
    private Switch sDefault;
    private UserAddressRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_address);

        text_location = findViewById(R.id.text_location);
        button_location = findViewById(R.id.button_location);
        text_address = findViewById(R.id.text_address);
        btnBackListAddress = findViewById(R.id.btnBackListAddress);
        btnComplete = findViewById(R.id.btnComplete);
        sDefault = findViewById(R.id.sDefault);

        repository = new UserAddressRepository(this);
        int userAddressId = (repository.getAll() == null || repository.getAll().isEmpty()) ? 1 : repository.getAll().size() + 1;
        Intent intent = getIntent();
        String location = "";
        if(intent != null && intent.hasExtra("address")){
            location = intent.getStringExtra("address");
            text_location.setText(location);
        }
        User user = (User) intent.getSerializableExtra("user");
        String finalLocation = location;
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] _location = finalLocation.split(",");
                if(_location.length == 3){
                    String province = _location[0];
                    String district = _location[1];
                    String ward = _location[2];
                    UserAddress address = new UserAddress(userAddressId,user.getId(),province,district,text_address.getText().toString(),ward,sDefault.isChecked());
                    repository.insert(address);
                    Intent _intent = new Intent(AddAddressActivity.this, AddressActivity.class);
                    _intent.putExtra("user", user);
                    startActivity(_intent);
                    finish();
                }else if(_location.length == 2){
                    String province = _location[0];
                    String district = _location[1];
                    UserAddress address = new UserAddress(userAddressId,user.getId(),province,district,text_address.getText().toString(),null,sDefault.isChecked());
                    repository.insert(address);
                    Intent _intent = new Intent(AddAddressActivity.this, AddressActivity.class);
                    _intent.putExtra("user", user);
                    startActivity(_intent);
                    finish();
                }
            }
        });


        btnBackListAddress.setOnClickListener(v -> {
            Intent _intent = new Intent(this, AddressActivity.class);
            _intent.putExtra("user", user);
            startActivity(_intent);
            finish();
        });
        button_location.setOnClickListener(v -> {
            Intent _intent = new Intent(this, LoadAddressAPIActivity.class);
            _intent.putExtra("user", user);
            startActivity(_intent);
            finish();
        });


    }
}