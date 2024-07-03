package com.example.high_tech_shop.entity.relationship;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.high_tech_shop.entity.Cart;
import com.example.high_tech_shop.entity.CartItem;

import java.util.List;

public class CartWithItems {
    @Embedded
    public Cart cart;

    @Relation(parentColumn = "id", entityColumn = "cartId")
    public List<CartItem> cartItems;
}

