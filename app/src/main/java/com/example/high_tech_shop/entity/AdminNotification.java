package com.example.high_tech_shop.entity;

public class AdminNotification {
    private String type;
    private String message;
    private long timestamp;

    public AdminNotification(String type, String message, long timestamp) {
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}