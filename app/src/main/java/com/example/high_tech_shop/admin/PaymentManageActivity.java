package com.example.high_tech_shop.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.adapter.PaymentAdapter;
import com.example.high_tech_shop.entity.Payment;
import com.example.high_tech_shop.repositories.PaymentRepository;

import java.util.List;

public class PaymentManageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PaymentAdapter adapter;
    private PaymentRepository paymentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_manage);

        paymentRepository = new PaymentRepository(getApplicationContext());

        recyclerView = findViewById(R.id.recycler_view_payments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new PaymentAdapter();
        recyclerView.setAdapter(adapter);

        loadPayments();
    }

    private void loadPayments() {
        new LoadPaymentsTask().execute();
    }

    private class LoadPaymentsTask extends AsyncTask<Void, Void, List<Payment>> {
        @Override
        protected List<Payment> doInBackground(Void... voids) {
            return paymentRepository.getAllPayments();
        }

        @Override
        protected void onPostExecute(List<Payment> payments) {
            super.onPostExecute(payments);
            adapter.setPayments(payments);
        }
    }

    public void handleVerifyPayment(Payment payment) {
        // Implement verification logic
        Toast.makeText(this, "Payment verified: " + payment.getId(), Toast.LENGTH_SHORT).show();
    }

    public void handleProcessRefund(Payment payment) {
        // Implement refund processing logic
        Toast.makeText(this, "Refund processed: " + payment.getId(), Toast.LENGTH_SHORT).show();
    }

    public void handlePaymentIssue(Payment payment) {
        // Implement payment issue handling logic
        Toast.makeText(this, "Payment issue resolved: " + payment.getId(), Toast.LENGTH_SHORT).show();
    }
}
