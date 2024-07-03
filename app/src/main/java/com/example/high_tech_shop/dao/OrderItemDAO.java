package com.example.high_tech_shop.dao;

import androidx.room.*;

import com.example.high_tech_shop.entity.OrderItem;

import java.util.List;
@Dao
public interface OrderItemDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addOrderItem(OrderItem orderItem);
    @Insert()
    void addOrderItem(List<OrderItem> orderItems);
    @Update
    void updateOrderItem(OrderItem orderItem);
    @Delete
    void delete(OrderItem orderItem);
    @Query("SELECT * FROM OrderItem WHERE OrderItem.id = :orderItemId")
    OrderItem getOrderItemById(int orderItemId);
    @Query("SELECT * FROM OrderItem")
    List<OrderItem> getAllOrderItems();
    @Query("SELECT * FROM OrderItem WHERE orderId IN (:orderIds)")
    List<OrderItem> getOrderItemsByOrderIds(List<Integer> orderIds);
}
