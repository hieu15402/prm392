package com.example.high_tech_shop.entity.relationship;



import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.high_tech_shop.entity.Order;
import com.example.high_tech_shop.entity.OrderItem;
import com.example.high_tech_shop.entity.Payment;

import java.util.List;

public class OrderWithItemsAndPayment {
    @Embedded
    public Order order;

    @Relation(parentColumn = "id", entityColumn = "orderId")
    public List<OrderItem> orderItems;

    @Relation(parentColumn = "id", entityColumn = "orderId")
    public Payment payment;
}

