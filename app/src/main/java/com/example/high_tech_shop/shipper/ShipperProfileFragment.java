package com.example.high_tech_shop.shipper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.User;

public class ShipperProfileFragment extends Fragment {

    private static final String ARG_USER = "user";
    private User user;

    public ShipperProfileFragment() {
        // Required empty public constructor
    }

    public static ShipperProfileFragment newInstance(User user) {
        ShipperProfileFragment fragment = new ShipperProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(ARG_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipper_profile, container, false);

        // Set the user's full name to the TextView
        TextView tvFullName = view.findViewById(R.id.tv_full_name);
        TextView tvEmail = view.findViewById(R.id.tv_email);
        TextView tvPhone = view.findViewById(R.id.tv_phone);
        TextView tvAddress = view.findViewById(R.id.tv_address);

        if (user != null) {
            tvFullName.setText(user.getFullName());
            tvEmail.setText(user.getEmail());
            tvPhone.setText(user.getPhone());
            // Assuming the User class has an address field. If not, adjust accordingly.
            tvAddress.setText(""); // Replace with actual address field from User class
        }

        return view;
    }
}
