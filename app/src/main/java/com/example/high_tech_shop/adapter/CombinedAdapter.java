package com.example.high_tech_shop.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.entity.UserAddress;

import java.util.List;

public class CombinedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_ADDRESS = 1;

    private AddressDefaultAdapter defaultAdapter;
    private AddressAdapter addressAdapter;

    public CombinedAdapter(AddressDefaultAdapter defaultAdapter, AddressAdapter addressAdapter) {
        this.defaultAdapter = defaultAdapter;
        this.addressAdapter = addressAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_DEFAULT;
        } else {
            return TYPE_ADDRESS;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_DEFAULT) {
            return defaultAdapter.onCreateViewHolder(parent, viewType);
        } else {
            return addressAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_DEFAULT) {
            defaultAdapter.onBindViewHolder((AddressDefaultAdapter.ViewHolder) holder, 0);
        } else {
            addressAdapter.onBindViewHolder((AddressAdapter.ViewHolder) holder, position - 1);
        }
    }

    @Override
    public int getItemCount() {
        return 1 + addressAdapter.getItemCount();
    }

    public void setDefaultAddress(UserAddress userAddress) {
        defaultAdapter.setTasks(userAddress);
        notifyItemChanged(0);
    }

    public void setAddressList(List<UserAddress> userAddressList) {
        addressAdapter.setTasks(userAddressList);
        notifyDataSetChanged();
    }
}