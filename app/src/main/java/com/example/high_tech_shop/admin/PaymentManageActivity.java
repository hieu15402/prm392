package com.example.high_tech_shop.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

        adapter = new PaymentAdapter(this);
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
        showVerifyConfirmationDialog(payment);
    }

    public void handleRefundPayment(Payment payment) {
        showRefundConfirmationDialog(payment);
    }

    private void showVerifyConfirmationDialog(Payment payment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Verification");
        builder.setMessage("Are you sure you want to set this payment to Processing?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                payment.setStatus("Processing");
                new UpdatePaymentTask().execute(payment);
                Toast.makeText(PaymentManageActivity.this, "Payment status set to Processing: " + payment.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showRefundConfirmationDialog(Payment payment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Refund");
        builder.setMessage("Are you sure you want to refund this payment?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                payment.setStatus("Refunded");
                new UpdatePaymentTask().execute(payment);
                Toast.makeText(PaymentManageActivity.this, "Payment status set to Refunded: " + payment.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class UpdatePaymentTask extends AsyncTask<Payment, Void, Void> {
        @Override
        protected Void doInBackground(Payment... payments) {
            paymentRepository.updatePayment(payments[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadPayments();
        }
    }
}
