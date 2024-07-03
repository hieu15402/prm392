package com.example.high_tech_shop.common;

import androidx.room.TypeConverter;

import com.example.high_tech_shop.entity.Role;

public class Converters {
    @TypeConverter
    public static String fromRoleToString(Role role) {
        return role == null ? null : role.name();
    }

    @TypeConverter
    public static Role fromStringToRole(String role) {
        return role == null ? null : Role.valueOf(role);
    }
}