package com.example.high_tech_shop.repositories;

import android.content.Context;

import com.example.high_tech_shop.dao.ProductDAO;
import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.room.HighTechShopRoomDatabase;

import java.util.List;

public class ProductRepository {
    private ProductDAO productDAO;
    public ProductRepository(Context context){
        this.productDAO = HighTechShopRoomDatabase.getInstance(context).productDAO();
    }
    public void insert(List<Product> products){
        productDAO.insert(products);
    }
    public Product get(int id){
        return productDAO.get(id);
    }
    public List<Product> getAll(){
        return productDAO.getAll();
    }
    public void deleteAll(){
        productDAO.deleteAll();
    }

    public List<Product> getProductsByCategoryId(int categoryId){
        return productDAO.getProductsByCategoryId(categoryId);
    }
    public Product getProductById(int id){
        return productDAO.get(id);
    }
    public void update(List<Product> products) {
        productDAO.update(products);
    }
}
