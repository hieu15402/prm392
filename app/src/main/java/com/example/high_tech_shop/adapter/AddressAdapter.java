package com.example.high_tech_shop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.UserAddress;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    List<UserAddress> userAddressList;
    Context context;
    public AddressAdapter(Context context, List<UserAddress> userAddressList) {
        this.userAddressList = userAddressList;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_address_item, parent, false);
        return new AddressAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
        if (userAddressList.get(position).getDistrict() == null && userAddressList.get(position).getWard() == null) {
            holder.tvCity.setText(userAddressList.get(position).getProvince());
        }
        if (userAddressList.get(position).getDistrict() != null && userAddressList.get(position).getWard() == null) {
            holder.tvCity.setText(userAddressList.get(position).getProvince()
                    + "," + userAddressList.get(position).getDistrict());
        }
        if (userAddressList.get(position).getDistrict() != null && userAddressList.get(position).getWard() != null) {
            holder.tvCity.setText(userAddressList.get(position).getProvince()
                    + "," + userAddressList.get(position).getDistrict()
                    + "," + userAddressList.get(position).getWard());
        }
        holder.tvAddress.setText(userAddressList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        if (userAddressList == null){
            return 0;
        }
        return userAddressList.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(List<UserAddress> _userAddressList) {
        userAddressList = _userAddressList;
        notifyDataSetChanged();
    }
    public List<UserAddress> getTasks() {
        return userAddressList;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity, tvAddress;
        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvAddress = itemView.findViewById(R.id.tvAddress);

        }
    }
}
