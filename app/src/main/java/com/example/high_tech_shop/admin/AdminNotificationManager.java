package com.example.high_tech_shop.admin;

import com.example.high_tech_shop.entity.AdminNotification;

import java.util.ArrayList;
import java.util.List;

public class AdminNotificationManager {
    private List<AdminNotification> notifications;

    public AdminNotificationManager() {
        notifications = new ArrayList<>();
    }

    public void addNotification(String type, String message) {
        long timestamp = System.currentTimeMillis();
        notifications.add(new AdminNotification(type, message, timestamp));
    }

    public List<AdminNotification> getNotifications() {
        return notifications;
    }

    public void clearNotifications() {
        notifications.clear();
    }
}
