package com.example.high_tech_shop.dao;

import androidx.room.*;
import androidx.room.Query;

import com.example.high_tech_shop.entity.Cart;

import java.util.List;
@Dao
public interface CartDAO {
    @Query("SELECT * FROM Cart")
    List<Cart> getAll();
    @Query("DELETE FROM Cart")
    void deleteAll();
    @Query("SELECT * FROM Cart WHERE id = :id")
    Cart getCartById(int id);
    @Insert
    void insert(Cart cart);
    @Update
    void update(Cart cart);
    @Delete
    void delete(Cart cart);
    @Insert
    void insertCart(List<Cart> carts);
    @Query("SELECT * FROM cart WHERE userId = :userId AND status = 1")
    Cart getCartByUserIdTrue(int userId);
    @Query("UPDATE cart SET status = 0 WHERE userId = :userId")
    void updateAllCartsStatusToFalse(int userId);
}
