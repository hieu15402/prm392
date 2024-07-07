package com.example.high_tech_shop.repositories;

import android.content.Context;

import com.example.high_tech_shop.dao.UserAddressDAO;
import com.example.high_tech_shop.entity.UserAddress;
import com.example.high_tech_shop.room.HighTechShopRoomDatabase;

import java.util.List;

public class UserAddressRepository {
    private UserAddressDAO userAddressDAO;
    public UserAddressRepository(Context context) {
        this.userAddressDAO = HighTechShopRoomDatabase.getInstance(context).userAddressDAO();
    }
    public void insert(List<UserAddress> userAddressList) {
        userAddressDAO.addUserAddress(userAddressList);
    }
    public void deleteAll() {
        userAddressDAO.deleteAll();
    }
    public List<UserAddress> getUserAddressByUserId(int userId) {
        return userAddressDAO.getUserAddressesByUserId(userId);
    }
    public List<UserAddress> getAll() {
        return userAddressDAO.getAllUserAddresses();
    }
    public UserAddress getUserAddressByUserIdDefault(int userId) {
        return userAddressDAO.getUserAddressesByUserIdDefault(userId);
    }
    public void insert(UserAddress userAddress) {
        userAddressDAO.addUserAddress(userAddress);
    }
}
