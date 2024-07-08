package com.example.high_tech_shop.shipper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.adapter.OrderBaseAdapter;
import com.example.high_tech_shop.entity.Order;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.repositories.OrderItemRepository;
import com.example.high_tech_shop.repositories.OrderRepository;
import com.example.high_tech_shop.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ShipperHomeFragment extends Fragment {
    private static final String ARG_ORDERS = "orders";
    private static final String ARG_USER = "user";

    private User user;
    private List<Order> orders;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;
    private OrderBaseAdapter adapter;

    public ShipperHomeFragment() {
        // Required empty public constructor
    }

    public static ShipperHomeFragment newInstance(User user, List<Order> orders) {
        ShipperHomeFragment fragment = new ShipperHomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        args.putSerializable(ARG_ORDERS, (ArrayList<Order>) orders); // Cast to ArrayList to make it Serializable
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(ARG_USER);
            orders = (List<Order>) getArguments().getSerializable(ARG_ORDERS);
        }
        orderRepository = new OrderRepository(getContext());
        orderItemRepository = new OrderItemRepository(getContext());
        productRepository = new ProductRepository(getContext());
    }

    public void onResume() {
        super.onResume();
        // Refresh the order list when the fragment is resumed
        if (getActivity() instanceof ShipperMainActivity) {
            ((ShipperMainActivity) getActivity()).fetchOrders();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipper_home, container, false);

        // Set the user's full name to the TextView
        TextView tvFullName = view.findViewById(R.id.tv_full_name);
        if (user != null) {
            tvFullName.setText(user.getFullName());
        }

        // Setup ListView
        ListView listView = view.findViewById(R.id.list_view_orders);
        adapter = new OrderBaseAdapter(getContext(), orders, orderItemRepository, productRepository, orderRepository, this);
        listView.setAdapter(adapter);

        return view;
    }

    public void updateOrderList(List<Order> updatedOrders) {
        this.orders = new ArrayList<>();
        for (Order order : updatedOrders) {
            if (order.getStatus().equals("Processing") || order.getStatus().equals("Delivered")) {
                this.orders.add(order);
            }
        }
        adapter.updateOrderList(this.orders);
    }
}