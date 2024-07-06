package com.example.high_tech_shop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.admin.PaymentManageActivity;
import com.example.high_tech_shop.entity.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentHolder> {
    private List<Payment> payments = new ArrayList<>();
    private PaymentManageActivity activity;

    public PaymentAdapter(PaymentManageActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_item, parent, false);
        return new PaymentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHolder holder, int position) {
        Payment currentPayment = payments.get(position);
        holder.textViewPaymentId.setText("Payment ID: " + currentPayment.getId());
        holder.textViewType.setText("Type: " + currentPayment.getType());
        holder.textViewTotalPrice.setText("Total Price: " + currentPayment.getTotalPrice());
        holder.textViewStatus.setText("Status: " + currentPayment.getStatus());

        holder.buttonVerify.setOnClickListener(v -> activity.handleVerifyPayment(currentPayment));
        holder.buttonRefund.setOnClickListener(v -> activity.handleRefundPayment(currentPayment));
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
        notifyDataSetChanged();
    }

    class PaymentHolder extends RecyclerView.ViewHolder {
        private TextView textViewPaymentId;
        private TextView textViewType;
        private TextView textViewTotalPrice;
        private TextView textViewStatus;
        private Button buttonVerify;
        private Button buttonRefund;

        public PaymentHolder(@NonNull View itemView) {
            super(itemView);
            textViewPaymentId = itemView.findViewById(R.id.text_view_payment_id);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewTotalPrice = itemView.findViewById(R.id.text_view_total_price);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
            buttonVerify = itemView.findViewById(R.id.button_verify_payment);
            buttonRefund = itemView.findViewById(R.id.button_refund_payment);
        }
    }
}
