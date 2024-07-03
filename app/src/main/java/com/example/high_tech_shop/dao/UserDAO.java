package com.example.high_tech_shop.dao;

import androidx.room.*;

import com.example.high_tech_shop.entity.User;

import java.util.List;
@Dao
public interface UserDAO {
    @Insert
    void insert(User user);
    @Insert
    void insert(List<User> users);
    @Update
    void update(User user);
    @Delete
    void delete(User user);
    @Query("SELECT * FROM user WHERE id = :id")
    User findById(int id);
    @Query("SELECT * FROM user")
    List<User> findAll();
    @Query("DELETE FROM user")
    void deleteAll();
    @Query("Select * from user Where email =:email And password=:password")
    User selectUserByUsernameAndPassword(String email, String password);
    @Query("Select * from user Where email =:email")
    User selectUserByEmail(String email);
}
