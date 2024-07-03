package com.example.high_tech_shop.user;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.repositories.UserRepository;

public class ProfileActivity extends AppCompatActivity {
    private ConstraintLayout address;
    TextView tvFullName,tvEmail, tvPhone, tvDataOfBirth, tvChange;
    Button btnEditProfile, btnBack;
    UserRepository userRepository;
    private LinearLayout orderHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        address = findViewById(R.id.address);
        orderHistory = findViewById(R.id.order_history);

        tvFullName = findViewById(R.id.tvUserName);
        tvEmail = findViewById(R.id.textView18);
        tvPhone = findViewById(R.id.tvPhoneContent);
        tvDataOfBirth = findViewById(R.id.tvDoBContent);
        tvChange = findViewById(R.id.tvChange);
        btnEditProfile = findViewById(R.id.btnEdit);
        btnBack = findViewById(R.id.btnBack);

        userRepository = new UserRepository(this);

        Intent intent = getIntent();
        final User user11 = (User) intent.getSerializableExtra("user");
        User user = userRepository.findByEmail(user11.getEmail());
        assert user != null;
        tvFullName.setText(user.getFullName());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getPhone());
        tvDataOfBirth.setText(user.getDob());

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AddressActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, OrderActivity.class);
                User user = userRepository.findByEmail(user11.getEmail());
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ProfileActivity.this);
                dialog.setContentView(R.layout.activity_update_profile_user);
                Intent intent = getIntent();
                User user1 = (User) intent.getSerializableExtra("user");

                EditText etFullName , etPhone, etDob;
                TextView tvEmail;
                Button btnUpdate, btnExit;

                etFullName = dialog.findViewById(R.id.editTextText);
                tvEmail = dialog.findViewById(R.id.editTextText2);
                etPhone = dialog.findViewById(R.id.editTextText3);
                etDob = dialog.findViewById(R.id.editTextDate);
                btnUpdate = dialog.findViewById(R.id.button);
                btnExit = dialog.findViewById(R.id.button2);

                assert user != null;
                etFullName.setText(user.getFullName());
                tvEmail.setText(user.getEmail());
                etPhone.setText(user.getPhone());
                etDob.setText(user.getDob());

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(etFullName.getText().toString().isEmpty()||
                                tvEmail.getText().toString().isEmpty()||
                                etPhone.getText().toString().isEmpty()||
                                etDob.getText().toString().isEmpty()) dialog.dismiss();
                        user1.setFullName(etFullName.getText().toString());
                        user1.setEmail(tvEmail.getText().toString());
                        user1.setPhone(etPhone.getText().toString());
                        user1.setDob(etDob.getText().toString());
                        userRepository.update(user1);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvFullName.setText(user1.getFullName());
                                tvEmail.setText(user1.getEmail());
                                tvPhone.setText(user1.getPhone());
                                tvDataOfBirth.setText(user1.getDob());
                            }
                        });

                        dialog.dismiss();
                    }
                });
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileActivity.this, HomePageActivity.class);

                intent.putExtra("returnFromProfileUpdate", true);

                User userUpdated = userRepository.findByEmail(user.getEmail());

                intent.putExtra("user", userUpdated);

                setResult(RESULT_OK, intent);

                finish();
            }
        });
        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ProfileActivity.this);
                dialog.setContentView(R.layout.activity_change_password_user);
                Intent intent = getIntent();
                User user = (User) intent.getSerializableExtra("user");
                assert user != null;
                User currentUser = userRepository.findByEmail(user.getEmail());

                EditText currentPass, newPass,confirmPass;
                Button btnUpdate, btnCancel;
                currentPass = dialog.findViewById(R.id.editTextTextPassword);
                newPass = dialog.findViewById(R.id.editTextTextPassword2);
                confirmPass = dialog.findViewById(R.id.editTextTextPassword3);
                btnUpdate = dialog.findViewById(R.id.button3);
                btnCancel = dialog.findViewById(R.id.button4);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean f = true;
                        if(currentPass.getText().toString().isEmpty()||
                                newPass.getText().toString().isEmpty()||
                                confirmPass.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(), "Can not empty", Toast.LENGTH_SHORT).show();
                            f = false;
                        }
                        if(!currentPass.getText().toString().equals(currentUser.getPassword())){
                            f = false;
                            Toast.makeText(getApplicationContext(), "Current password is wrong", Toast.LENGTH_SHORT).show();
                        }
                        if(newPass.getText().toString().equals(currentPass.getText().toString())){
                            f = false;
                            Toast.makeText(getApplicationContext(), "The new password must be different from the current password.", Toast.LENGTH_SHORT).show();
                        }
                        if(!newPass.getText().toString().equals(confirmPass.getText().toString())){
                            f = false;
                            Toast.makeText(getApplicationContext(), "New password and confirm password do not match.", Toast.LENGTH_SHORT).show();
                        }
                        if(f){
                            currentUser.setPassword(newPass.getText().toString());
                            userRepository.update(currentUser);
                            Toast.makeText(getApplicationContext(), "Change password success.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

    }
}