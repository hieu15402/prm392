package com.example.high_tech_shop.repositories;

import android.content.Context;

import com.example.high_tech_shop.dao.CategoryDAO;
import com.example.high_tech_shop.entity.Category;
import com.example.high_tech_shop.room.HighTechShopRoomDatabase;

import java.util.List;

public class CategoryRepository {
    private CategoryDAO categoryDAO;
    public CategoryRepository(Context context) {
        this.categoryDAO = HighTechShopRoomDatabase.getInstance(context).categoryDAO();
    }
    public void insert(List<Category> category) {
        categoryDAO.insert(category);
    }
    public void deleteAll() {
        categoryDAO.deleteALl();
    }
    public Category findById(int id){
        return  categoryDAO.findById(id);
    }
    public void delete(Category category) {
        categoryDAO.delete(category);
    }
    public void update(Category category) {
        categoryDAO.update(category);
    }

    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

}
