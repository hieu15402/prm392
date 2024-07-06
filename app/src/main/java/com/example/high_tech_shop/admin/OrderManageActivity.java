package com.example.high_tech_shop.admin;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.adapter.OrderAdapter;
import com.example.high_tech_shop.adapter.OrderManageAdapter;
import com.example.high_tech_shop.entity.Order;
import com.example.high_tech_shop.repositories.OrderRepository;

import java.util.List;

public class OrderManageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderManageAdapter adapter;
    private OrderRepository orderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manage);

        orderRepository = new OrderRepository(getApplicationContext());

        recyclerView = findViewById(R.id.recycler_view_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new OrderManageAdapter(this);
        recyclerView.setAdapter(adapter);

        loadOrders();
    }

    private void loadOrders() {
        new LoadOrdersTask().execute();
    }

    private class LoadOrdersTask extends AsyncTask<Void, Void, List<Order>> {
        @Override
        protected List<Order> doInBackground(Void... voids) {
            return orderRepository.getAll();
        }

        @Override
        protected void onPostExecute(List<Order> orders) {
            super.onPostExecute(orders);
            adapter.setOrders(orders);
        }
    }

    public void handleUpdateOrder(Order order) {
        showUpdateStatusDialog(order);
    }

    public void handleDeleteOrder(Order order) {
        showDeleteConfirmationDialog(order);
    }

    private void showUpdateStatusDialog(Order order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Order Status");

        View view = getLayoutInflater().inflate(R.layout.dialog_update_status, null);
        Spinner spinner = view.findViewById(R.id.spinner_status);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.order_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        builder.setView(view);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedStatus = spinner.getSelectedItem().toString();
                order.setStatus(selectedStatus);
                new UpdateOrderTask().execute(order);
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDeleteConfirmationDialog(Order order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to cancel this order?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                order.setStatus("Cancelled");
                new UpdateOrderTask().execute(order);
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class UpdateOrderTask extends AsyncTask<Order, Void, Void> {
        @Override
        protected Void doInBackground(Order... orders) {
            orderRepository.updateOrder(orders[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadOrders();
            Toast.makeText(OrderManageActivity.this, "Order updated", Toast.LENGTH_SHORT).show();
        }
    }
}
