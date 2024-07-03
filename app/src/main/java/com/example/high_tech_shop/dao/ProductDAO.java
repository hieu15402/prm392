package com.example.high_tech_shop.dao;

import androidx.room.*;

import com.example.high_tech_shop.entity.Product;

import java.util.List;
@Dao
public interface ProductDAO {
    @Insert
    void insert(Product product);
    @Insert
    void insert(List<Product> products);
    @Update
    void update(Product product);
    @Delete
    void delete(Product product);
    @Query("DELETE FROM product")
    void deleteAll();
    @Query("SELECT * FROM product WHERE id = :id")
    Product get(int id);
    @Query("SELECT * FROM product")
    List<Product> getAll();
    @Query("SELECT * FROM product WHERE categoryId = :categoryId")
    List<Product> getProductsByCategoryId(int categoryId);

}
