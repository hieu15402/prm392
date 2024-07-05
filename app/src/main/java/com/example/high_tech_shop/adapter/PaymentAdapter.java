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

    public PaymentAdapter() {
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
        holder.textViewOrderId.setText(String.valueOf(currentPayment.getOrderId()));
        holder.textViewType.setText(currentPayment.getType());
        holder.textViewTotalPrice.setText(String.valueOf(currentPayment.getTotalPrice()));
        holder.buttonVerify.setOnClickListener(v -> activity.handleVerifyPayment(currentPayment));
        holder.buttonRefund.setOnClickListener(v -> activity.handleProcessRefund(currentPayment));
        holder.buttonIssue.setOnClickListener(v -> activity.handlePaymentIssue(currentPayment));
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
        private TextView textViewOrderId;
        private TextView textViewType;
        private TextView textViewTotalPrice;
        private Button buttonVerify;
        private Button buttonRefund;
        private Button buttonIssue;

        public PaymentHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.text_view_order_id);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewTotalPrice = itemView.findViewById(R.id.text_view_total_price);
            buttonVerify = itemView.findViewById(R.id.button_verify);
            buttonRefund = itemView.findViewById(R.id.button_refund);
            buttonIssue = itemView.findViewById(R.id.button_issue);
        }
    }
}
