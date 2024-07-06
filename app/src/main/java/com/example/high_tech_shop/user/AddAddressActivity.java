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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.entity.UserAddress;
import com.example.high_tech_shop.repositories.UserAddressRepository;
import com.example.high_tech_shop.repositories.UserRepository;

import java.util.ArrayList;

public class AddAddressActivity extends AppCompatActivity {
    private TextView text_location,text_address;
    private ImageButton button_location;
    private ImageView btnBackListAddress;
    private Button btnComplete, btnDeleteAddress;
    private Switch sDefault;
    private UserAddressRepository repository;
    private UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        text_location = findViewById(R.id.text_location);
        button_location = findViewById(R.id.button_location);
        text_address = findViewById(R.id.text_address);
        btnBackListAddress = findViewById(R.id.btnBackListAddress);
        btnComplete = findViewById(R.id.btnComplete);
        sDefault = findViewById(R.id.sDefault);
        btnDeleteAddress = findViewById(R.id.btnDeleteAddress);

        repository = new UserAddressRepository(this);
        userRepository = new UserRepository(this);
        int userAddressId = (repository.getAll() == null || repository.getAll().isEmpty()) ? 1 : repository.getAll().size() + 1;
        Intent intent = getIntent();
        String location = "";
        int userAddressIdUpdate;
        if(intent != null && intent.hasExtra("addressUpdate")){
            text_location.setText(intent.getStringExtra("addressUpdate"));
            text_address.setText(intent.getStringExtra("_addressUpdate"));
            sDefault.setChecked(intent.getBooleanExtra("isDefault",false));
            userAddressIdUpdate = intent.getIntExtra("addressId",0);
            btnDeleteAddress.setVisibility(View.VISIBLE);
        } else {
            userAddressIdUpdate = 0;
        }
        if(intent != null && intent.hasExtra("address")){
            location = intent.getStringExtra("address");
            text_location.setText(location);
        }else{
            location = text_location.getText().toString();
        }

        User user = (User) intent.getSerializableExtra("user");
        if(user == null){
            Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show();
            return;
        }
        String finalLocation = location;
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] _location = finalLocation.split(",");
                UserAddress address ;

                if (intent != null && intent.hasExtra("addressUpdate")) {
                    address = repository.getUserAddressById(userAddressIdUpdate);
                    updateAddressFields(address, _location);

                    if (sDefault.isChecked()) {
                        updateDefaultAddress(address, user.getId());
                    } else {
                        repository.update(address);
                    }
                } else {
                    address = createNewAddress(userAddressId, user.getId(), _location);
                    repository.insert(address);
                }
                navigateToAddressActivity();
            }
        });
        btnBackListAddress.setOnClickListener(v -> {
            navigateToAddressActivity();
        });
        button_location.setOnClickListener(v -> {
            Intent _intent = new Intent(AddAddressActivity.this, LoadAddressAPIActivity.class);
            _intent.putExtra("user", user);
            if(!text_location.getText().toString().isEmpty()){
                _intent.putExtra("location", text_location.getText().toString());
                _intent.putExtra("_location", text_address.getText().toString());
            }
            startActivity(_intent);
            finish();
        });

        btnDeleteAddress.setOnClickListener(v -> {
            UserAddress address = repository.getUserAddressById(userAddressIdUpdate);
            repository.delete(address);
            navigateToAddressActivity();
        });
    }
    private void updateAddressFields(UserAddress address, String[] location) {
        address.setProvince(location[0]);
        if (location.length > 1) {
            address.setDistrict(location[1]);
        }
        if (location.length > 2) {
            address.setWard(location[2]);
        }
        address.setAddress(text_address.getText().toString());
    }

    private void updateDefaultAddress(UserAddress address,int userId) {
        ArrayList<UserAddress> userAddresses = new ArrayList<>();
        UserAddress _userAddress = repository.getUserAddressByUserIdDefault(userId);
        if (_userAddress != address) {
            _userAddress.setStatus(false);
            userAddresses.add(_userAddress);
        }
        address.setStatus(true);
        userAddresses.add(address);
        repository.update(userAddresses);
    }

    private UserAddress createNewAddress(int addressId, int userId, String[] location) {
        String province = location[0];
        String district = location.length > 1 ? location[1] : null;
        String ward = location.length > 2 ? location[2] : null;
        return new UserAddress(addressId, userId, province, district, text_address.getText().toString(), ward, sDefault.isChecked());
    }

    private void navigateToAddressActivity() {
        Intent _intent = new Intent(AddAddressActivity.this, AddressActivity.class);
        User user = (User) getIntent().getSerializableExtra("user");
        _intent.putExtra("user", user);
        startActivity(_intent);
        finish();
    }
}