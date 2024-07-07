package com.example.high_tech_shop.admin;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.high_tech_shop.R;

public class TechActivity extends AppCompatActivity {

    // Declare your views here
    private TextView amazonName, amazonDesc;
    private ImageView amazonImage;
    private TextView clothingName, elecName, homeName, beautyName, pharmName, grocName;
    private ImageView clothingImage, elecImage, homeImage, beautyImage, pharmImage, grocImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech);

        // Initialize your views here
        amazonName = findViewById(R.id.amazonName);
        amazonDesc = findViewById(R.id.amazonDesc);
        amazonImage = findViewById(R.id.amazonImage);

        clothingName = findViewById(R.id.clothingName);
        elecName = findViewById(R.id.elecName);
        homeName = findViewById(R.id.homeName);
        beautyName = findViewById(R.id.beautyName);
        pharmName = findViewById(R.id.pharmName);
        grocName = findViewById(R.id.grocName);

        clothingImage = findViewById(R.id.clothingImage);
        elecImage = findViewById(R.id.elecImage);
        homeImage = findViewById(R.id.homeImage);
        beautyImage = findViewById(R.id.beautyImage);
        pharmImage = findViewById(R.id.pharmImage);
        grocImage = findViewById(R.id.grocImage);

        // You can set texts or images programmatically if needed
        // Example:
        // amazonName.setText("Amazon");
        // amazonImage.setImageResource(R.drawable.amazon_logo);
    }
}
