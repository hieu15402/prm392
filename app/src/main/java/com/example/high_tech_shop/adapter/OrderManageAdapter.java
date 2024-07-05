package com.example.high_tech_shop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.admin.OrderManageActivity;
import com.example.high_tech_shop.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderManageAdapter extends RecyclerView.Adapter<OrderManageAdapter.OrderHolder>{
    private List<Order> orders = new ArrayList<>();
    private OrderManageActivity activity;

    public OrderManageAdapter(OrderManageActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        return new OrderHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Order currentOrder = orders.get(position);
        holder.textViewOrderId.setText("Order ID: " + currentOrder.getId());
        holder.textViewCustomerName.setText("Customer: " + currentOrder.getNameCustomer());
        holder.textViewTotalPrice.setText("Total Price: " + currentOrder.getTotalPrice());
        holder.textViewStatus.setText("Status: " + currentOrder.getStatus());

        holder.buttonUpdate.setOnClickListener(v -> activity.handleUpdateOrder(currentOrder));
        holder.buttonDelete.setOnClickListener(v -> activity.handleDeleteOrder(currentOrder));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    class OrderHolder extends RecyclerView.ViewHolder {
        private TextView textViewOrderId;
        private TextView textViewCustomerName;
        private TextView textViewTotalPrice;
        private TextView textViewStatus;
        private Button buttonUpdate;
        private Button buttonDelete;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.text_view_order_id);
            textViewCustomerName = itemView.findViewById(R.id.text_view_customer_name);
            textViewTotalPrice = itemView.findViewById(R.id.text_view_total_price);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
            buttonUpdate = itemView.findViewById(R.id.button_update_order);
            buttonDelete = itemView.findViewById(R.id.button_delete_order);
        }
    }
}

