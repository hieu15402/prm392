package com.example.high_tech_shop.entity.relationship;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.high_tech_shop.entity.Cart;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.entity.UserAddress;

import java.util.List;

public class UserWithCartAndAddress {
    @Embedded
    public User user;

    @Relation(parentColumn = "id", entityColumn = "userId")
    public List<Cart> carts;

    @Relation(parentColumn = "id", entityColumn = "userId")
    public List<UserAddress> userAddresses;
}
