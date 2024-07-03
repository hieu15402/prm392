package com.example.high_tech_shop.dao;

import androidx.room.*;

import com.example.high_tech_shop.entity.ProductSubImage;

import java.util.List;
@Dao
public interface ProductSubImageDAO {
    @Query("SELECT * FROM ProductSubImage")
    List<ProductSubImage> getAll();
    @Query("SELECT * FROM ProductSubImage WHERE id = :id")
    ProductSubImage getById(int id);
    @Insert
    void insert(ProductSubImage productSubImage);
    @Insert
    void insert(List<ProductSubImage> productSubImages);
    @Update
    void update(ProductSubImage productSubImage);
    @Delete
    void delete(ProductSubImage productSubImage);
}
