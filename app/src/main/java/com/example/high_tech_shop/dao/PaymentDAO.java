package com.example.high_tech_shop.dao;

import androidx.room.*;

import com.example.high_tech_shop.entity.Payment;

import java.util.List;
@Dao
public interface PaymentDAO {
    @Insert
    void insertPayment(Payment payment);
    @Insert
    void insertPayment(List<Payment> payments);
    @Update
    void updatePayment(Payment payment);
    @Delete
    void deletePayment(Payment payment);
    @Query("SELECT * FROM Payment WHERE id = :id")
    Payment getPayment(int id);
    @Query("SELECT * FROM Payment")
    List<Payment> getAllPayments();
}
