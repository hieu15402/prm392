package com.example.high_tech_shop.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import android.content.Context;
import android.widget.Toast;

import com.example.high_tech_shop.common.Constant;
import com.example.high_tech_shop.common.Converters;
import com.example.high_tech_shop.dao.*;
import com.example.high_tech_shop.entity.*;

@Database(entities = {Cart.class, Product.class, User.class, Order.class, OrderItem.class, Category.class, CartItem.class, ProductSubImage.class, UserAddress.class, Payment.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class HighTechShopRoomDatabase extends RoomDatabase {

    public abstract CartDAO cartDAO();
    public abstract CartItemDAO cartItemDAO();
    public abstract CategoryDAO categoryDAO();
    public abstract OrderDAO orderDAO();
    public abstract OrderItemDAO orderItemDAO();
    public abstract PaymentDAO paymentDAO();
    public abstract ProductDAO productDAO();
    public abstract ProductSubImageDAO productSubImageDAO();
    public abstract UserAddressDAO userAddressDAO();
    public abstract UserDAO userDAO();
    private static HighTechShopRoomDatabase INSTANCE = null;

    public static HighTechShopRoomDatabase getInstance(Context context) {
        synchronized (RoomDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context,
                                HighTechShopRoomDatabase.class, Constant.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }
}
