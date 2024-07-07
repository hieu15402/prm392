package com.example.high_tech_shop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.*;

import java.util.List;

public class AddressDefaultAdapter extends RecyclerView.Adapter<AddressDefaultAdapter.ViewHolder> {
    Context context;
    UserAddress userAddress;
    public AddressDefaultAdapter(Context context, UserAddress userAddress) {
        this.context = context;
        this.userAddress = userAddress;
    }

    @NonNull
    @Override
    public AddressDefaultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_address_default_item, parent, false);
        return new AddressDefaultAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressDefaultAdapter.ViewHolder holder, int position) {
        if (userAddress.getDistrict() == null && userAddress.getWard() == null) {
            holder.tvCity_default.setText(userAddress.getProvince());
        }
        if (userAddress.getDistrict() != null && userAddress.getWard() == null) {
            holder.tvCity_default.setText(userAddress.getProvince()
                    + "," + userAddress.getDistrict());
        }
        if (userAddress.getDistrict() != null && userAddress.getWard() != null) {
            holder.tvCity_default.setText(userAddress.getProvince()
                    + "," + userAddress.getDistrict()
                    + "," + userAddress.getWard());
        }
        holder.tvAddress_default.setText(userAddress.getAddress());
    }

    @Override
    public int getItemCount() {
       return 1;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(UserAddress _userAddress) {
        userAddress = _userAddress;
        notifyDataSetChanged();
    }
    public UserAddress getTasks() {
        return userAddress;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity_default, tvAddress_default;
        ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvCity_default = itemView.findViewById(R.id.tvCity_default);
            tvAddress_default = itemView.findViewById(R.id.tvAddress_default);

        }
    }
}
