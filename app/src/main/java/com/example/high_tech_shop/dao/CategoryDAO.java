package com.example.high_tech_shop.dao;

import androidx.room.*;
import androidx.room.Query;

import com.example.high_tech_shop.entity.Category;

import java.util.List;
@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM Category")
    List<Category> findAll();
    @Query("DELETE FROM Category")
    void deleteALl();
    @Query("SELECT * FROM Category WHERE id = :id")
    Category findById(int id);
    @Insert
    void insert(Category category);
    @Insert
    void insert(List<Category> categories);
    @Update
    void update(Category category);
    @Delete
    void save(Category category);

    @Delete
    void delete(Category category);
}
