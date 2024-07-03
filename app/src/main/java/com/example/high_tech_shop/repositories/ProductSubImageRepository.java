package com.example.high_tech_shop.repositories;

import android.content.Context;

import com.example.high_tech_shop.dao.ProductSubImageDAO;
import com.example.high_tech_shop.entity.ProductSubImage;
import com.example.high_tech_shop.room.HighTechShopRoomDatabase;

import java.util.List;

public class ProductSubImageRepository {
    private ProductSubImageDAO productSubImageDAO;
    public ProductSubImageRepository(Context context) {
        this.productSubImageDAO = HighTechShopRoomDatabase.getInstance(context).productSubImageDAO();
    }
    public void insert(List<ProductSubImage> productSubImages) {
        productSubImageDAO.insert(productSubImages);
    }
}
