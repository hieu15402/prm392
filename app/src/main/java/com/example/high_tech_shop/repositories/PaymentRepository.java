package com.example.high_tech_shop.repositories;

import android.content.Context;

import com.example.high_tech_shop.dao.PaymentDAO;
import com.example.high_tech_shop.entity.Payment;
import com.example.high_tech_shop.room.HighTechShopRoomDatabase;

import java.util.List;

public class PaymentRepository {
    private PaymentDAO paymentDAO;
    public PaymentRepository(Context context) {
        this.paymentDAO = HighTechShopRoomDatabase.getInstance(context).paymentDAO();
    }
    public void insertPayment(List<Payment> payments) {
        paymentDAO.insertPayment(payments);
    }
    public List<Payment> getAllPayments() {
        return paymentDAO.getAllPayments();
    }
}
