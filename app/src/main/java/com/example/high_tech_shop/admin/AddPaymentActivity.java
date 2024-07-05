package com.example.high_tech_shop.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.Payment;
import com.example.high_tech_shop.repositories.PaymentRepository;

import java.util.List;

public class AddPaymentActivity  extends AppCompatActivity {
    private EditText editTextOrderId;
    private EditText editTextType;
    private EditText editTextTotalPrice;
    private PaymentRepository paymentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        paymentRepository = new PaymentRepository(getApplicationContext());

        editTextOrderId = findViewById(R.id.edit_text_order_id);
        editTextType = findViewById(R.id.edit_text_type);
        editTextTotalPrice = findViewById(R.id.edit_text_total_price);

        findViewById(R.id.button_save_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePayment();
            }
        });
    }

    private void savePayment() {
        int orderId = Integer.parseInt(editTextOrderId.getText().toString());
        String type = editTextType.getText().toString();
        double totalPrice = Double.parseDouble(editTextTotalPrice.getText().toString());

        Payment payment = new Payment(0, type, totalPrice, orderId);
        paymentRepository.insertPayment((List<Payment>) payment);

        Toast.makeText(this, "Payment saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
