package com.example.high_tech_shop.dao;

import androidx.room.*;

import com.example.high_tech_shop.entity.Order;

import java.util.List;
@Dao
public interface OrderDAO {
    @Query("SELECT * FROM `Order`")
    public List<Order> getAllOrders();
    @Query("SELECT * FROM `Order` WHERE id = :id")
    public Order getOrderById(int id);
    @Insert
    public void addOrder(Order order);
    @Insert
    public void addOrder(List<Order> orders);
    @Update
    public void updateOrder(Order order);
    @Delete
    public void deleteOrder(Order order);
    @Query("SELECT id FROM `Order` WHERE userId = :userId")
    List<Integer> getOrdersByUserId(int userId);
}
