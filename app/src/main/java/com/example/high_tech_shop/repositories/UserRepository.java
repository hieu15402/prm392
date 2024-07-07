package com.example.high_tech_shop.repositories;

import android.content.Context;

import com.example.high_tech_shop.dao.UserDAO;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.room.HighTechShopRoomDatabase;

import java.util.List;

public class UserRepository {
    private UserDAO userDAO;
    public UserRepository(Context context){
        this.userDAO = HighTechShopRoomDatabase.getInstance(context).userDAO();
    }
    public void insert(List<User> users){
        this.userDAO.insert(users);
    }
    public void deleteAll(){
        this.userDAO.deleteAll();
    }
    public void update(User user){
        this.userDAO.update(user);
    }
    public User findByEmail(String email){
        return this.userDAO.selectUserByEmail(email);
    }
}
