package com.example.high_tech_shop.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Order.class, parentColumns = "id", childColumns = "orderId", onDelete = ForeignKey.CASCADE))
public class Payment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String type;
    private double totalPrice;
    private int orderId;
    private String status;

    public Payment(int id, String type, double totalPrice, int orderId, String status) {
        this.id = id;
        this.type = type;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

