package com.example.high_tech_shop.repositories;

import android.content.Context;

import com.example.high_tech_shop.dao.OrderDAO;
import com.example.high_tech_shop.entity.Order;
import com.example.high_tech_shop.room.HighTechShopRoomDatabase;

import java.util.Arrays;
import java.util.List;

public class OrderRepository {
    private OrderDAO orderDAO;
    public OrderRepository(Context context) {
        this.orderDAO = HighTechShopRoomDatabase.getInstance(context).orderDAO();
    }
    public void insert(List<Order> orders) {
        orderDAO.addOrder(orders);
    }
    public List<Integer> getOrdersByUserId(int userId){
        return orderDAO.getOrdersByUserId(userId);}
        public List<Order> getAll() {
            return orderDAO.getAllOrders();
        }
        public Order getOrderById(int id) {
            return orderDAO.getOrderById(id);
        }

    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderDAO.getOrdersByStatus(status);
    }
    public List<Order> getOrdersByStatuses(List<String> statuses) {
        return orderDAO.getOrdersByStatuses(statuses);
    }
}
