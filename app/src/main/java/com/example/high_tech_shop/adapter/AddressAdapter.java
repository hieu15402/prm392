package com.example.high_tech_shop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.entity.UserAddress;
import com.example.high_tech_shop.repositories.ProductRepository;
import com.example.high_tech_shop.repositories.UserAddressRepository;
import com.example.high_tech_shop.repositories.UserRepository;
import com.example.high_tech_shop.user.AddAddressActivity;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private List<UserAddress> userAddressList;
    private Context context;
    private UserRepository userRepository;
    private UserAddressRepository repository;

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
        UserAddress address = userAddressList.get(position);

        if (address.getDistrict() == null && address.getWard() == null) {
            holder.tvCity.setText(address.getProvince());
        } else if (address.getDistrict() != null && address.getWard() == null) {
            holder.tvCity.setText(address.getProvince() + "," + address.getDistrict());
        } else if (address.getDistrict() != null && address.getWard() != null) {
            holder.tvCity.setText(address.getProvince() + "," + address.getDistrict() + "," + address.getWard());
        }
        holder.tvAddress.setText(address.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                userRepository = new UserRepository(context);
                repository = new UserAddressRepository(context);
                User user = userRepository.findById(address.getUserId());
                UserAddress userAddress = repository.getUserAddressByUserIdDefault(address.getUserId());
                intent.putExtra("user", user);
                intent.putExtra("_addressUpdate", address.getAddress());
                intent.putExtra("isDefault", address.isStatus());
                intent.putExtra("addressId", address.getId());
                if (address.getDistrict() == null && address.getWard() == null) {
                    intent.putExtra("addressUpdate", address.getProvince());
                } else if (address.getDistrict() != null && address.getWard() == null) {
                    intent.putExtra("addressUpdate", address.getProvince() + "," + address.getDistrict());
                } else if (address.getDistrict() != null && address.getWard() != null) {
                    intent.putExtra("addressUpdate", address.getProvince() + "," + address.getDistrict() + "," + address.getWard());
                }

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userAddressList != null ? userAddressList.size() : 0;
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

